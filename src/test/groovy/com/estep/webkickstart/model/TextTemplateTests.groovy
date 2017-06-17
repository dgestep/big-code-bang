package com.estep.bigbangcode.model

class TextTemplateTests extends GroovyTestCase {

    void testRenderNestedLevel() {
        def projectBase = ServerProperty.get("project_base_folder")
        def companyName = ServerProperty.get("company_name")
        def productName = ServerProperty.get("product_name")

        def template = '${projectBase}/${companyName}.${productName}'
        def value = TextTemplate.renderDeep(template)
        assert value.equals(projectBase + "/" + companyName + "." + productName)
    }
}
