package com.estep.bigcodebang.model

import com.estep.bigcodebang.PackageNameManager
import groovy.text.SimpleTemplateEngine

/**
 * Handles simple text template processing.
 *
 * @author dougestep.
 */
class TextTemplate {
    static TextTemplate INSTANCE
    SimpleTemplateEngine engine
    LinkedHashMap<String, String> bindings

    /**
     * Creates an instance of this class.
     */
    private TextTemplate() {
        PackageNameManager pnm = new PackageNameManager()
        String topLevelDomain = pnm.formatPackageElement(ServerProperty.get("top_level_domain"))
        String companyName = pnm.formatPackageElement(ServerProperty.get("company_name"))
        String productName = pnm.formatPackageElement(ServerProperty.get("product_name"))

        String rawContextRoot = formatProperty(ViewProperty.get("context_root"))
        String formattedContextRoot = rawContextRoot
        if (rawContextRoot.size() != 0) {
            formattedContextRoot = "/" + rawContextRoot
        }

        String localhostPort = formatProperty(ViewProperty.get("localhost_port"))

        String localhostRawPort = localhostPort
        if (localhostPort.size() > 0) {
            localhostPort = ":" + localhostPort
        }
        if (localhostRawPort.size() == 0) {
            localhostRawPort = "80"
        }

        String serverPort = ViewProperty.get("server_port")
        if (!serverPort.equals("")) {
            serverPort = ":" + serverPort
        }

        engine = new SimpleTemplateEngine()
        bindings = [
                "projectBase"               : ServerProperty.get("project_base_folder"),
                "rootFolderName"            : ServerProperty.get("root.folder.name"),
                "rootBasePath"              : ServerProperty.get("root.base.path"),
                "topLevelDomain"            : topLevelDomain,
                "companyName"               : companyName,
                "companyNameLong"           : ServerProperty.get("company_name_long"),
                "productName"               : productName,
                "gradleVersion"             : ServerProperty.get("gradle_version"),
                "modelFolderName"           : ServerProperty.get("model.folder.name"),
                "modelBasePath"             : ServerProperty.get("model.base.path"),
                "webFolderName"             : ServerProperty.get("web.folder.name"),
                "webBasePath"               : ServerProperty.get("web.base.path"),
                "applogicFolderName"        : ServerProperty.get("applogic.folder.name"),
                "applogicBaseRoot"          : ServerProperty.get("applogic.base.root"),
                "applogicBasePath"          : ServerProperty.get("applogic.base.path"),
                "dataFolderName"            : ServerProperty.get("data.folder.name"),
                "dataBaseRoot"              : ServerProperty.get("data.base.root"),
                "dataBasePath"              : ServerProperty.get("data.base.path"),
                "sharedFolderName"          : ServerProperty.get("shared.folder.name"),
                "sharedBaseRoot"            : ServerProperty.get("shared.base.root"),
                "sharedBasePath"            : ServerProperty.get("shared.base.path"),
                "applogicAspect"            : ServerProperty.get("applogic.aspect"),
                "applogicServiceLookup"     : ServerProperty.get("applogic.service.lookup"),
                "applogicServiceSecurity"   : ServerProperty.get("applogic.service.security"),
                "applogicServiceUser"       : ServerProperty.get("applogic.service.user"),
                "javaVersion"               : ServerProperty.get("java_version"),
                "mailHostName"              : ServerProperty.get("mail_host_name"),
                "mailFromAddress"           : ServerProperty.get("mail_from_address"),
                "databaseJndiName"          : ServerProperty.get("database_jndi_name"),
                "databaseDriverClass"       : ServerProperty.get("database_driver_class"),
                "databaseUrl"               : ServerProperty.get("database_url"),
                "databaseUsername"          : ServerProperty.get("database_username"),
                "databasePassword"          : ServerProperty.get("database_password"),
                "codeAuthor"                : ServerProperty.get("code_author"),
                "webWarName"                : ServerProperty.get("web_war_name"),
                "applicationLogFileLocation": ServerProperty.get("application_log_file_location"),
                "viewRootFolderName"        : ViewProperty.get("view_root_folder_name"),
                "applicationTitle"          : ViewProperty.get("application_title"),
                "contextRoot"               : rawContextRoot,
                "formattedContextRoot"      : formattedContextRoot,
                "localhostPort"             : localhostPort,
                "localhostRawPort"          : localhostRawPort,
                "serverHostName"            : ViewProperty.get("server_host_name"),
                "serverPort"                : serverPort
        ]
    }

    /**
     * Returns the property value or empty string if the property value is null.
     * @param propertyValue the value.
     * @return the formatted value.
     */
    private String formatProperty(String propertyValue) {
        if (propertyValue == null) {
            propertyValue = "";
        } else {
            propertyValue = propertyValue.trim()
        }
        propertyValue
    }

    /**
     * Returns an instance of this class.
     * @return the instance.
     */
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
