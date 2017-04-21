package com.estep.webkickstart.model

import com.estep.webkickstart.ProjectHelper

/**
 * Handles the creation of the project and module folder structures for the web UI and services project.
 */
class WebStructureManager {
    WebStructureManager() {
    }

    /**
     * Creates the web structure on the file system.
     */
    void createWebStructure() {
        ProjectHelper.createConfigStructure()

        createWebModuleStructure("main")
        createWebModuleStructure("test")
    }

    /**
     * Deletes the web structure from the file system.
     */
    void deleteWebStructure() {

    }

    /**
     * Creates the application logic module structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createWebModuleStructure(folderName) {
        def path = ProjectHelper.createSourceStructure("web.base.path", folderName)
        ProjectHelper.createCodeStructure("web.security.path", path)
        ProjectHelper.createCodeStructure("web.user.path", path)

        path = ProjectHelper.createSourceStructure("web.base.path", folderName)
        ProjectHelper.createCodeStructure("web.security.path", path)
        ProjectHelper.createCodeStructure("web.user.path", path)
    }
}
