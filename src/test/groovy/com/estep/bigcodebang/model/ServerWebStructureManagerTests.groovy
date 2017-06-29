package com.estep.bigcodebang.model

class ServerWebStructureManagerTests extends GroovyTestCase {
    ServerRestServiceStructureManager manager
    TemplateCopy templateCopy

    void setUp() {
        manager = new ServerRestServiceStructureManager()
        templateCopy = new TemplateCopy()
    }


    void tearDown() {
    }

    void testCreateModelStructure() {
        manager.createRestServiceStructure()
        assertWebStructure()
    }

    private void assertWebStructure() {
        def mainPath = renderValue("web.base.path", "src/main")
        def testPath = renderValue("web.base.path", "src/test")
        assertWebPathsExist(mainPath, true)
        assertWebPathsExist(testPath, true)
    }

    private String renderValue(propertyName, folderName) {
        def template = ServerProperty.get(propertyName) + '/' + folderName + "/java"
        def path = TextTemplate.renderDeep(template)

        path
    }

    private void assertWebPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + '/' + render("web.security.path"), exists)
        assertPath(path + '/' + render("web.user.path"), exists)
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(ServerProperty.get(propertyName))
    }
}