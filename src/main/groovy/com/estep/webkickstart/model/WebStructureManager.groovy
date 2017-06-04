package com.estep.webkickstart.model

import com.estep.webkickstart.ProjectHelper
import com.estep.webkickstart.model.script.ScriptHelper

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

        createWebModuleSourceStructure("main")
        createWebModuleSourceStructure("test")

        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "css"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "fonts"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "footer"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "header"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "login"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "session-lost"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "shared"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "user-profile"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("app", "tabs"))
        createWebContentSourceStructure("images")
        createWebContentSourceStructure("WEB-INF")
        createWebContentSourceStructure(ScriptHelper.createSubpackages("spec", "support"))
        createWebContentSourceStructure(ScriptHelper.createSubpackages("testing"))
    }

    /**
     * Deletes the web structure from the file system.
     */
    void deleteWebStructure() {
    }

    /**
     * Creates the web java source structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createWebModuleSourceStructure(folderName) {
        def srcPath = "src" + File.separator + folderName
        def path = ProjectHelper.createSourceStructure("web.base.path", srcPath)
        ProjectHelper.createCodeStructure("web.security.path", path)
        ProjectHelper.createCodeStructure("web.user.path", path)
    }

    /**
     * Creates the web content structure.
     * @param folderName the folder name to place the structure in.
     */
    protected void createWebContentSourceStructure(folderName) {
        def template = Property.get("web.base.path") + File.separator + "WebContent" + File.separator + folderName
        def path = TextTemplate.renderDeep(template)
        ProjectHelper.makeDirectories(path)
    }
}
