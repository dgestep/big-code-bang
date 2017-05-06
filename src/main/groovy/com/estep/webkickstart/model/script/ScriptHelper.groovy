package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.Property
import com.estep.webkickstart.model.TemplateCopy
import com.estep.webkickstart.model.TextTemplate
import com.estep.webkickstart.model.Tuple

class ScriptHelper {

    static String createSubpackages(startPackage, ...subPackages) {
        StringBuilder buf = new StringBuilder()
        buf.append(startPackage)

        for (String subPackage in subPackages) {
            buf.append(File.separator).append(subPackage)
        }

        buf.toString()
    }

    static String render(propertyName) {
        TextTemplate.renderDeep(Property.get(propertyName))
    }

    static void render(templateFolder, paths) {
        TemplateCopy templateCopy = new TemplateCopy()

        for (Tuple path : paths) {
            StringBuilder template = new StringBuilder().append(templateFolder).append(File.separator).append(path.get(0))

            templateCopy.renderAndCopy(template.toString(), path.get(1))
        }
    }
}
