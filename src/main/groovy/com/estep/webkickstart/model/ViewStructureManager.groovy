package com.estep.webkickstart.model

import sun.reflect.generics.reflectiveObjects.NotImplementedException

/**
 * Handles the creation of the project and module folder structures for the web UI and services project.
 */
class ViewStructureManager {
    private static final String BASE_FOLDER = "project_base_folder";
    private static final String APP_FOLDER = "app";
    private static final String SRC_FOLDER = "src";

    ViewStructureManager() {
    }

    /**
     * Creates the view structure on the file system.
     */
    void createViewStructure() {
        createCodeStructure(BASE_FOLDER, "e2e")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER)
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "environment")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "footer")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "header")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "home")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "security")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "shared")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + APP_FOLDER + File.separator + "user")

        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + "assets")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + "bootstrap")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + "font-awesome")
        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + "google")

        createCodeStructure(BASE_FOLDER, SRC_FOLDER + File.separator + "environments")
    }

    /**
     * Creates the folder structure structure related to the supplied source property/information.
     *
     * @param basePathProperty the property pointing to the base folder structure.
     * @param path the path to append to the base structure.
     * @return the created path.
     */
    void createCodeStructure(basePathProperty, path) {
        def value = TextTemplate.renderDeep(ViewProperty.get(basePathProperty))
        File file = new File(value + File.separator + path)
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
