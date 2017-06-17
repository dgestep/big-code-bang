package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.ServerProperty
import com.estep.webkickstart.model.TextTemplate
import com.estep.webkickstart.model.ViewStructureManager

def prop = ServerProperty.get("project_base_folder")
def projectBasePath = TextTemplate.renderDeep(prop)
File file = new File(projectBasePath)
file.deleteDir()

ViewStructureManager viewManager = new ViewStructureManager()
viewManager.deleteViewStructure()