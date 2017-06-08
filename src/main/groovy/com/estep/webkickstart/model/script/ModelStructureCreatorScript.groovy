package com.estep.webkickstart.model.script

import com.estep.webkickstart.model.TemplateCopy
import com.estep.webkickstart.model.Tuple

class ModelStructureCreatorScript {
    ModelStructureCreatorScript() {
    }

    void execute() {
        copyModelGradleRootBuildScript()

        copyModelApplLogicGradleRootBuildScript()
        copyModelDataGradleRootBuildScript()
        copyModelSharedGradleRootBuildScript()

        copyModelSpringContextXml()
        copyModelTestSpringContextXml()

        copyModelProjectCode()
        copySharedProjectCode()
        copyAppLogicServiceCode()
    }

    private void copyModelGradleRootBuildScript() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("model.base.path"))
        buf.append(File.separator).append("build.gradle")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("model_build_gradle_root.txt", buf.toString())
    }

    private void copyModelApplLogicGradleRootBuildScript() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("applogic.base.root"))
        buf.append(File.separator).append("build.gradle")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("model_applogic_gradle_root.txt", buf.toString())
    }

    private void copyModelDataGradleRootBuildScript() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("data.base.root"))
        buf.append(File.separator).append("build.gradle")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("model_data_gradle_root.txt", buf.toString())
    }

    private void copyModelSharedGradleRootBuildScript() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("shared.base.root"))
        buf.append(File.separator).append("build.gradle")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("model_shared_gradle_root.txt", buf.toString())
    }

    private void copyModelSpringContextXml() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("applogic.base.path"))
        buf.append(File.separator).append("main")
        buf.append(File.separator).append("resources")
        buf.append(File.separator).append("model-spring-context.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("model_spring_context_xml.txt", buf.toString())
    }

    private void copyModelTestSpringContextXml() {
        StringBuilder buf = new StringBuilder()
        buf.append(ScriptHelper.serverRender("data.base.path"))
        buf.append(File.separator).append("test")
        buf.append(File.separator).append("resources")
        buf.append(File.separator).append("test-model-spring-context.xml")

        TemplateCopy templateCopy = new TemplateCopy()
        templateCopy.renderAndCopy("model_test_spring_context_xml.txt", buf.toString())
    }

    private void copyModelProjectCode() {
        copyCodeModelRepositoryPackage()
        copyCodeModelRepositoryTestPackage()

        copyCodeModelRepositoryLookupPackage()
        copyCodeModelRepositoryLookupTestPackage()

        copyCodeModelRepositoryMailPackage()

        copyCodeModelRepositoryUserPackage()
        copyCodeModelRepositoryUserTestPackage()
    }

    private void copyCodeModelRepositoryPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("ApplicationRepository.java", getPathToRepoCode("main", null, "ApplicationRepository.java")))
        apps.add(new Tuple("ApplicationRepositoryImpl.java", getPathToRepoCode("main", null, "ApplicationRepositoryImpl.java")))
        apps.add(new Tuple("CrudRepository.java", getPathToRepoCode("main", null, "CrudRepository.java")))
        apps.add(new Tuple("CrudRepositoryImpl.java", getPathToRepoCode("main", null, "CrudRepositoryImpl.java")))
        apps.add(new Tuple("CruRepository.java", getPathToRepoCode("main", null, "CruRepository.java")))
        apps.add(new Tuple("CruRepositoryImpl.java", getPathToRepoCode("main", null, "CruRepositoryImpl.java")))
        apps.add(new Tuple("DataSet.java", getPathToRepoCode("main", null, "DataSet.java")))
        apps.add(new Tuple("package-info.java", getPathToRepoCode("main", null, "package-info.java")))
        apps.add(new Tuple("RepositoryHelper.java", getPathToRepoCode("main", null, "RepositoryHelper.java")))
        apps.add(new Tuple("ResultSetManager.java", getPathToRepoCode("main", null, "ResultSetManager.java")))

        ScriptHelper.render("data-repository-templates", apps)
    }

    private void copyCodeModelRepositoryTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestDataSet.java", getPathToRepoCode("test", null, "TestDataSet.java")))
        apps.add(new Tuple("ApplicationTestCase.java", getPathToRepoCode("test", null, "ApplicationTestCase.java")))

        ScriptHelper.render("data-repository-test-templates", apps)
    }

    private void copyCodeModelRepositoryLookupPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("LookupKeyValueRepository.java", getPathToRepoCode("main", "lookup", "LookupKeyValueRepository.java")))
        apps.add(new Tuple("LookupKeyValueRepositoryImpl.java", getPathToRepoCode("main", "lookup", "LookupKeyValueRepositoryImpl.java")))
        apps.add(new Tuple("package-info.java", getPathToRepoCode("main", "lookup", "package-info.java")))

        ScriptHelper.render("data-repository-lookup-templates", apps)
    }

    private void copyCodeModelRepositoryLookupTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestLookupKeyValueRepository.java", getPathToRepoCode("test", "lookup", "TestLookupKeyValueRepository.java")))

        ScriptHelper.render("data-repository-test-lookup-templates", apps)
    }

    private void copyCodeModelRepositoryMailPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("MailRepository.java", getPathToRepoCode("main", "mail", "MailRepository.java")))
        apps.add(new Tuple("MailRepositoryImpl.java", getPathToRepoCode("main", "mail", "MailRepositoryImpl.java")))
        apps.add(new Tuple("package-info.java", getPathToRepoCode("main", "mail", "package-info.java")))

        ScriptHelper.render("data-repository-mail-templates", apps)
    }

    private void copyCodeModelRepositoryUserPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("JasyptPasswordValidatorImpl.java", getPathToRepoCode("main", "user","JasyptPasswordValidatorImpl.java")))
        apps.add(new Tuple("package-info.java", getPathToRepoCode("main", "user", "package-info.java")))
        apps.add(new Tuple("PasswordGeneratorRepository.java", getPathToRepoCode("main", "user", "PasswordGeneratorRepository.java")))
        apps.add(new Tuple("PasswordGeneratorRepositoryImpl.java", getPathToRepoCode("main", "user", "PasswordGeneratorRepositoryImpl.java")))
        apps.add(new Tuple("PasswordValidator.java", getPathToRepoCode("main", "user", "PasswordValidator.java")))
        apps.add(new Tuple("UserCredentialRepository.java", getPathToRepoCode("main", "user", "UserCredentialRepository.java")))
        apps.add(new Tuple("UserCredentialRepositoryImpl.java", getPathToRepoCode("main", "user", "UserCredentialRepositoryImpl.java")))
        apps.add(new Tuple("UserCredentialValidator.java", getPathToRepoCode("main", "user", "UserCredentialValidator.java")))
        apps.add(new Tuple("UserCredentialValidatorImpl.java", getPathToRepoCode("main", "user", "UserCredentialValidatorImpl.java")))
        apps.add(new Tuple("UserRepository.java", getPathToRepoCode("main", "user", "UserRepository.java")))
        apps.add(new Tuple("UserRepositoryImpl.java", getPathToRepoCode("main", "user", "UserRepositoryImpl.java")))

        ScriptHelper.render("data-repository-user-templates", apps)
    }

    private void copyCodeModelRepositoryUserTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestJasyptEncryptorRepository.java", getPathToRepoCode("test", "user", "TestJasyptEncryptorRepository.java")))
        apps.add(new Tuple("TestPasswordGeneratorRepository.java", getPathToRepoCode("test", "user", "TestPasswordGeneratorRepository.java")))
        apps.add(new Tuple("TestUserCredentialValidator.java", getPathToRepoCode("test", "user", "TestUserCredentialValidator.java")))
        apps.add(new Tuple("TestUserRepository.java", getPathToRepoCode("test", "user", "TestUserRepository.java")))

        ScriptHelper.render("data-repository-test-user-templates", apps)
    }

    private void copySharedProjectCode() {
        copyCodeSharedPackage()
        copyCodeSharedTestPackage()

        copyCodeSharedCriteriaPackage()
        copyCodeSharedDataPackage()
        copyCodeSharedEnumerationPackage()

        copyCodeSharedExceptionPackage()
        copyCodeSharedExceptionTestPackage()

        copyCodeSharedLogPackage()
        copyCodeSharedLogTestPackage()

        copyCodeSharedUtilPackage()
        copyCodeSharedUtilTestPackage()
    }

    private void copyCodeSharedPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("ConfigConstant.java", getPathToSharedCode("main", null, "ConfigConstant.java")))
        apps.add(new Tuple("EnvironmentConfiguration.java", getPathToSharedCode("main", null, "EnvironmentConfiguration.java")))
        apps.add(new Tuple("JsonResponseData.java", getPathToSharedCode("main", null, "JsonResponseData.java")))
        apps.add(new Tuple("ModelHelper.java", getPathToSharedCode("main", null, "ModelHelper.java")))
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", null, "package-info.java")))
        apps.add(new Tuple("RoleConstant.java", getPathToSharedCode("main", null, "RoleConstant.java")))
        apps.add(new Tuple("TomcatJndiTemplate.java", getPathToSharedCode("main", null, "TomcatJndiTemplate.java")))

        ScriptHelper.render("shared-templates", apps)
    }

    private void copyCodeSharedTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestEnvironmentConfiguration.java", getPathToSharedCode("test", null, "TestEnvironmentConfiguration.java")))
        apps.add(new Tuple("TestJsonResponseData.java", getPathToSharedCode("test", null, "TestJsonResponseData.java")))
        apps.add(new Tuple("TestModelHelper.java", getPathToSharedCode("test", null, "TestModelHelper.java")))

        ScriptHelper.render("shared-test-templates", apps)
    }

    private void copyCodeSharedCriteriaPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("UserSearchCriteriaData.java", getPathToSharedCode("main", "criteria", "UserSearchCriteriaData.java")))
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", "criteria", "package-info.java")))

        ScriptHelper.render("shared-criteria-templates", apps)
    }

    private void copyCodeSharedDataPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("LookupKeyValue.java", getPathToSharedCode("main", "data", "LookupKeyValue.java")))
        apps.add(new Tuple("LookupKeyValuePK.java", getPathToSharedCode("main", "data", "LookupKeyValuePK.java")))
        apps.add(new Tuple("MessageData.java", getPathToSharedCode("main", "data", "MessageData.java")))
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", "data", "package-info.java")))
        apps.add(new Tuple("RoleConverter.java", getPathToSharedCode("main", "data", "RoleConverter.java")))
        apps.add(new Tuple("UserCredential.java", getPathToSharedCode("main", "data", "UserCredential.java")))
        apps.add(new Tuple("UserProfile.java", getPathToSharedCode("main", "data", "UserProfile.java")))
        apps.add(new Tuple("UserData.java", getPathToSharedCode("main", "data", "UserData.java")))
        apps.add(new Tuple("UserRole.java", getPathToSharedCode("main", "data", "UserRole.java")))
        apps.add(new Tuple("UserToken.java", getPathToSharedCode("main", "data", "UserToken.java")))

        ScriptHelper.render("shared-data-templates", apps)
    }

    private void copyCodeSharedExceptionPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("DataInputException.java", getPathToSharedCode("main", "exception", "DataInputException.java")))
        apps.add(new Tuple("SystemLoggedException.java", getPathToSharedCode("main", "exception", "SystemLoggedException.java")))
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", "exception", "package-info.java")))

        ScriptHelper.render("shared-exception-templates", apps)
    }

    private void copyCodeSharedExceptionTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestDataAccessException.java", getPathToSharedCode("test", "exception", "TestDataAccessException.java")))
        apps.add(new Tuple("TestSystemLoggedException.java", getPathToSharedCode("test", "exception", "TestSystemLoggedException.java")))

        ScriptHelper.render("shared-test-exception-templates", apps)
    }

    private void copyCodeSharedLogPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("Log4JLoggerImpl.java", getPathToSharedCode("main", "log", "Log4JLoggerImpl.java")))
        apps.add(new Tuple("LogFactory.java", getPathToSharedCode("main", "log", "LogFactory.java")))
        apps.add(new Tuple("Logger.java", getPathToSharedCode("main", "log", "Logger.java")))
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", "log", "package-info.java")))

        ScriptHelper.render("shared-log-templates", apps)
    }

    private void copyCodeSharedLogTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestLog4jLoggerImpl.java", getPathToSharedCode("test", "log", "TestLog4jLoggerImpl.java")))

        ScriptHelper.render("shared-test-log-templates", apps)
    }

    private void copyCodeSharedUtilPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("NameParser.java", getPathToSharedCode("main", "util", "NameParser.java")))
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", "util", "package-info.java")))

        ScriptHelper.render("shared-util-templates", apps)
    }

    private void copyCodeSharedUtilTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestNameParser.java", getPathToSharedCode("test", "util", "TestNameParser.java")))

        ScriptHelper.render("shared-test-util-templates", apps)
    }

    private void copyCodeSharedEnumerationPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main", "enumeration", "package-info.java")))
        apps.add(new Tuple("Region.java", getPathToSharedCode("main", "enumeration", "Region.java")))
        apps.add(new Tuple("Role.java", getPathToSharedCode("main", "enumeration", "Role.java")))

        ScriptHelper.render("shared-enumeration-templates", apps)

        apps = new ArrayList<>()
        apps.add(new Tuple("package-info.java", getPathToSharedCode("main",  ScriptHelper.createSubpackages("enumeration",
                "message"),"package-info.java")))
        apps.add(new Tuple("ServiceMessage.java", getPathToSharedCode("main",  ScriptHelper.createSubpackages("enumeration",
                "message"),"ServiceMessage.java")))
        apps.add(new Tuple("GeneralMessage.java", getPathToSharedCode("main",  ScriptHelper.createSubpackages("enumeration",
                "message"),"GeneralMessage.java")))
        apps.add(new Tuple("UserMessage.java", getPathToSharedCode("main",  ScriptHelper.createSubpackages("enumeration",
                "message"),"UserMessage.java")))
        apps.add(new Tuple("SecurityMessage.java", getPathToSharedCode("main",  ScriptHelper.createSubpackages("enumeration",
                "message"),"SecurityMessage.java")))

        ScriptHelper.render("shared-enumeration-message-templates", apps)
    }

    private void copyAppLogicServiceCode() {
        copyAppLogicAspectPackage()

        copyAppLogicServicePackage()
        copyAppLogicServiceTestPackage()

        copyAppLogicServiceLookupPackage()
        copyAppLogicServiceLookupTestPackage()

        copyAppLogicServiceSecurityPackage()
        copyAppLogicServiceSecurityTestPackage()

        copyAppLogicServiceUserPackage()
        copyAppLogicServiceUserTestPackage()
    }

    private void copyAppLogicAspectPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("AspectAdvice.java", getPathToAppLogicCode("main", "aspect", "AspectAdvice.java")))
        apps.add(new Tuple("package-info.java", getPathToAppLogicCode("main", "aspect", "package-info.java")))
        apps.add(new Tuple("ServiceAroundAdvice.java", getPathToAppLogicCode("main", "aspect", "ServiceAroundAdvice" +
                ".java")))

        ScriptHelper.render("applogic-aspect-templates", apps)
    }

    private void copyAppLogicServicePackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("CrudService.java", getPathToAppLogicCode("main", "service", "CrudService.java")))
        apps.add(new Tuple("CruService.java", getPathToAppLogicCode("main", "service", "CruService.java")))
        apps.add(new Tuple("EntityAssertion.java", getPathToAppLogicCode("main", "service", "EntityAssertion.java")))
        apps.add(new Tuple("package-info.java", getPathToAppLogicCode("main", "service", "package-info.java")))
        apps.add(new Tuple("SecurityHelper.java", getPathToAppLogicCode("main", "service", "SecurityHelper.java")))

        ScriptHelper.render("applogic-service-templates", apps)
    }

    private void copyAppLogicServiceTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestEntityAssertion.java", getPathToAppLogicCode("test", "service", "TestEntityAssertion.java")))

        ScriptHelper.render("applogic-test-service-templates", apps)
    }

    private void copyAppLogicServiceLookupPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("LookupKeyValueService.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","lookup"), "LookupKeyValueService.java")))
        apps.add(new Tuple("LookupKeyValueServiceImpl.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","lookup"), "LookupKeyValueServiceImpl.java")))
        apps.add(new Tuple("package-info.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","lookup"),"package-info.java")))

        ScriptHelper.render("applogic-service-lookup-templates", apps)
    }

    private void copyAppLogicServiceLookupTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestLookupKeyValueServiceImpl.java", getPathToAppLogicCode("test", ScriptHelper.createSubpackages("service","lookup"), "TestLookupKeyValueServiceImpl.java")))

        ScriptHelper.render("applogic-test-service-lookup-templates", apps)
    }

    private void copyAppLogicServiceSecurityPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("AuthenticationService.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","security"), "AuthenticationService.java")))
        apps.add(new Tuple("AuthenticationServiceImpl.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","security"), "AuthenticationServiceImpl.java")))
        apps.add(new Tuple("package-info.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","security"),"package-info.java")))

        ScriptHelper.render("applogic-security-templates", apps)
    }

    private void copyAppLogicServiceSecurityTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestAuthenticationServiceImpl.java", getPathToAppLogicCode("test", ScriptHelper.createSubpackages("service","security"), "TestAuthenticationServiceImpl.java")))

        ScriptHelper.render("applogic-test-service-security-templates", apps)
    }

    private void copyAppLogicServiceUserPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("package-info.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","user"),"package-info.java")))
        apps.add(new Tuple("UserService.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","user"),"UserService.java")))
        apps.add(new Tuple("UserServiceImpl.java", getPathToAppLogicCode("main", ScriptHelper.createSubpackages("service","user"),"UserServiceImpl.java")))

        ScriptHelper.render("applogic-user-templates", apps)
    }

    private void copyAppLogicServiceUserTestPackage() {
        List<Tuple> apps = new ArrayList<>()
        apps.add(new Tuple("TestUserServiceImpl.java", getPathToAppLogicCode("test", ScriptHelper.createSubpackages("service","user"), "TestUserServiceImpl.java")))

        ScriptHelper.render("applogic-test-service-user-templates", apps)
    }

    private String getPathToAppLogicCode(folderName, subPackage, programName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.serverRender("applogic.base.path"))
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("java")
        buf.append(File.separator).append(ScriptHelper.serverRender("top_level_domain"))
        buf.append(File.separator).append(ScriptHelper.serverRender("company_name"))
        buf.append(File.separator).append(ScriptHelper.serverRender("product_name"))
        buf.append(File.separator).append("model")
        if (subPackage != null) {
            buf.append(File.separator).append(subPackage)
        }

        buf.append(File.separator).append(programName)

        buf.toString()
    }

    private String getPathToRepoCode(folderName, subPackage, programName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.serverRender("data.base.path"))
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("java")
        buf.append(File.separator).append(ScriptHelper.serverRender("top_level_domain"))
        buf.append(File.separator).append(ScriptHelper.serverRender("company_name"))
        buf.append(File.separator).append(ScriptHelper.serverRender("product_name"))
        buf.append(File.separator).append("model")
        buf.append(File.separator).append("repository")
        if (subPackage != null) {
            buf.append(File.separator).append(subPackage)
        }

        buf.append(File.separator).append(programName)

        buf.toString()
    }

    private String getPathToSharedCode(folderName, subPackage, programName) {
        StringBuilder buf = new StringBuilder()

        buf.append(ScriptHelper.serverRender("shared.base.path"))
        buf.append(File.separator).append(folderName)
        buf.append(File.separator).append("java")
        buf.append(File.separator).append(ScriptHelper.serverRender("top_level_domain"))
        buf.append(File.separator).append(ScriptHelper.serverRender("company_name"))
        buf.append(File.separator).append(ScriptHelper.serverRender("product_name"))
        buf.append(File.separator).append("model")
        if (subPackage != null) {
            buf.append(File.separator).append(subPackage)
        }

        buf.append(File.separator).append(programName)

        buf.toString()
    }
}
