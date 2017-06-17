package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.TemplateCopy
import com.estep.webkickstart.model.Tuple

class ViewSourceGeneratorScript {

    void execute() {
        copyRootFiles()
//        copyE2eCode()
        copyEnvironments()

        copyAssets()

        copyBootstrapCssAssets()
        copyBootstrapFontsAssets()
        copyBootstrapJsAssets()

        copyFaCssAssets()
        copyFaFontsAssets()

        copyGoogleAssets()

        copyAppEnvironment()
        copyAppFooter()
        copyAppHeader()
        copyAppHome()
        copyAppSecurity()
        copyAppShared()
        copyAppUser()

        copyApp()
        copySrc()
    }

    private void copyRootFiles() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("angular_cli_json.txt", getPathToViewCode(null, ".angular-cli.json")))
        apps.add(new Tuple("editorconfig.txt", getPathToViewCode(null, ".editorconfig")))
        apps.add(new Tuple("gitignore.txt", getPathToViewCode(null, ".gitignore")))
        apps.add(new Tuple("package_json.txt", getPathToViewCode(null, "package.json")))
        apps.add(new Tuple("readme_md.txt", getPathToViewCode(null, "README.md")))
        apps.add(new Tuple("tsconfig_json.txt", getPathToViewCode(null, "tsconfig.json")))
        apps.add(new Tuple("tslint_json.txt", getPathToViewCode(null, "tslint.json")))

        ScriptHelper.render("view-templates", apps)

    }

    private void copyE2eCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("app_e2e_spec_ts.txt", getPathToViewCode("e2e", "app.e2e-spec.ts")))
        apps.add(new Tuple("app_po_ts.txt", getPathToViewCode("e2e", "app.po.ts")))
        apps.add(new Tuple("tsconfig_e2e_json.txt", getPathToViewCode("e2e", "tsconfig.e2e.json")))

        ScriptHelper.render("view-e2e-templates", apps)
    }

    private void copyEnvironments() {
        def appRoot = "src" + File.separator + "environments"
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("environment_prod_ts.txt", getPathToViewCode(appRoot, "environment.prod.ts")))
        apps.add(new Tuple("environment_ts.txt", getPathToViewCode(appRoot, "environment.ts")))

        ScriptHelper.render("view-environments-templates", apps)
    }

    private void copyAppEnvironment() {
        def appRoot = "src" + File.separator + "app" + File.separator + "environment";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("environment_data_ts.txt", getPathToViewCode(appRoot, "environment.data.ts")))
        apps.add(new Tuple("environment_service_ts.txt", getPathToViewCode(appRoot, "environment.service.ts")))

        ScriptHelper.render("view-app-environment-templates", apps)
    }

    private void copyAppFooter() {
        def appRoot = "src" + File.separator + "app" + File.separator + "footer";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("footer_component_html.txt", getPathToViewCode(appRoot, "footer.component.html")))
        apps.add(new Tuple("footer_component_css.txt", getPathToViewCode(appRoot, "footer.component.css")))
        apps.add(new Tuple("footer_component_ts.txt", getPathToViewCode(appRoot, "footer.component.ts")))

        ScriptHelper.render("view-app-footer-templates", apps)
    }

    private void copyAppHeader() {
        def appRoot = "src" + File.separator + "app" + File.separator + "header";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("header_component_css.txt", getPathToViewCode(appRoot, "header.component.css")))
        apps.add(new Tuple("header_component_html.txt", getPathToViewCode(appRoot, "header.component.html")))
        apps.add(new Tuple("header_component_ts.txt", getPathToViewCode(appRoot, "header.component.ts")))

        ScriptHelper.render("view-app-header-templates", apps)
    }

    private void copyAppHome() {
        def appRoot = "src" + File.separator + "app" + File.separator + "home";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("home_component_html.txt", getPathToViewCode(appRoot, "home.component.html")))
        apps.add(new Tuple("home_component_ts.txt", getPathToViewCode(appRoot, "home.component.ts")))

        ScriptHelper.render("view-app-home-templates", apps)
    }

    private void copyAppSecurity() {
        def appRoot = "src" + File.separator + "app" + File.separator + "security";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("logged_in_guard_ts.txt", getPathToViewCode(appRoot, "logged-in.guard.ts")))
        apps.add(new Tuple("login_component_html.txt", getPathToViewCode(appRoot, "login.component.html")))
        apps.add(new Tuple("login_component_ts.txt", getPathToViewCode(appRoot, "login.component.ts")))
        apps.add(new Tuple("session_lost_component_html.txt", getPathToViewCode(appRoot, "session.lost.component.html")))
        apps.add(new Tuple("session_lost_component_ts.txt", getPathToViewCode(appRoot, "session.lost.component.ts")))

        ScriptHelper.render("view-app-security-templates", apps)
    }

    private void copyAppShared() {
        def appRoot = "src" + File.separator + "app" + File.separator + "shared";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("constants_ts.txt", getPathToViewCode(appRoot, "constants.ts")))
        apps.add(new Tuple("list_result_summary_component_ts.txt", getPathToViewCode(appRoot, "list.result.summary.component.ts")))
        apps.add(new Tuple("name_value_pair_ts.txt", getPathToViewCode(appRoot, "name.value.pair.ts")))
        apps.add(new Tuple("pager_communication_service_ts.txt", getPathToViewCode(appRoot, "pager.communication.service.ts")))
        apps.add(new Tuple("pager_component_html.txt", getPathToViewCode(appRoot, "pager.component.html")))
        apps.add(new Tuple("pager_component_ts.txt", getPathToViewCode(appRoot, "pager.component.ts")))
        apps.add(new Tuple("pager_data_ts.txt", getPathToViewCode(appRoot, "pager.data.ts")))
        apps.add(new Tuple("pager_service_ts.txt", getPathToViewCode(appRoot, "pager.service.ts")))
        apps.add(new Tuple("progress_component_ts.txt", getPathToViewCode(appRoot, "progress.component.ts")))
        apps.add(new Tuple("response_message_ts.txt", getPathToViewCode(appRoot, "response.message.ts")))
        apps.add(new Tuple("service_error_ts.txt", getPathToViewCode(appRoot, "service.error.ts")))
        apps.add(new Tuple("service_results_ts.txt", getPathToViewCode(appRoot, "service.results.ts")))
        apps.add(new Tuple("session_data_ts.txt", getPathToViewCode(appRoot, "session.data.ts")))
        apps.add(new Tuple("session_helper_ts.txt", getPathToViewCode(appRoot, "session.helper.ts")))
        apps.add(new Tuple("tab_data_ts.txt", getPathToViewCode(appRoot, "tab.data.ts")))
        apps.add(new Tuple("tab_manager_service_ts.txt", getPathToViewCode(appRoot, "tab.manager.service.ts")))
        apps.add(new Tuple("view_helper_ts.txt", getPathToViewCode(appRoot, "view.helper.ts")))

        ScriptHelper.render("view-app-shared-templates", apps)
    }

    private void copyAppUser() {
        def appRoot = "src" + File.separator + "app" + File.separator + "user";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("add_profile_component_html.txt", getPathToViewCode(appRoot, "add.profile.component.html")))
        apps.add(new Tuple("change_password_component_html.txt", getPathToViewCode(appRoot, "change.password.component.html")))
        apps.add(new Tuple("edit_profile_component_html.txt", getPathToViewCode(appRoot, "edit.profile.component.html")))
        apps.add(new Tuple("user_add_profile_component_ts.txt", getPathToViewCode(appRoot, "user.add.profile.component.ts")))
        apps.add(new Tuple("user_change_password_component_ts.txt", getPathToViewCode(appRoot, "user.change.password.component.ts")))
        apps.add(new Tuple("user_edit_profile_component_ts.txt", getPathToViewCode(appRoot, "user.edit.profile.component.ts")))
        apps.add(new Tuple("user_list_component_html.txt", getPathToViewCode(appRoot, "user.list.component.html")))
        apps.add(new Tuple("user_list_component_ts.txt", getPathToViewCode(appRoot, "user.list.component.ts")))
        apps.add(new Tuple("user_password_data_ts.txt", getPathToViewCode(appRoot, "user.password.data.ts")))
        apps.add(new Tuple("user_password_form_data_ts.txt", getPathToViewCode(appRoot, "user.password.form.data.ts")))
        apps.add(new Tuple("user_profile_component_html.txt", getPathToViewCode(appRoot, "user.profile.component.html")))
        apps.add(new Tuple("user_profile_component_ts.txt", getPathToViewCode(appRoot, "user.profile.component.ts")))
        apps.add(new Tuple("user_profile_form_data_ts.txt", getPathToViewCode(appRoot, "user.profile.form.data.ts")))
        apps.add(new Tuple("user_profile_ts.txt", getPathToViewCode(appRoot, "user.profile.ts")))
        apps.add(new Tuple("user_service_ts.txt", getPathToViewCode(appRoot, "user.service.ts")))

        ScriptHelper.render("view-app-user-templates", apps)
    }

    private void copyApp() {
        def appRoot = "src" + File.separator + "app";
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("app_component_css.txt", getPathToViewCode(appRoot, "app.component.css")))
        apps.add(new Tuple("app_component_html.txt", getPathToViewCode(appRoot, "app.component.html")))
