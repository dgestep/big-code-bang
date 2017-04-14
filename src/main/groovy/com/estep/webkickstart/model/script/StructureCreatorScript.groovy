package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.Property
import com.estep.webkickstart.model.StructureManager
import com.estep.webkickstart.model.TemplateCopy
import com.estep.webkickstart.model.TextTemplate

StructureManager manager = new StructureManager()
manager.createModelStructure()

copyCheckstyle()
copyPmd()
copyModelGradleRootBuildScript()
copyModelApplLogicGradleRootBuildScript()
copyModelDataGradleRootBuildScript()
copyModelSharedGradleRootBuildScript()
copyModelSpringContextXml()
copyModelTestSpringContextXml()

private void copyCheckstyle() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("model.base.path")
    destination = destination  + File.separator + "config"
    destination = destination + File.separator + "checkstyle"
    destination = destination + File.separator + "proj-checkstyle.xml"
    templateCopy.copy("proj-checkstyle.xml", destination)
}

private void copyPmd() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("model.base.path")
    destination = destination + File.separator + "config"
    destination = destination + File.separator + "rulesets"
    destination = destination + File.separator + "proj-pmd-rules.xml"
    templateCopy.copy("proj-pmd-rules.xml", destination)
}

private void copyModelGradleRootBuildScript() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("model.base.path")
    destination = destination + File.separator + "build.gradle"
    templateCopy.renderAndCopy("model_build_gradle_root.txt", destination)
}

private void copyModelApplLogicGradleRootBuildScript() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("applogic.base.root")
    destination = destination + File.separator + "build.gradle"
    templateCopy.renderAndCopy("model_applogic_gradle_root.txt", destination)
}

private void copyModelDataGradleRootBuildScript() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("data.base.root")
    destination = destination + File.separator + "build.gradle"
    templateCopy.renderAndCopy("model_data_gradle_root.txt", destination)
}

private void copyModelSharedGradleRootBuildScript() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("shared.base.root")
    destination = destination + File.separator + "build.gradle"
    templateCopy.renderAndCopy("model_shared_gradle_root.txt", destination)
}

private void copyModelSpringContextXml() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("applogic.base.path")
    destination = destination + File.separator + "main"
    destination = destination + File.separator + "resources"
    destination = destination + File.separator + "model-spring-context.xml"
    templateCopy.renderAndCopy("model_spring_context_xml.txt", destination)
}

private void copyModelTestSpringContextXml() {
    TemplateCopy templateCopy = new TemplateCopy()
    String destination = render("data.base.path")
    destination = destination + File.separator + "test"
    destination = destination + File.separator + "resources"
    destination = destination + File.separator + "test-model-spring-context.xml"
    templateCopy.renderAndCopy("model_test_spring_context_xml.txt", destination)
}

private String render(propertyName) {
    TextTemplate.render(Property.get(propertyName), 3)
}
