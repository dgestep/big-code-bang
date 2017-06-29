package com.estep.bigcodebang.model

class ViewStructureManagerTests extends GroovyTestCase {
    ViewStructureManager manager
    TemplateCopy templateCopy

    void setUp() {
        manager = new ViewStructureManager()
        templateCopy = new TemplateCopy()
    }

    void tearDown() {
    }

    void testCreateAndDeleteStructure() {
        manager.createViewStructure()
        assertViewStructure()

        manager.deleteViewStructure();

        def path = TextTemplate.renderDeep(ViewProperty.get("project_base_folder"))
        File file = new File(path)
        assert !file.exists()
    }

    private void assertViewStructure() {
        def path = renderFromBase("src/app/footer")
        assertPath(path, true)
        path = renderFromBase("src/app/header")
        assertPath(path, true)
        path = renderFromBase("src/app/home")
        assertPath(path, true)
        path = renderFromBase("src/app/security")
        assertPath(path, true)
        path = renderFromBase("src/app/shared")
        assertPath(path, true)
        path = renderFromBase("src/app/user")
        assertPath(path, true)
        path = renderFromBase("src/environments")
        assertPath(path, true)
        path = renderFromBase("src/assets/bootstrap")
        assertPath(path, true)
        path = renderFromBase("src/assets/font-awesome")
        assertPath(path, true)
        path = renderFromBase("src/assets/google")
        assertPath(path, true)
    }

    private String renderFromBase(folderName) {
        def base = renderValue("project_base_folder") + '/' + renderValue("view_root_folder_name")
        def template = base + '/' + folderName
        def path = TextTemplate.renderDeep(template)

        path
    }

    private String renderValue(propertyName) {
        def template = ViewProperty.get(propertyName)
        def path = TextTemplate.renderDeep(template)

        path
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }
}