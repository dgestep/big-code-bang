package com.estep.bigbangcode.model

class TemplateCopy {

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

    private String getSourceName(fileName) {
        String sourceFileName = fileName;
        if (!new File(fileName).exists()) {
            URI uri = this.getClass().getClassLoader().getResource(fileName).toURI()
            sourceFileName = new File(uri).absolutePath;
        }
        sourceFileName
    }

    void copyAll(sourceFolder, destination) {
        URI uri = this.getClass().getClassLoader().getResource(sourceFolder).toURI()
        File source = new File(uri)
        File[] files = source.listFiles()
        for (File file : files) {
            String sourceFileName = file.absolutePath;
            String destinationFileName = destination + File.separator + file.name;
            copy(sourceFileName, destinationFileName)
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
