package com.estep.bigcodebang.model

class TextTemplateTests extends GroovyTestCase {

    void testRenderNestedLevel() {
        def projectBase = ServerProperty.get("project_base_folder")
        def companyName = ServerProperty.get("company_name")
        def productName = ServerProperty.get("product_name")

        def template = '${projectBase}/${companyName}.${productName}'
        def value = TextTemplate.renderDeep(template)
        assert value.equals(projectBase + "/" + companyName + "." + productName)
    }

    void testRenderNestedLevelWithSlashInValue() {
        def location = ServerProperty.get("application_log_file_location")
        def companyName = ServerProperty.get("company_name")

        def template = '${applicationLogFileLocation}/${companyName}'
        def value = TextTemplate.renderDeep(template)
        assert value.equals(location + "/" + companyName)
    }
}
