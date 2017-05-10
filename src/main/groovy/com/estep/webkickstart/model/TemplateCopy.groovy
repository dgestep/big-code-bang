package com.estep.webkickstart.model

class TemplateCopy {

    void copy(fileName, destination) {
        URI uri = this.getClass().getClassLoader().getResource(fileName).toURI()
        String data = new File(uri).text

        File to = new File(destination);
        to.write(data)
    }

    void copyAll(sourceFolder, destination) {
        URI uri = this.getClass().getClassLoader().getResource(sourceFolder).toURI()
        File source = new File(uri)
        File[] files = source.listFiles()
        for (File file : files) {
            String contents = file.text

            File to = new File(destination + File.separator + file.getName())
            to.write(contents)
        }

    }

    void renderAndCopy(templateName, destination) {
        URI uri = this.getClass().getClassLoader().getResource(templateName).toURI()
        String contents = new File(uri).text
        String data = TextTemplate.renderDeep(contents)

        File to = new File(destination);
        to.write(data)
    }
}
