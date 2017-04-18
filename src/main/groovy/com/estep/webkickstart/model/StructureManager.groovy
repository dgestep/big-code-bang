package com.estep.webkickstart.model

/**
 * Handles the creation of the project and module folder structures.
 */
class StructureManager {
    StructureManager() {
    }

    /**
     * Creates the model structure on the file system.
     */
    void createModelStructure() {
        createConfigStructure()
        createAppLogicModuleStructure()
        createDataModuleStructure()
        createSharedModuleStructure()
    }

    /**
     * Deletes the model structure from the file system.
     */
    void deleteModelStructure() {
        def modelBasePath = TextTemplate.renderDeep(Property.get("model.base.path"))

        File file = new File(modelBasePath)
        file.deleteDir()
    }

    /**
     * Creates the config folder structures.
     */
    protected void createConfigStructure() {
        def basePath = TextTemplate.renderDeep(Property.get("root.base.path"))
        makeDirectories(basePath + File.separator + "config" + File.separator + "checkstyle")
        makeDirectories(basePath + File.separator + "config" + File.separator + "rulesets")
    }

    /**
     * Creates the application logic module structure.
     */
    protected void createAppLogicModuleStructure() {
        def path = createSourceStructure("applogic.base.path", "main")

        createCodeStructure(Property.get("applogic.aspect"), path)
        createCodeStructure(Property.get("applogic.service.lookup"), path)
        createCodeStructure(Property.get("applogic.service.security"), path)
        createCodeStructure(Property.get("applogic.service.user"), path)

        path = createSourceStructure("applogic.base.path", "test")

        createCodeStructure(Property.get("applogic.aspect"), path)
        createCodeStructure(Property.get("applogic.service.lookup"), path)
        createCodeStructure(Property.get("applogic.service.security"), path)
        createCodeStructure(Property.get("applogic.service.user"), path)
    }

    /**
     * Creates the data module structure.
     */
    protected void createDataModuleStructure() {
        def path = createSourceStructure("data.base.path", "main")
        createCodeStructure(Property.get("model.data.lookup"), path)
        createCodeStructure(Property.get("model.data.mail"), path)
        createCodeStructure(Property.get("model.data.user"), path)

        path = createSourceStructure("data.base.path", "test")

        createCodeStructure(Property.get("model.data.lookup"), path)
        createCodeStructure(Property.get("model.data.mail"), path)
        createCodeStructure(Property.get("model.data.user"), path)
    }

    /**
     * Creates the shared module structure.
     */
    protected createSharedModuleStructure() {
        def path = createSourceStructure("shared.base.path", "main")
        createCodeStructure(Property.get("model.shared.exception"), path)
        createCodeStructure(Property.get("model.shared.criteria"), path)
        createCodeStructure(Property.get("model.shared.data"), path)
        createCodeStructure(Property.get("model.shared.message"), path)
        createCodeStructure(Property.get("model.shared.log"), path)
        createCodeStructure(Property.get("model.shared.util"), path)

        path = createSourceStructure("shared.base.path", "test")

        createCodeStructure(Property.get("model.shared.exception"), path)
        createCodeStructure(Property.get("model.shared.criteria"), path)
        createCodeStructure(Property.get("model.shared.data"), path)
        createCodeStructure(Property.get("model.shared.message"), path)
        createCodeStructure(Property.get("model.shared.log"), path)
        createCodeStructure(Property.get("model.shared.util"), path)
    }

    private void createCodeStructure(String folderStructure, String path) {
        def value = TextTemplate.renderDeep(folderStructure)
        File file = new File(path + File.separator + value)
        file.mkdirs()
    }

    private String createSourceStructure(basePathProperty, folderName) {
        def template = Property.get(basePathProperty) + File.separator + folderName
        def path = TextTemplate.renderDeep(template)

        def resources = path + File.separator + "resources"
        makeDirectories(resources)

        path += File.separator + "java"
        makeDirectories(path)

        path
    }

    private void makeDirectories(String path) {
        File file = new File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
    }
}
