package com.estep.webkickstart.model

import com.estep.webkickstart.ProjectHelper

/**
 * Handles the creation of the project and module folder structures for the Model projects.
 */
class ServerModelStructureManager {
    ServerModelStructureManager() {
    }

    /**
     * Creates the model structure on the file system.
     */
    void createModelStructure() {
        ProjectHelper.createConfigStructure()
        createAppLogicModuleStructure("main")
        createAppLogicModuleStructure("test")

        createDataModuleStructure("main")
        createDataModuleStructure("test")

        createSharedModuleStructure("main")
        createSharedModuleStructure("test")
    }

    /**
     * Deletes the model structure from the file system.
     */
    void deleteModelStructure() {
        def modelBasePath = TextTemplate.renderDeep(ServerProperty.get("model.base.path"))

        File file = new File(modelBasePath)
        file.deleteDir()
    }

    /**
     * Creates the application logic module structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createAppLogicModuleStructure(folderName) {
        def path = ProjectHelper.createSourceStructure("applogic.base.path", folderName)

        ProjectHelper.createCodeStructure("applogic.aspect", path)
        ProjectHelper.createCodeStructure("applogic.service.lookup", path)
        ProjectHelper.createCodeStructure("applogic.service.security", path)
        ProjectHelper.createCodeStructure("applogic.service.user", path)
    }

    /**
     * Creates the data module structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createDataModuleStructure(folderName) {
        def path = ProjectHelper.createSourceStructure("data.base.path", folderName)
        ProjectHelper.createCodeStructure("model.data.lookup", path)
        ProjectHelper.createCodeStructure("model.data.mail", path)
        ProjectHelper.createCodeStructure("model.data.user", path)
    }

    /**
     * Creates the shared module structure.
     * @param folderName the folder name to place the structure in.
     */
    protected createSharedModuleStructure(folderName) {
        def path = ProjectHelper.createSourceStructure("shared.base.path", folderName)
        ProjectHelper.createCodeStructure("model.shared.exception", path)
        ProjectHelper.createCodeStructure("model.shared.criteria", path)
        ProjectHelper.createCodeStructure("model.shared.data", path)
        ProjectHelper.createCodeStructure("model.shared.message", path)
        ProjectHelper.createCodeStructure("model.shared.log", path)
        ProjectHelper.createCodeStructure("model.shared.util", path)
    }
}
