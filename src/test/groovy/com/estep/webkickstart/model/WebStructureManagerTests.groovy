package com.estep.webkickstart.model

class WebStructureManagerTests extends GroovyTestCase {
    WebStructureManager manager
    TemplateCopy templateCopy

    void setUp() {
        manager = new WebStructureManager()
        templateCopy = new TemplateCopy()
    }


    void tearDown() {
    }

    void testCreateModelStructure() {
        manager.createWebStructure()
        assertWebStructure()
    }

    private void assertWebStructure() {
        def mainPath = renderValue("web.base.path", "main")
        def testPath = renderValue("web.base.path", "test")
        assertWebcPathsExist(mainPath, true)
        assertWebcPathsExist(testPath, true)
    }

    private String renderValue(propertyName, folderName) {
        def template = Property.get(propertyName) + File.separator + folderName + File.separator + "java"
        def path = TextTemplate.renderDeep(template)

        path
    }

    private void assertWebcPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + File.separator + render("web.security.path"), exists)
        assertPath(path + File.separator + render("web.user.path"), exists)
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(Property.get(propertyName))
    }
}