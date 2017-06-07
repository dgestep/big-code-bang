package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.*

ModelStructureManager manager = new ModelStructureManager()
manager.createModelStructure()

def includeWebProject = "Y".equalsIgnoreCase(ServerProperty.get("web_include_project"))
if (includeWebProject) {
    ViewStructureManager webManager = new ViewStructureManager()
    webManager.createViewStructure()
}

copyCheckstyle()
copyPmd()

ModelStructureCreatorScript modelScript = new ModelStructureCreatorScript()
modelScript.execute()

WebStructureCreatorScript webScript = new WebStructureCreatorScript()
webScript.execute()


private void copyCheckstyle() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.render("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("checkstyle")
    buf.append(File.separator).append("proj-checkstyle.xml")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("proj-checkstyle.xml", buf.toString())
}

private void copyPmd() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.render("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("rulesets")
    buf.append(File.separator).append("proj-pmd-rules.xml")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("proj-pmd-rules.xml", buf.toString())
}
