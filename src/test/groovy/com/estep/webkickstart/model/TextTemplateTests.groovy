package com.estep.webkickstart.model

class TextTemplateTests extends GroovyTestCase {

    void testRenderNestedLevel() {
        def projectBase = Property.get("project_base_folder")
        def companyName = Property.get("company_name")
        def productName = Property.get("product_name")

        def template = '${projectBase}/${companyName}.${productName}'
        def value = TextTemplate.renderDeep(template, 2)
        assert value.equals(projectBase + "/" + companyName + "." + productName)
    }
}
