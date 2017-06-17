package com.estep.bigbangcode.model.script

import com.estep.bigbangcode.model.ServerProperty
import com.estep.bigbangcode.model.TextTemplate
import com.estep.bigbangcode.model.ViewStructureManager

def prop = ServerProperty.get("project_base_folder")
def projectBasePath = TextTemplate.renderDeep(prop)
File file = new File(projectBasePath)
file.deleteDir()

ViewStructureManager viewManager = new ViewStructureManager()
viewManager.deleteViewStructure()