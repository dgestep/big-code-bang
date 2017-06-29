package com.estep.bigcodebang.model

class ModelStructureManagerTests extends GroovyTestCase {
    ServerModelStructureManager manager
    TemplateCopy templateCopy

    void setUp() {
        manager = new ServerModelStructureManager()
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
        String destination = render("root.base.path") + "/config/checkstyle/proj-checkstyle.xml"
        templateCopy.copy("proj-checkstyle.xml", destination)

        assertPath(destination, true)
    }

    private void copyPmd() {
        String destination = render("root.base.path") + "/config/rulesets/proj-pmd-rules.xml"
        templateCopy.copy("proj-pmd-rules.xml", destination)

        assertPath(destination, true)
    }

    private void copyModelGradleRootBuildScript() {
        String destination = render("model.base.path") + "/build.gradle"
        templateCopy.renderAndCopy("model_build_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelApplLogicGradleRootBuildScript() {
        String destination = render("applogic.base.root") + "/build.gradle"
        templateCopy.renderAndCopy("model_applogic_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelDataGradleRootBuildScript() {
        String destination = render("data.base.root") + "/build.gradle"
        templateCopy.renderAndCopy("model_data_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelSharedGradleRootBuildScript() {
        String destination = render("shared.base.root") + "/build.gradle"
        templateCopy.renderAndCopy("model_shared_gradle_root.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelSpringContextXml() {
        String destination = render("applogic.base.path") + "/main/resources/model-spring-context.xml"
        templateCopy.renderAndCopy("model_spring_context_xml.txt", destination)

        assertPath(destination, true)
    }

    private void copyModelTestSpringContextXml() {
        String destination = render("data.base.path") + "/test/resources/test-model-spring-context.xml"
        templateCopy.renderAndCopy("model_test_spring_context_xml.txt", destination)

        assertPath(destination, true)
    }

    private void assertConfigPathsExist(rootBasePath, modelPath, exists) {
        assertPath(modelPath, exists)

        assertPath(rootBasePath + "/config/checkstyle", exists)
        assertPath(rootBasePath + "/config/rulesets", exists)
    }

    private void assertAppLogicPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + '/' + render("applogic.aspect"), exists)
        assertPath(path + '/' + render("applogic.service.lookup"), exists)
        assertPath(path + '/' + render("applogic.service.security"), exists)
        assertPath(path + '/' + render("applogic.service.user"), exists)
    }

    private void assertDataPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + '/' + render("model.data.lookup"), exists)
        assertPath(path + '/' + render("model.data.mail"), exists)
        assertPath(path + '/' + render("model.data.user"), exists)
    }

    private void assertSharedPathsExist(path, exists) {
        assertPath(path, exists)

        assertPath(path + '/' + render("model.shared.exception"), exists)
        assertPath(path + '/' + render("model.shared.criteria"), exists)
        assertPath(path + '/' + render("model.shared.data"), exists)
        assertPath(path + '/' + render("model.shared.message"), exists)
        assertPath(path + '/' + render("model.shared.log"), exists)
    }

    private void assertPath(path, exists) {
        File file = new File(path)
        assert exists == file.exists()
    }

    private String renderValue(propertyName, folderName) {
        def template = ServerProperty.get(propertyName) + '/' + folderName + "/java"
        def path = TextTemplate.renderDeep(template)

        path
    }

    private String render(propertyName) {
        TextTemplate.renderDeep(ServerProperty.get(propertyName))
    }
}
