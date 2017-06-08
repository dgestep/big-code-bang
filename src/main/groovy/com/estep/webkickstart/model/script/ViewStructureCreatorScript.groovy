package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.Tuple

class ViewStructureCreatorScript {

    void execute() {
        copyRootFiles()
        copyE2eCode()
        copyEnvironments()
        copyAppEnvironment()
    }

    private void copyRootFiles() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("angular_cli_json.txt", getPathToViewCode(null, ".angular-cli.json")))
        apps.add(new Tuple("editorconfig.txt", getPathToViewCode(null, ".editorconfig")))
        apps.add(new Tuple("gitignore.txt", getPathToViewCode(null, ".gitignore")))
        apps.add(new Tuple("karma_conf_js.txt", getPathToViewCode(null, "karma.conf.js")))
        apps.add(new Tuple("package_json.txt", getPathToViewCode(null, "package.json")))
//        apps.add(new Tuple("package_lock_json.txt", getPathToViewCode(null, "package-lock.json")))
        apps.add(new Tuple("protractor_conf_js.txt", getPathToViewCode(null, "protractor.conf.js")))
        apps.add(new Tuple("readme_md.txt", getPathToViewCode(null, "README.md")))
        apps.add(new Tuple("tsconfig_json.txt", getPathToViewCode(null, "tsconfig.json")))
        apps.add(new Tuple("tslint_json.txt", getPathToViewCode(null, "tslint.json")))

        ScriptHelper.render("view-templates", apps)

    }

    private void copyE2eCode() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("app_e2e_spec_ts.txt", getPathToViewCode("e2e", "app.e2e-spec.ts")))
        apps.add(new Tuple("app_po_ts.txt", getPathToViewCode("e2e", "app.po.ts")))
        apps.add(new Tuple("tsconfig_e2e_json.txt", getPathToViewCode("e2e", "tsconfig.e2e.json.ts")))

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