//        apps.add(new Tuple("app_component_spec_ts.txt", getPathToViewCode(appRoot, "app.component.spec.ts")))
        apps.add(new Tuple("app_component_ts.txt", getPathToViewCode(appRoot, "app.component.ts")))
        apps.add(new Tuple("app_module_ts.txt", getPathToViewCode(appRoot, "app.module.ts")))
        apps.add(new Tuple("app_routes_ts.txt", getPathToViewCode(appRoot, "app.routes.ts")))

        ScriptHelper.render("view-app-templates", apps)
    }

    private void copySrc() {
        def appRoot = "src";

        def tmplFolder = "view-src-templates" + File.separator;
        TemplateCopy copy = new TemplateCopy()
        copy.copy(tmplFolder + "favicon.ico", getPathToViewCode(appRoot, "favicon.ico"))
        copy.copy(tmplFolder + "main.ts", getPathToViewCode(appRoot, "main.ts"))
        copy.copy(tmplFolder + "polyfills.ts", getPathToViewCode(appRoot, "polyfills.ts"))
        copy.copy(tmplFolder + "typings.d.ts", getPathToViewCode(appRoot, "typings.d.ts"))
        copy.copy(tmplFolder + "tsconfig.app.json", getPathToViewCode(appRoot, "tsconfig.app.json"))
//        copy.copy(tmplFolder + "tsconfig.spec.json", getPathToViewCode(appRoot, "tsconfig.spec.json"))
//        copy.copy(tmplFolder + "test.ts", getPathToViewCode(appRoot, "test.ts"))

        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("index_html.txt", getPathToViewCode(appRoot, "index.html")))
        apps.add(new Tuple("styles_css.txt", getPathToViewCode(appRoot, "styles.css")))

        ScriptHelper.render("view-src-templates", apps)
    }

    private void copyAssets() {
        def appRoot = "src" + File.separator + "assets";

        def tmplFolder = "view-assets" + File.separator;
        TemplateCopy copy = new TemplateCopy()
        copy.copy(tmplFolder + "Logo.png", getPathToViewCode(appRoot, "Logo.png"))
        copy.copy(tmplFolder + "tabs.json", getPathToViewCode(appRoot, "tabs.json"))

        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("environment_config_json.txt", getPathToViewCode(appRoot, "environment.config.json")))
        apps.add(new Tuple("environment_production_config_json.txt", getPathToViewCode(appRoot, "environment" +
                ".production.config.json")))

        ScriptHelper.render("view-assets", apps)
    }

    private void copyBootstrapCssAssets() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("src", "assets",
                "bootstrap", "css"))
        copy.copyAll("view-assets-bootstrap-css-templates", destinationFolder)
    }

    private void copyBootstrapFontsAssets() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("src", "assets",
                "bootstrap", "fonts"))
        copy.copyAll("view-assets-bootstrap-fonts-templates", destinationFolder)
    }

    private void copyBootstrapJsAssets() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("src", "assets",
                "bootstrap", "js"))
        copy.copyAll("view-assets-bootstrap-js-templates", destinationFolder)
    }

    private void copyFaCssAssets() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("src", "assets",
                "font-awesome", "css"))
        copy.copyAll("view-assets-font-awesome-css-templates", destinationFolder)
    }

    private void copyFaFontsAssets() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("src", "assets",
                "font-awesome", "fonts"))
        copy.copyAll("view-assets-font-awesome-fonts-templates", destinationFolder)
    }

    private void copyGoogleAssets() {
        TemplateCopy copy = new TemplateCopy()
        String destinationFolder = getPathToViewCode(ScriptHelper.createSubpackages("src", "assets",
                "google"))
        copy.copyAll("view-assets-google-templates", destinationFolder)
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

    private String getPathToViewCode(folderName, fileName) {
        StringBuilder buf = new StringBuilder()
        buf.append(getPathToViewCode(folderName))
        buf.append(fileName)

        buf.toString()
    }
}
