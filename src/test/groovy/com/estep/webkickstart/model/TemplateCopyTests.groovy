package com.estep.webkickstart.model

class TemplateCopyTests extends GroovyTestCase {
    TemplateCopy manager

    void setUp() {
        manager = new TemplateCopy();
    }

    void tearDown() {
    }

    void testCopyTemplate() {
        def basePath = render("model.base.path")
        basePath = basePath + File.separator + "config"
        basePath = basePath + File.separator + "checkstyle"
        new File(basePath).mkdirs()

        def fileName = basePath + File.separator + "proj-checkstyle.xml"
        manager.copy("proj-checkstyle.xml", fileName)
        assert new File(fileName).exists()

        new File(render("model.base.path")).deleteDir()
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(Property.get(propertyName), 2)
    }
}
