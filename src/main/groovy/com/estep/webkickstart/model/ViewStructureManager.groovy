package com.estep.webkickstart.model

import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
 * Handles the creation of the project and module folder structures for the web UI and services project.
 */
class ViewStructureManager {
    private static final String BASE_FOLDER = "project_base_folder";
    private static final String ROOT_FOLDER = "view_root_folder_name";
    private static final String APP_FOLDER = "app";
    private static final String SRC_FOLDER = "src";

    ViewStructureManager() {
    }

    /**
     * Creates the view structure on the file system.
     */
    void createViewStructure() {
        def bf = TextTemplate.renderDeep(ViewProperty.get(BASE_FOLDER))

        def rf = TextTemplate.renderDeep(ViewProperty.get(ROOT_FOLDER))

        createCodeStructure(bf, rf, "e2e")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER)
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "environment")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "footer")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "header")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "home")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "security")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "shared")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "user")

        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + "assets")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + "assets" + File.separator + "bootstrap")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + "assets" + File.separator + "font-awesome")
        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + "assets" + File.separator + "google")

        createCodeStructure(bf, rf, SRC_FOLDER + File.separator + "environments")
    }

    /**
     * Creates the folder structure structure related to the supplied source property/information.
     *
     * @param bf the property pointing to the base folder name.
     * @param rf the property pointing to the root folder name.
     * @param path the path to append to the base structure.
     * @return the created path.
     */
    void createCodeStructure(bf, rf, path) {
        File file = new File(bf + File.separator + rf + File.separator + path)
        file.mkdirs()
    }

    /**
     * Deletes the view structure from the file system.
     */
    void deleteViewStructure() {
        def value = TextTemplate.renderDeep(ViewProperty.get(BASE_FOLDER))
        File file = new File(value)
        file.deleteDir();
    }
}
