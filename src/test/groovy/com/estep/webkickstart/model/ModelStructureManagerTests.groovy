package com.estep.webkickstart.model

class ModelStructureManagerTests extends GroovyTestCase {
    ModelStructureManager manager
    TemplateCopy templateCopy

    void setUp() {
        manager = new ModelStructureManager()
        templateCopy = new TemplateCopy()
    }

    void tearDown() {
    }

    void testCreateModelStructure() {
        manager.createModelStructure()

        def rootBasePath = render("root.base.path")
        def modelBasePath = render("model.base.path")
        assertConfigPathsExist(rootBasePath, modelBasePath, true)

        assertAppLogicStructure()
        assertDataStructure()
        assertSharedStructure()

        copyCheckstyle()
        copyPmd()
        copyModelGradleRootBuildScript()
        copyModelApplLogicGradleRootBuildScript()
        copyModelDataGradleRootBuildScript()
        copyModelSharedGradleRootBuildScript()
        copyModelSpringContextXml()
        copyModelTestSpringContextXml()

        manager.deleteModelStructure()
        assertPath(modelBasePath, false)
    }

    private void assertAppLogicStructure() {
        def mainPath = renderValue("applogic.base.path", "main")
        def testPath = renderValue("applogic.base.path", "test")
        assertAppLogicPathsExist(mainPath, true)
        assertAppLogicPathsExist(testPath, true)
    }

    private void assertDataStructure() {
        def mainPath = renderValue("data.base.path", "main")
        def testPath = renderValue("data.base.path", "test")
        assertDataPathsExist(mainPath, true)
        assertDataPathsExist(testPath, true)
    }

    private void assertSharedStructure() {
        def mainPath = renderValue("shared.base.path", "main")
        def testPath = renderValue("shared.base.path", "test")
        assertSharedPathsExist(mainPath, true)
        assertSharedPathsExist(testPath, true)
    }

    private void copyCheckstyle() {
        String destination = render("root.base.path") + File.separator + "config" + File.separator +
                "checkstyle/proj-checkstyle.xml"
        templateCopy.copy("proj-checkstyle.xml", destination)

        assertPath(destination, true)
    }

    private void copyPmd() {
        String destination = render("root.base.path") + File.separator + "config" + File.separator +
                "rulesets/proj-pmd-rules.xml"
        templateCopy.copy("proj-pmd-rules.xml", destination)

        assertPath(destination, true)
    }

    private void copyModelGradleRootBuildScript() {
        String destination = render("model.base.path") + File.separator + "build.gradle"
        templateCopy.renderAndCopy("model_build_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelApplLogicGradleRootBuildScript() {
        String destination = render("applogic.base.root") + File.separator + "build.gradle"
        templateCopy.renderAndCopy("model_applogic_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelDataGradleRootBuildScript() {
        String destination = render("data.base.root") + File.separator + "build.gradle"
        templateCopy.renderAndCopy("model_data_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelSharedGradleRootBuildScript() {
        String destination = render("shared.base.root") + File.separator + "build.gradle"
        templateCopy.renderAndCopy("model_shared_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelSpringContextXml() {
        String destination = render("applogic.base.path") + File.separator + "main" + File.separator + "resources" +
                File.separator + "model-spring-context.xml"
        templateCopy.renderAndCopy("model_spring_context_xml.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelTestSpringContextXml() {
        String destination = render("data.base.path") + File.separator + "test" + File.separator + "resources" +
                File.separator + "test-model-spring-context.xml"
        templateCopy.renderAndCopy("model_test_spring_context_xml.txt", destination)

        assertPath(destination, true)
    }

    private void assertConfigPathsExist(rootBasePath, modelPath, exists) {
        assertPath(modelPath, exists)

        assertPath(rootBasePath + File.separator + "config" + File.separator + "checkstyle", exists)
        assertPath(rootBasePath + File.separator + "config" + File.separator + "rulesets", exists)
    }

    private void assertAppLogicPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + File.separator + render("applogic.aspect"), exists)
        assertPath(path + File.separator + render("applogic.service.lookup"), exists)
        assertPath(path + File.separator + render("applogic.service.security"), exists)
        assertPath(path + File.separator + render("applogic.service.user"), exists)
    }

    private void assertDataPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + File.separator + render("model.data.lookup"), exists)
        assertPath(path + File.separator + render("model.data.mail"), exists)
        assertPath(path + File.separator + render("model.data.user"), exists)
    }

    private void assertSharedPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + File.separator + render("model.shared.exception"), exists)
        assertPath(path + File.separator + render("model.shared.criteria"), exists)
        assertPath(path + File.separator + render("model.shared.data"), exists)
        assertPath(path + File.separator + render("model.shared.message"), exists)
        assertPath(path + File.separator + render("model.shared.log"), exists)
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }

    private String renderValue(propertyName, folderName) {
        def template = Property.get(propertyName) + File.separator + folderName + File.separator + "java"
        def path = TextTemplate.renderDeep(template)

        path
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(Property.get(propertyName))
    }
}
