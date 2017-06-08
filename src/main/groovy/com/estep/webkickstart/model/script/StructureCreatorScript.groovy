package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.*

//ModelStructureManager modelManager = new ModelStructureManager()
//modelManager.createModelStructure()
//
//ServerWebStructureManager serverManager = new ServerWebStructureManager()
//serverManager.createWebStructure()

ViewStructureManager viewManager = new ViewStructureManager()
viewManager.createViewStructure()

//copyCheckstyle()
//copyPmd()
//
//ModelStructureCreatorScript modelScript = new ModelStructureCreatorScript()
//modelScript.execute()
//
//ServerWebStructureCreatorScript serverRest = new ServerWebStructureCreatorScript()
//serverRest.execute()

ViewStructureCreatorScript viewScript = new ViewStructureCreatorScript();
viewScript.execute()


private void copyCheckstyle() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.serverRender("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("checkstyle")
    buf.append(File.separator).append("proj-checkstyle.xml")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("proj-checkstyle.xml", buf.toString())
}

private void copyPmd() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.serverRender("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("rulesets")
    buf.append(File.separator).append("proj-pmd-rules.xml")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("proj-pmd-rules.xml", buf.toString())
}
