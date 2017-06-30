package com.estep.bigcodebang.model

import com.estep.bigcodebang.ProjectHelper

/**
 * Handles the creation of the project and module folder structures for the REST services project.
 */
class ServerRestServiceStructureManager {
    ServerRestServiceStructureManager() {
    }

    /**
     * Creates the web structure on the file system.
     */
    void createRestServiceStructure() {
        def modelPath = TextTemplate.renderDeep(ServerProperty.get("web.base.path"))
        ProjectHelper.makeDirectories(modelPath + "/gradle/wrapper")

        ProjectHelper.createConfigStructure()

        createWebModuleSourceStructure("main")
        createWebModuleSourceStructure("test")

        createWebContentSourceStructure("WEB-INF")
    }

    /**
     * Creates the web java source structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createWebModuleSourceStructure(folderName) {
        def srcPath = "src/" + folderName
        def path = ProjectHelper.createSourceStructure("web.base.path", srcPath)
        ProjectHelper.createCodeStructure("web.security.path", path)
        ProjectHelper.createCodeStructure("web.user.path", path)
    }

    /**
     * Creates the web content structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createWebContentSourceStructure(folderName) {
        def template = ServerProperty.get("web.base.path") + "/WebContent/" + folderName
        def path = TextTemplate.renderDeep(template)
        ProjectHelper.makeDirectories(path)
    }
}
