package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.Property
import com.estep.webkickstart.model.TemplateCopy
import com.estep.webkickstart.model.TextTemplate
import com.estep.webkickstart.model.Tuple

class WebStructureCreatorScript {

    void execute() {
        def notIncludeWebProject = !"Y".equalsIgnoreCase(Property.get("web_include_project"))
        if (notIncludeWebProject) {
            return
        }

        copyWebProjectCode()
        copyWebSecurityProjectCode()
        copyWebUserProjectCode()

        copyWebOuterScripts("web_build_gradle_outer.txt", "build.gradle")
        copyWebOuterScripts("web_settings_gradle_outer.txt", "settings.gradle")
        copyWebGradleRootBuildScript()
        copyInsomniaWorkspaceJson("test")
        copyLog4jDtd("main")
        copyLog4XmlFiles("log4j-development", "web_log4j_development_xml.txt")
        copyLog4XmlFiles("log4j-production", "web_log4j_production_xml.txt")
        copyServiceSpringXml("main")
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

        ScriptHelper.render("web-restcontroller-security-templates", apps)
    }

    private void copyWebUserProjectCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("UserController.java", getPathToWebCode("main", "user","UserController.java")))

        ScriptHelper.render("web-restcontroller-user-templates", apps)
    }

    private void copyWebGradleRootBuildScript() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("build.gradle")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web_build_gradle_root.txt", buf.toString())
    }

    private void copyWebOuterScripts(templateName, fileName) {
        StringBuilder buf = new StringBuilder()
        def basePath = TextTemplate.renderDeep(Property.get("root.base.path"))

        buf.append(ScriptHelper.render("root.base.path"))
        buf.append(File.separator).append(fileName)

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy(templateName, buf.toString())
    }

    private void copyInsomniaWorkspaceJson(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("src")
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("resources")
        buf.append(File.separator).append("insomnia-workspace.json")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web_insomnia_workspace_json.txt", buf.toString())
    }

    private void copyLog4jDtd(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("src")
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("resources")
        buf.append(File.separator).append("log4j.dtd")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web_log4j_dtd.txt", buf.toString())
    }

    private void copyLog4XmlFiles(name, templateName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("src")
        buf.append(File.separator).append("main")
        buf.append(File.separator).append("resources")
        buf.append(File.separator).append(name).append(".xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy(templateName, buf.toString())
    }

    private void copyServiceSpringXml(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("src")
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("resources")
        buf.append(File.separator).append("service-spring-main.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web_service_spring_main_xml.txt", buf.toString())
    }

    private String getPathToWebCode(folderName, subPackage, programName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("src")
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("java")
        buf.append(File.separator).append("com")
        buf.append(File.separator).append(ScriptHelper.render("company_name"))
        buf.append(File.separator).append(ScriptHelper.render("product_name"))
        buf.append(File.separator).append("restcontroller")
        if (subPackage != null) {
            buf.append(File.separator).append(subPackage)
        }

        buf.append(File.separator).append(programName)

        buf.toString()
    }
}
