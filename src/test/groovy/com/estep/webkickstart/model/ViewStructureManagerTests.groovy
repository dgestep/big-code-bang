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
        def path = renderFromBase("src" + File.separator + "app" + File.separator + "environment")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "app" + File.separator + "footer")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "app" + File.separator + "header")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "app" + File.separator + "home")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "app" + File.separator + "security")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "app" + File.separator + "shared")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "app" + File.separator + "user")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "environments")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "assets" + File.separator + "bootstrap")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "assets" + File.separator + "font-awesome")
        assertPath(path, true)
        path = renderFromBase("src" + File.separator + "assets" + File.separator + "google")
        assertPath(path, true)
    }

    private String renderFromBase(folderName) {
        def base = renderValue("project_base_folder") + File.separator + renderValue("view_root_folder_name")
        def template = base + File.separator + folderName
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