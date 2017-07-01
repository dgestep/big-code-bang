package com.estep.bigcodebang.model

/**
 * Handles the copying of file information from source to destination.
 *
 * @author dougestep.
 */
class TemplateCopy {

    /**
     * Copies a file.
     * @param fileName the source file.
     * @param destination the destination file.
     */
    void copy(fileName, destination) {
        FileInputStream fis = null
        FileOutputStream fos = null
        try {
            String sourceName = getSourceName(fileName)
            fis = new FileInputStream(sourceName)
            fos = new FileOutputStream(destination)

            byte[] buffer = new byte[1024]
            int noOfBytes = 0

            while ((noOfBytes = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, noOfBytes);
            }
        } finally {
            if (fis != null) {
                fis.close()
            }
            if (fos != null) {
                fos.close()
            }
        }
    }

    /**
     * Returns the absolute file path for the supplied file name.
     * @param fileName the file name.
     * @return the absolute file path.
     */
    private String getSourceName(fileName) {
        String sourceFileName = fileName;
        if (!new File(fileName).exists()) {
            URI uri = this.getClass().getClassLoader().getResource(fileName).toURI()
            sourceFileName = new File(uri).absolutePath;
        }
        sourceFileName
    }

    /**
     * Copies all files from the source folder to the destination folder.
     * @param sourceFolder the source folder.
     * @param destination the destination folder.
     */
    void copyAll(sourceFolder, destination) {
        URI uri = this.getClass().getClassLoader().getResource(sourceFolder).toURI()
        File source = new File(uri)
        File[] files = source.listFiles()
        for (File file : files) {
            String sourceFileName = file.absolutePath;
            String destinationFileName = destination + '/' + file.name;
            copy(sourceFileName, destinationFileName)
        }
    }

    /**
     * Reads the template data from the supplied template, renders the template value replacing the placeholders with
     * actual values, and copies the rendered template to the destination.
     * @param templateName the template name.
     * @param destination the destination.
     */
    void renderAndCopy(templateName, destination) {
        URI uri = this.getClass().getClassLoader().getResource(templateName).toURI()
        String contents = new File(uri).text
        String data = TextTemplate.renderDeep(contents)

        File to = new File(destination);
        to.write(data)
    }
}
