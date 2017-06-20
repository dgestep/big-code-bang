package com.estep.bigcodebang.model

import com.estep.bigcodebang.model.script.ScriptHelper

class TemplateCopyTests extends GroovyTestCase {
    TemplateCopy manager

    void setUp() {
        manager = new TemplateCopy();
    }

    void tearDown() {
    }

    void testCopyTemplate() {
        def basePath = render("model.base.path")
        basePath = basePath + File.separator + "config"
        basePath = basePath + File.separator + "checkstyle"
        new File(basePath).mkdirs()

        def fileName = basePath + File.separator + "proj-checkstyle.xml"
        manager.copy("proj-checkstyle.xml", fileName)
        assert new File(fileName).exists()

        new File(render("model.base.path")).deleteDir()
    }

    void testCopyAll() {
        String folder = ScriptHelper.createSubpackages("src", "assets", "bootstrap", "css")
        String destinationFolder = getPathToViewCode(folder)
        new File(destinationFolder).mkdirs()

        manager.copyAll("view-assets-bootstrap-css-templates", destinationFolder)
        assert new File(destinationFolder + File.separator + "bootstrap.css").exists()
        assert new File(destinationFolder + File.separator + "bootstrap-theme.css").exists()

        new File(destinationFolder).deleteDir()
    }

    private String getPathToViewCode(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.viewRender("project_base_folder")).append(File.separator)
        buf.append(ScriptHelper.viewRender("view_root_folder_name")).append(File.separator)
        if (folderName != null) {
            buf.append(folderName).append(File.separator)
        }

        buf.toString()
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(ServerProperty.get(propertyName))
    }
}
