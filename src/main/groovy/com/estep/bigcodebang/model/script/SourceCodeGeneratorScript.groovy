package com.estep.bigcodebang.model.script

import com.estep.bigcodebang.model.*

ServerModelStructureManager modelManager = new ServerModelStructureManager()
modelManager.createModelStructure()

ServerRestServiceStructureManager serverManager = new ServerRestServiceStructureManager()
serverManager.createRestServiceStructure()

ViewStructureManager viewManager = new ViewStructureManager()
viewManager.createViewStructure()

copyCheckstyle()
copyPmd()
copyMySqlImport()
copyReadme()

ServerModelSourceGeneratorScript modelScript = new ServerModelSourceGeneratorScript()
modelScript.execute()

ServerRestServiceSourceGeneratorScript serverRest = new ServerRestServiceSourceGeneratorScript()
serverRest.execute()

ViewSourceGeneratorScript viewScript = new ViewSourceGeneratorScript();
viewScript.execute()

private void copyCheckstyle() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.serverRender("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("checkstyle")
    buf.append(File.separator).append("proj-checkstyle.xml")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("root-server-config-folder-templates/proj-checkstyle.xml", buf.toString())
}

private void copyPmd() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.serverRender("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("rulesets")
    buf.append(File.separator).append("proj-pmd-rules.xml")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("root-server-config-folder-templates/proj-pmd-rules.xml", buf.toString())
}

private void copyMySqlImport() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.serverRender("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("mysql-import.sql")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.copy("root-server-config-folder-templates/mysql-import.sql", buf.toString())
}

private void copyReadme() {
    StringBuilder buf = new StringBuilder()
    buf.append(ScriptHelper.serverRender("root.base.path"))
    buf.append(File.separator).append("config")
    buf.append(File.separator).append("readme.txt")

    TemplateCopy templateCopy = new TemplateCopy()
    templateCopy.renderAndCopy("root-server-config-folder-templates/readme.txt", buf.toString())
}
