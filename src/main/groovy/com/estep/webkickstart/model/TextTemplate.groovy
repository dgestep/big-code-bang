package com.estep.webkickstart.model

import groovy.text.SimpleTemplateEngine

/**
 * Handles simple text template processing.
 */
class TextTemplate {
    static TextTemplate INSTANCE
    SimpleTemplateEngine engine
    LinkedHashMap<String, String> bindings

    private TextTemplate() {
        engine = new SimpleTemplateEngine()
        bindings = [
                "projectBase"            : Property.get("project_base_folder"),
                "rootFolderName"         : Property.get("root.folder.name"),
                "rootBasePath"           : Property.get("root.base.path"),
                "topLevelDomain"         : Property.get("top_level_domain"),
                "companyName"            : Property.get("company_name"),
                "companyNameLong"        : Property.get("company_name_long"),
                "productName"            : Property.get("product_name"),
                "gradleVersion"          : Property.get("gradle_version"),
                "modelFolderName"        : Property.get("model.folder.name"),
                "modelBasePath"          : Property.get("model.base.path"),
                "webFolderName"          : Property.get("web.folder.name"),
                "webBasePath"            : Property.get("web.base.path"),
                "applogicFolderName"     : Property.get("applogic.folder.name"),
                "applogicBaseRoot"       : Property.get("applogic.base.root"),
                "applogicBasePath"       : Property.get("applogic.base.path"),
                "dataFolderName"         : Property.get("data.folder.name"),
                "dataBaseRoot"           : Property.get("data.base.root"),
                "dataBasePath"           : Property.get("data.base.path"),
                "sharedFolderName"       : Property.get("shared.folder.name"),
                "sharedBaseRoot"         : Property.get("shared.base.root"),
                "sharedBasePath"         : Property.get("shared.base.path"),
                "applogicAspect"         : Property.get("applogic.aspect"),
                "applogicServiceLookup"  : Property.get("applogic.service.lookup"),
                "applogicServiceSecurity": Property.get("applogic.service.security"),
                "applogicServiceUser"    : Property.get("applogic.service.user"),
                "javaVersion"            : Property.get("java_version"),
                "mailHostName"           : Property.get("mail_host_name"),
                "mailFromAddress"        : Property.get("mail_from_address"),
                "databaseJndiName"       : Property.get("database_jndi_name"),
                "databaseDriverClass"    : Property.get("database_driver_class"),
                "databaseUrl"            : Property.get("database_url"),
                "databaseUsername"       : Property.get("database_username"),
                "databasePassword"       : Property.get("database_password"),
                "codeAuthor"             : Property.get("code_author"),
                "applicationTitle"       : Property.get("application_title"),
                "webWarName"             : Property.get("web_war_name")
        ]
    }

    private static TextTemplate instanceOf() {
        if (INSTANCE == null) {
            INSTANCE = new TextTemplate()
        }
        return INSTANCE
    }

    /**
     * Returns the string using the supplied template.
     *
     * @param templateString the template.
     * @return the rendered value.
     */
    static String renderDeep(templateString) {
        TextTemplate tt = instanceOf()

        String value = templateString

        while (true) {
            value = tt.engine.createTemplate(value).make(tt.bindings).toString()
            if (value.indexOf('${') < 0) {
                break;
            }
        }

        value = value.replace("||||", "\${")
        value.replace("~~", "\$")
    }
}
