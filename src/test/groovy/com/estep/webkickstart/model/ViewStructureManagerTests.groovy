package com.estep.webkickstart.model

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
        def path = renderValue("project_base_folder", "e2e")
        assertPath(path, true)

        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "environment")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "footer")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "header")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "home")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "security")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "shared")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "app" + File.separator + "user")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "assets")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "bootstrap")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "environments")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "font-awesome")
        assertPath(path, true)
        path = renderValue("project_base_folder", "src" + File.separator + "google")
        assertPath(path, true)
    }

    private String renderValue(propertyName, folderName) {
        def template = ViewProperty.get(propertyName) + File.separator + folderName
        def path = TextTemplate.renderDeep(template)

        path
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }
}