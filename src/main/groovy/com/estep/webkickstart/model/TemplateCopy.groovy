package com.estep.webkickstart.model

class TemplateCopy {

    void copy(fileName, destination) {
        URI uri = this.getClass().getClassLoader().getResource(fileName).toURI()
        String data = new File(uri).text

        File to = new File(destination);
        to.write(data)
    }

    void renderAndCopy(templateName, destination) {
        URI uri = this.getClass().getClassLoader().getResource(templateName).toURI()
        String contents = new File(uri).text
        String data = TextTemplate.renderDeep(contents)

        File to = new File(destination);
        to.write(data)
    }
}
