package grudge

class DiagramController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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

        layoutRender(diagramInstance)

        if (diagramInstance.save(flush: true)) {
            flash.message = "${message(code: 'default.created.message', args: [message(code: 'diagram.label', default: 'Diagram'), diagramInstance.id])}"
            redirect(action: "show", id: diagramInstance.id)
        }
        else {
            render(view: "create", model: [diagramInstance: diagramInstance])
        }
    }

    def layoutRender(diagramInstance) {
        String[] roots =  ['scripts/layout-render']
        def gse = new GroovyScriptEngine(roots)
        Binding binding = new Binding()
        def filename = diagramInstance.logicalSpecificationHash + '.png'
        String[] args = [filename]
        binding.setVariable("args", args)
        binding.setVariable("logicalSpecification", diagramInstance.logicalSpecification)
        gse.run('LayoutRender.groovy', binding)
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
