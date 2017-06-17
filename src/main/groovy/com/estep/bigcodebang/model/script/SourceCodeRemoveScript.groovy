package com.estep.bigcodebang.model.script

import com.estep.bigcodebang.model.ServerProperty
import com.estep.bigcodebang.model.TextTemplate
import com.estep.bigcodebang.model.ViewStructureManager

def prop = ServerProperty.get("project_base_folder")
def projectBasePath = TextTemplate.renderDeep(prop)
File file = new File(projectBasePath)
file.deleteDir()

ViewStructureManager viewManager = new ViewStructureManager()
viewManager.deleteViewStructure()