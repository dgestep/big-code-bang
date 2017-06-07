package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.ServerProperty
import com.estep.webkickstart.model.TemplateCopy
import com.estep.webkickstart.model.Tuple

class WebStructureCreatorScript {

    void execute() {
        def notIncludeWebProject = !"Y".equalsIgnoreCase(ServerProperty.get("web_include_project"))
        if (notIncludeWebProject) {
            return
        }

        copyWebProjectCode()
        copyWebSecurityProjectCode()
        copyWebUserProjectCode()

        copyWebOuterScripts("web_build_gradle_outer.txt", "build.gradle")
        copyWebOuterScripts("settings_gradle_outer.txt", "settings.gradle")
        copyWebOuterScripts("gitignore_outer.txt", ".gitignore")
        copyWebGradleRootBuildScript()
        copyInsomniaWorkspaceJson("test")
        copyLog4jDtd("main")
        copyLog4XmlFiles("log4j-development", "web_log4j_development_xml.txt")
        copyLog4XmlFiles("log4j-production", "web_log4j_production_xml.txt")
        copyServicesServletXml()
        copyWebXml()

        copyViewCode()
        copyViewAppCode()
        copyViewAppLoginCode()
        copyViewAppCssCode()
        copyAllFonts()
        copyViewAppFooterCode()
        copyViewAppHeaderCode()
        copyViewAppSessionLostCode()
        copyViewAppSharedCode()
        copyViewAppUserProfileCode()
        copyViewAppTabsCode();
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

    private void copyServicesServletXml() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("WebContent")
        buf.append(File.separator).append("WEB-INF")
        buf.append(File.separator).append("services-servlet.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("services_servlet_xml.txt", buf.toString())
    }

    private void copyWebXml() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("WebContent")
        buf.append(File.separator).append("WEB-INF")
        buf.append(File.separator).append("web.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("web_xml.txt", buf.toString())
    }

    private String getPathToWebCode(folderName, subPackage, programName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.render("web.base.path"))
        buf.append(File.separator).append("src")
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("java")
        buf.append(File.separator).append(ScriptHelper.render("top_level_domain"))
        buf.append(File.separator).append(ScriptHelper.render("company_name"))
        buf.append(File.separator).append(ScriptHelper.render("product_name"))
        buf.append(File.separator).append("restcontroller")
        if (subPackage != null) {
            buf.append(File.separator).append(subPackage)
        }

        buf.append(File.separator).append(programName)

        buf.toString()
    }

