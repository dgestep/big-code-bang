package com.estep.bigcodebang.model.script

import com.estep.bigcodebang.model.TemplateCopy
import com.estep.bigcodebang.model.TextTemplate
import com.estep.bigcodebang.model.Tuple
import com.estep.bigcodebang.model.ViewProperty
import com.estep.bigcodebang.model.ServerProperty
import com.estep.bigcodebang.model.TemplateCopy
import com.estep.bigcodebang.model.TextTemplate
import com.estep.bigcodebang.model.Tuple
import com.estep.bigcodebang.model.ViewProperty

class ScriptHelper {

    static String createSubpackages(startPackage, ...subPackages) {
        StringBuilder buf = new StringBuilder()
        buf.append(startPackage)

        for (String subPackage in subPackages) {
            buf.append(File.separator).append(subPackage)
        }

        buf.toString()
    }

    static String viewRender(propertyName) {
        TextTemplate.renderDeep(ViewProperty.get(propertyName))
    }

    static String serverRender(propertyName) {
        TextTemplate.renderDeep(ServerProperty.get(propertyName))
    }

    static void render(templateFolder, paths) {
        TemplateCopy templateCopy = new TemplateCopy()

        for (Tuple path : paths) {
            StringBuilder template = new StringBuilder().append(templateFolder).append(File.separator).append(path.get(0))

            templateCopy.renderAndCopy(template.toString(), path.get(1))
        }
    }

    static void copy(templateFolder, paths) {
        TemplateCopy templateCopy = new TemplateCopy()

        for (Tuple path : paths) {
            StringBuilder template = new StringBuilder().append(templateFolder).append(File.separator).append(path.get(0))

            templateCopy.copy(template.toString(), path.get(1))
        }
    }
}
