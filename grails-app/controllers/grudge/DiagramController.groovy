package grudge

class DiagramController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", redraw: "POST"]

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [diagramInstanceList: Diagram.list(params), diagramInstanceTotal: Diagram.count()]
    }

    def create = {
        def diagramInstance = new Diagram()
        diagramInstance.properties = params
        return [diagramInstance: diagramInstance]
    }

    def save = {
        def diagramInstance = new Diagram(params)
        diagramInstance.logicalSpecificationHash = diagramInstance.hash()

        def duplicate = Diagram.findByLogicalSpecificationHash(diagramInstance.logicalSpecificationHash)
        if(duplicate) {
            redirect(action: "show", id: duplicate.id)
            return
        }

        layoutDiagram(diagramInstance)
        renderDiagram(diagramInstance)

        if (diagramInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'diagram.label', default: 'Diagram'), diagramInstance.id])}"
            redirect(action: "show", id: diagramInstance.id)
        }
        else {
            render(view: "create", model: [diagramInstance: diagramInstance])
        }
    }

    def redraw = {
        def diagramInstance = Diagram.get(params.id)
        if (!diagramInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diagram.label', default: 'Diagram'), params.id])}"
            redirect(action: "list")
            return
        }

        def f = new File(diagramInstance.logicalSpecificationHash + '.png')
        f.delete()

        renderDiagram(diagramInstance)

        redirect(action: "show", id: diagramInstance.id)
    }

    def layoutDiagram(diagramInstance) {
        def parser = new SpecificationParser()
        try {
            parser.parse(diagramInstance.logicalSpecification)
        }
        catch(ex) {
            println "Parse error in logical specification"
            return 1
        }
        def diagramSpec = parser.diagramSpecification

        def algorithm = new ForceBasedAlgorithm()
        algorithm.layout(diagramSpec)

        def writer = new SpecificationWriter()
        def buf = new ByteArrayOutputStream()
        def originalOut = System.out
        System.out = new PrintStream(buf)
        writer.write(diagramSpec)
        diagramInstance.layoutSpecification = buf.toString()
        System.out = originalOut
    }

    def renderDiagram(diagramInstance) {
        def parser = new SpecificationParser()
        try {
            parser.parse(diagramInstance.layoutSpecification)
        }
        catch(ex) {
            println "Parse error in layout specification"
            return 1
        }

        def renderer = new DiagramRenderer()
        renderer.filename = diagramInstance.logicalSpecificationHash + '.png'
        renderer.render(parser.diagramSpecification)
    }

    def show = {
        def diagramInstance = Diagram.get(params.id)
        if (!diagramInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diagram.label', default: 'Diagram'), params.id])}"
            redirect(action: "list")
        }
        else {
            [diagramInstance: diagramInstance]
        }
    }

    def img = {
        // TODO: security! whitelist time
        def hash = params.id
        def f = new File(hash + '.png')
        byte[] bytes = f.readBytes()
        response.setContentType("image/png")
        response.getOutputStream().write(bytes)
        response.flushBuffer()
    }

    def delete = {
        def diagramInstance = Diagram.get(params.id)
        if (diagramInstance) {
            try {
                def f = new File(diagramInstance.logicalSpecificationHash + '.png')
                f.delete()

                diagramInstance.delete(flush: true)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'diagram.label', default: 'Diagram'), params.id])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'diagram.label', default: 'Diagram'), params.id])}"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'diagram.label', default: 'Diagram'), params.id])}"
            redirect(action: "list")
        }
    }
}
