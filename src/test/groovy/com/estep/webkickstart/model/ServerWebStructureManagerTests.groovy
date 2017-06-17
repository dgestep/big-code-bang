package com.estep.bigbangcode.model

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
        def mainPath = renderValue("web.base.path", "src" + File.separator + "main")
        def testPath = renderValue("web.base.path", "src" + File.separator + "test")
        assertWebPathsExist(mainPath, true)
        assertWebPathsExist(testPath, true)
    }

    private String renderValue(propertyName, folderName) {
        def template = ServerProperty.get(propertyName) + File.separator + folderName + File.separator + "java"
        def path = TextTemplate.renderDeep(template)

        path
    }

    private void assertWebPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + File.separator + render("web.security.path"), exists)
        assertPath(path + File.separator + render("web.user.path"), exists)
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(ServerProperty.get(propertyName))
    }
}