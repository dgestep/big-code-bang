package com.estep.bigcodebang.model

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

        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER)
        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER + "/footer")
        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER + "/header")
        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER + "/home")
        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER + "/security")
        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER + "/shared")
        createCodeStructure(bf, rf, SRC_FOLDER + '/' + APP_FOLDER + "/user")

        createCodeStructure(bf, rf, SRC_FOLDER + "/assets/bootstrap/css")
        createCodeStructure(bf, rf, SRC_FOLDER + "/assets/bootstrap/fonts")
        createCodeStructure(bf, rf, SRC_FOLDER + "/assets/bootstrap/js")

        createCodeStructure(bf, rf, SRC_FOLDER + "/assets/font-awesome/css")
        createCodeStructure(bf, rf, SRC_FOLDER + "/assets/font-awesome/fonts")

        createCodeStructure(bf, rf, SRC_FOLDER + "/assets/google")

        createCodeStructure(bf, rf, SRC_FOLDER + "/environments")
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
        File file = new File(bf + '/' + rf + '/' + path)
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
