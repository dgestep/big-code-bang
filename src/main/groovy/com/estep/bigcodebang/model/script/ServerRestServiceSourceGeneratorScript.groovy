package com.estep.bigcodebang.model.script

import com.estep.bigcodebang.model.TemplateCopy
import com.estep.bigcodebang.model.TemplateCopy
import com.estep.bigcodebang.model.Tuple

class ServerRestServiceSourceGeneratorScript {

    void execute() {
        copyWebProjectCode()
        copyWebSecurityProjectCode()
        copyWebUserProjectCode()

        copyWebOuterScripts("root-folder-templates/web_build_gradle_outer.txt", "build.gradle")
        copyWebOuterScripts("root-folder-templates/settings_gradle_outer.txt", "settings.gradle")
        copyWebOuterScripts("root-folder-templates/gitignore_outer.txt", ".gitignore")
        copyWebGradleRootBuildScript()
        copyInsomniaWorkspaceJson("test")
        copyLog4jDtd("main")
        copyLog4XmlFiles("log4j-development", "web-resources-templates/web_log4j_development_xml.txt")
        copyLog4XmlFiles("log4j-production", "web-resources-templates/web_log4j_production_xml.txt")
        copyServicesServletXml()
        copyWebXml()
    }

    private void copyWebProjectCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("ControllerHelper.java", getPathToWebCode("main", null, "ControllerHelper.java")))
        apps.add(new Tuple("EnvironmentSetupListener.java", getPathToWebCode("main", null, "EnvironmentSetupListener.java")))
        apps.add(new Tuple("ErrorController.java", getPathToWebCode("main", null, "ErrorController.java")))
        apps.add(new Tuple("HealthCheckController.java", getPathToWebCode("main", null, "HealthCheckController.java")))
        apps.add(new Tuple("RestControllerJsonAroundAdvice.java", getPathToWebCode("main", null, "RestControllerJsonAroundAdvice.java")))

        ScriptHelper.render("web-restcontroller-templates", apps)
    }

    private void copyWebSecurityProjectCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("CustomCookieCsrfTokenRepository.java", getPathToWebCode("main", "security", "CustomCookieCsrfTokenRepository.java")))
        apps.add(new Tuple("RestAuthenticationEntryPoint.java", getPathToWebCode("main", "security", "RestAuthenticationEntryPoint.java")))
        apps.add(new Tuple("RestAuthenticationManager.java", getPathToWebCode("main", "security", "RestAuthenticationManager.java")))
        apps.add(new Tuple("RestServiceFilter.java", getPathToWebCode("main", "security", "RestServiceFilter.java")))
        apps.add(new Tuple("SimpleCORSFilter.java", getPathToWebCode("main", "security", "SimpleCORSFilter.java")))
        apps.add(new Tuple("TokenThreadLocal.java", getPathToWebCode("main", "security", "TokenThreadLocal.java")))
        apps.add(new Tuple("UserThreadLocal.java", getPathToWebCode("main", "security", "UserThreadLocal.java")))
        apps.add(new Tuple("SecurityController.java", getPathToWebCode("main", "security", "SecurityController.java")))

        ScriptHelper.render("web-restcontroller-security-templates", apps)
    }

    private void copyWebUserProjectCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("UserController.java", getPathToWebCode("main", "user","UserController.java")))

        ScriptHelper.render("web-restcontroller-user-templates", apps)
    }

    private void copyWebGradleRootBuildScript() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append("/build.gradle")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("gradle-scripts-templates/web_build_gradle_root.txt", buf.toString())
    }

    private void copyWebOuterScripts(templateName, fileName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.serverRender("root.base.path"))
        buf.append('/').append(fileName)

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy(templateName, buf.toString())
    }

    private void copyInsomniaWorkspaceJson(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append('/').append("src")
        buf.append('/').append(folderName)
        buf.append('/').append("resources")
        buf.append('/').append("insomnia-workspace.json")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web-test-resources/web_insomnia_workspace_json.txt", buf.toString())
    }

    private void copyLog4jDtd(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append("/src")
        buf.append('/').append(folderName)
        buf.append("/resources")
        buf.append("/log4j.dtd")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web-resources-templates/web_log4j_dtd.txt", buf.toString())
    }

    private void copyLog4XmlFiles(name, templateName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append("/src")
        buf.append("/main")
        buf.append("/resources")
        buf.append('/').append(name).append(".xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy(templateName, buf.toString())
    }

    private void copyServicesServletXml() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append("/WebContent")
        buf.append("/WEB-INF")
        buf.append("/services-servlet.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web-webinf-templates/services_servlet_xml.txt", buf.toString())
    }

    private void copyWebXml() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append("/WebContent")
        buf.append("/WEB-INF")
        buf.append("/web.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web-webinf-templates/web_xml.txt", buf.toString())
    }

    private String getPathToWebCode(folderName, subPackage, programName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.serverRender("web.base.path"))
        buf.append("/src")
        buf.append('/').append(folderName)
        buf.append("/java")
        buf.append('/').append(ScriptHelper.serverRender("top_level_domain"))
        buf.append('/').append(ScriptHelper.serverRender("company_name"))
        buf.append('/').append(ScriptHelper.serverRender("product_name"))
        buf.append("/restcontroller")
        if (subPackage != null) {
            buf.append('/').append(subPackage)
        }

        buf.append('/').append(programName)

        buf.toString()
    }

    private String getPathToViewCode(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("web.base.path")).append('/')
        buf.append("WebContent/")
        if (folderName != null) {
            buf.append(folderName).append('/')
        }

        buf.toString()
    }
}