    private void copyViewCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("index_html.txt", getPathToViewCode(null, "index.html")))
        apps.add(new Tuple("web_package_json.txt", getPathToViewCode(null, "package.json")))
        apps.add(new Tuple("tsconfig_json.txt", getPathToViewCode(null, "tsconfig.json")))
        apps.add(new Tuple("typings_json.txt", getPathToViewCode(null, "typings.json")))
        apps.add(new Tuple("gulpfile_js.txt", getPathToViewCode(null, "gulpfile.js")))
        apps.add(new Tuple("karma_conf_js.txt", getPathToViewCode(null, "karma.conf.js")))
        apps.add(new Tuple("karma_test_shim_js.txt", getPathToViewCode(null, "karma-test-shim.js")))
        apps.add(new Tuple("system_js_config_extras_js.txt", getPathToViewCode(null, "systemjs.config.extras.js")))
        apps.add(new Tuple("system_js_config_js.txt", getPathToViewCode(null, "systemjs.config.js")))

        String supportSubPkg = ScriptHelper.createSubpackages("spec", "support");
        apps.add(new Tuple("jasmine_json.txt", getPathToViewCode(supportSubPkg, "jasmine.json")))

        ScriptHelper.render("web-view-templates", apps)
    }

    private void copyViewAppCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("main_ts.txt", getPathToViewCode("app", "main.ts")))
        apps.add(new Tuple("app_module_ts.txt", getPathToViewCode("app", "app.module.ts")))
        apps.add(new Tuple("app_component_ts.txt", getPathToViewCode("app", "app.component.ts")))
        apps.add(new Tuple("app_component_html.txt", getPathToViewCode("app", "app.component.html")))
        apps.add(new Tuple("app_routes_ts.txt", getPathToViewCode("app", "app.routes.ts")))
        apps.add(new Tuple("landing_page_component_html.txt", getPathToViewCode("app", "landing.page.component.html")))
        apps.add(new Tuple("landing_page_component_ts.txt", getPathToViewCode("app", "landing.page.component.ts")))

        ScriptHelper.render("web-view-app-templates", apps)
    }

    private void copyViewAppLoginCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("logged_in_guard_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "login"), "logged-in.guard.ts")))
        apps.add(new Tuple("logout_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "login"), "logout.component.ts")))
        apps.add(new Tuple("logout_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "login"), "logout.component.html")))
        apps.add(new Tuple("catch_reset_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "login"), "catch.reset.component.ts")))
        apps.add(new Tuple("catch_reset_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "login"), "catch.reset.component.html")))
        apps.add(new Tuple("password_reset_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "login"), "password.reset.component.html")))
        apps.add(new Tuple("password_reset_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "login"), "password.reset.component.ts")))

        ScriptHelper.render("web-view-app-login-templates", apps)
    }

    private void copyViewAppCssCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("bootstrap.css", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "css"), "bootstrap.css")))
        apps.add(new Tuple("bootstrap-theme.css", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "css"), "bootstrap-theme.css")))
        apps.add(new Tuple("main.css", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "css"), "main.css")))

        ScriptHelper.copy("web-view-app-css-templates", apps)
    }

    private void copyViewAppFooterCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("footer_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "footer"), "footer.component.html")))
        apps.add(new Tuple("footer_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "footer"), "footer.component.ts")))
        apps.add(new Tuple("footer_service_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "footer"), "footer.service.ts")))

        ScriptHelper.render("web-view-app-footer-templates", apps)
    }

    private void copyViewAppHeaderCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("header_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "header"), "header.component.html")))
        apps.add(new Tuple("header_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "header"), "header.component.ts")))

        ScriptHelper.render("web-view-app-header-templates", apps)
    }

    private void copyViewAppSessionLostCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("session_lost_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "session-lost"), "session.lost.component.html")))
        apps.add(new Tuple("session_lost_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "session-lost"), "session.lost.component.ts")))

        ScriptHelper.render("web-view-app-session-lost-templates", apps)
    }

    private void copyViewAppSharedCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("constants_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "shared"), "constants.ts")))
        apps.add(new Tuple("name_value_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages("app",
                "shared"), "name.value.ts")))
        apps.add(new Tuple("pager_communication_service_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "pager.communication.service.ts")))
        apps.add(new Tuple("pager_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "pager.component.html")))
        apps.add(new Tuple("pager_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "pager.component.ts")))
        apps.add(new Tuple("pager_data_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "pager.data.ts")))
        apps.add(new Tuple("pager_service_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "pager.service.ts")))
        apps.add(new Tuple("response_message_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "response.message.component.html")))
        apps.add(new Tuple("response_message_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "response.message.component.ts")))
        apps.add(new Tuple("response_message_helper_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "response.message.helper.ts")))
        apps.add(new Tuple("response_message_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "response.message.ts")))
        apps.add(new Tuple("service_error_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "service.error.ts")))
        apps.add(new Tuple("service_helper_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "service.helper.ts")))
        apps.add(new Tuple("service_results_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "service.results.ts")))
        apps.add(new Tuple("session_data_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "session.data.ts")))
        apps.add(new Tuple("session_helper_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "shared"), "session.helper.ts")))
        apps.add(new Tuple("state_change_communication_service_ts.txt", getPathToViewCode(ScriptHelper
                .createSubpackages("app", "shared"), "state.change.communication.service.ts")))
        apps.add(new Tuple("user_add_data_ts.txt", getPathToViewCode(ScriptHelper
                .createSubpackages("app", "shared"), "user.add.data.ts")))
        apps.add(new Tuple("user_data_ts.txt", getPathToViewCode(ScriptHelper
                .createSubpackages("app", "shared"), "user.data.ts")))
        apps.add(new Tuple("user_service_spec_ts.txt", getPathToViewCode(ScriptHelper
                .createSubpackages("app", "shared"), "user.service.spec.ts")))
        apps.add(new Tuple("user_service_ts.txt", getPathToViewCode(ScriptHelper
                .createSubpackages("app", "shared"), "user.service.ts")))

        ScriptHelper.render("web-view-app-shared-templates", apps)
    }

    private void copyViewAppUserProfileCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("change_password_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "change.password.component.html")))
        apps.add(new Tuple("change_password_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "change.password.component.ts")))
        apps.add(new Tuple("user_add_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.add.component.html")))
        apps.add(new Tuple("user_add_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.add.component.ts")))
        apps.add(new Tuple("user_admin_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.admin.component.html")))
        apps.add(new Tuple("user_admin_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.admin.component.ts")))
        apps.add(new Tuple("user_profile_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.profile.component.html")))
        apps.add(new Tuple("user_profile_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.profile.component.ts")))
        apps.add(new Tuple("user_profile_data_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.profile.data.ts")))
        apps.add(new Tuple("user_profile_service_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.profile.service.ts")))
        apps.add(new Tuple("user_profile_tab_data_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "user-profile"), "user.profile.tab.data.ts")))

        ScriptHelper.render("web-view-app-user-profile-templates", apps)
    }

    private void copyViewAppTabsCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("tab_link_data_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "tabs"), "tab.link.data.ts")))
        apps.add(new Tuple("tab_manager_component_html.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "tabs"), "tab.manager.component.html")))
        apps.add(new Tuple("tab_manager_component_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "tabs"), "tab.manager.component.ts")))
        apps.add(new Tuple("tab_manager_service_ts.txt", getPathToViewCode(ScriptHelper.createSubpackages
                ("app", "tabs"), "tab.manager.service.ts")))

        ScriptHelper.render("web-view-app-tabs-templates", apps)
    }

    private void copyAllFonts() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("app",
                "fonts"))
        copy.copyAll("web-view-app-fonts-templates", destinationFolder)
    }

    private String getPathToViewCode(folderName) {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.render("web.base.path")).append(File.separator)
        buf.append("WebContent").append(File.separator)
        if (folderName != null) {
            buf.append(folderName).append(File.separator)
        }

        buf.toString()
    }

    private String getPathToViewCode(folderName, fileName) {
        StringBuilder buf = new StringBuilder()
        buf.append(getPathToViewCode(folderName))
        buf.append(fileName)

        buf.toString()
    }
}
