package grudge

import grails.test.*

class DiagramControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
        // http://jira.codehaus.org/browse/GRAILS-5926
        controller.metaClass.message = { Map map -> return "" }
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSave() {
        controller.metaClass.getParams = {-> [logicalSpecification:'Sample',title:'Sample']}
        controller.save()
        def diagram = Diagram.findByLogicalSpecificationHash('c5dd1b2697720fe692c529688d3f4f8d')
        assertNotNull diagram
        assertEquals 'c5dd1b2697720fe692c529688d3f4f8d', diagram.logicalSpecificationHash
        assertTrue (new File('c5dd1b2697720fe692c529688d3f4f8d.png').exists())
        assertEquals 'show', controller.redirectArgs.action
    }

    void testDelete() {
        def diagram = new Diagram(logicalSpecification:'Sample',
                                  title:'Sample',
                                  logicalSpecificationHash:'c5dd1b2697720fe692c529688d3f4f8d',
                                  layoutSpecification:'Sample').save()
        controller.metaClass.getParams = {-> [id:diagram.id]}
        controller.delete()
        assertFalse (new File('c5dd1b2697720fe692c529688d3f4f8d.png').exists())
        assertEquals 'list', controller.redirectArgs.action
    }
}
