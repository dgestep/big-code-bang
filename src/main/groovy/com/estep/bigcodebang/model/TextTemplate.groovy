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
        localhostPort = prependColon(localhostPort)
        localhostRawPort = appendPort80(localhostRawPort)

        String localhostViewPort = formatProperty(ViewProperty.get("localhost_view_port"))
        String localhostRawViewPort = localhostViewPort
        localhostViewPort = prependColon(localhostViewPort)
        localhostRawViewPort = appendPort80(localhostRawViewPort)

        String serverViewPort = formatProperty(ViewProperty.get("server_view_port"))
        String serverRawViewPort = serverViewPort
        serverViewPort = prependColon(serverViewPort)
        serverRawViewPort = appendPort80(serverRawViewPort)

        String serverPort = prependColon(ViewProperty.get("server_port"))

        engine = new SimpleTemplateEngine()
        bindings = [
                "projectBase"               : ServerProperty.get("project_base_folder"),
                "rootFolderName"            : ServerProperty.get("root.folder.name"),
                "rootBasePath"              : ServerProperty.get("root.base.path"),
                "topLevelDomain"            : topLevelDomain,
                "companyName"               : companyName,
                "companyNameLong"           : defaultValue(ServerProperty.get("company_name_long"), "Big Code Bang " +
                        "Generated Application"),
                "productName"               : productName,
                "gradleVersion"             : defaultValue(ServerProperty.get("gradle_version"), "3.1"),
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
                "javaVersion"               : defaultValue(ServerProperty.get("java_version"), "1.8"),
                "mailHostName"              : defaultValue(ServerProperty.get("mail_host_name"), "localhost"),
                "mailFromAddress"           : defaultValue(ServerProperty.get("mail_from_address"), "webmaster@localhost"),
                "databaseJndiName"          : defaultValue(ServerProperty.get("database_jndi_name"), "jdbc/bcb"),
                "databaseDriverClass"       : defaultValue(ServerProperty.get("database_driver_class"), "com.mysql.cj.jdbc.Driver"),
                "databaseUrl"               : defaultValue(ServerProperty.get("database_url"), "jdbc:mysql://localhost/<my-database-goes-here>"),
                "databaseUsername"          : defaultValue(ServerProperty.get("database_username"),
                        "<my-database-user-goes-here>"),
                "databasePassword"          : defaultValue(ServerProperty.get("database_password"),
                        "my-database-password-goes-here>"),
                "codeAuthor"                : defaultValue(ServerProperty.get("code_author"), "Big Code Bang"),
                "webWarName"                : defaultValue(ServerProperty.get("web_war_name"), "bcb.war"),
                "applicationLogFileLocation": ServerProperty.get("application_log_file_location"),
                "viewRootFolderName"        : ViewProperty.get("view_root_folder_name"),
                "applicationTitle"          : defaultValue(ViewProperty.get("application_title"), "Big Code Bang"),
                "contextRoot"               : rawContextRoot,
                "formattedContextRoot"      : formattedContextRoot,
                "localhostPort"             : localhostPort,
                "localhostRawPort"          : localhostRawPort,
                "localhostViewPort"         : defaultValue(localhostViewPort, ":4200"),
                "localhostRawViewPort"      : localhostRawViewPort,
                "serverHostName"            : ViewProperty.get("server_host_name"),
                "serverPort"                : serverPort,
                "serverViewPort"            : defaultValue(serverViewPort, ""),
                "serverViewRawPort"         : serverRawViewPort
        ]
    }

    private String appendPort80(String port) {
        if (port.size() == 0) {
            port = ":80"
        }
        port
    }

    private String prependColon(String port) {
        if (port.size() > 0) {
            port = ":" + port
        }
        port
    }

    private String defaultValue(String value, String defaultValue) {
        String formattedValue = formatProperty(value)
        if (formattedValue.size() == 0) {
            formattedValue = defaultValue
        }
        formattedValue
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
        INSTANCE
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
