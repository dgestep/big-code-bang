package com.estep.bigcodebang.model.script

import com.estep.bigcodebang.model.*

ViewStructureManager viewManager = new ViewStructureManager()
viewManager.createViewStructure()

ViewSourceGeneratorScript viewScript = new ViewSourceGeneratorScript();
viewScript.execute()
