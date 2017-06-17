package com.estep.bigcodebang.model

root.folder.name = '${companyName}-${productName}'
root.base.path = '${projectBase}/${rootFolderName}'

model.folder.name = '${rootFolderName}-model'
model.base.path = '${rootBasePath}/${modelFolderName}'

web.folder.name = '${rootFolderName}-web'
web.base.path = '${rootBasePath}/${webFolderName}'

applogic.folder.name = '${rootFolderName}-applogic'
applogic.base.root = '${modelBasePath}/${rootFolderName}-applogic'
applogic.base.path = '${applogicBaseRoot}/src'

data.folder.name = '${rootFolderName}-data'
data.base.root = '${modelBasePath}/${rootFolderName}-data'
data.base.path = '${dataBaseRoot}/src'

shared.folder.name = '${rootFolderName}-shared'
shared.base.root = '${modelBasePath}/${rootFolderName}-shared'
shared.base.path = '${sharedBaseRoot}/src'

applogic.aspect = '${topLevelDomain}/${companyName}/${productName}/model/aspect'
applogic.service.lookup = '${topLevelDomain}/${companyName}/${productName}/model/service/lookup'
applogic.service.security = '${topLevelDomain}/${companyName}/${productName}/model/service/security'
applogic.service.user = '${topLevelDomain}/${companyName}/${productName}/model/service/user'

model.data.lookup = '${topLevelDomain}/${companyName}/${productName}/model/repository/lookup'
model.data.mail = '${topLevelDomain}/${companyName}/${productName}/model/repository/mail'
model.data.user = '${topLevelDomain}/${companyName}/${productName}/model/repository/user'

model.shared.exception = '${topLevelDomain}/${companyName}/${productName}/model/exception'
model.shared.criteria = '${topLevelDomain}/${companyName}/${productName}/model/criteria'
model.shared.data = '${topLevelDomain}/${companyName}/${productName}/model/data'
model.shared.message = '${topLevelDomain}/${companyName}/${productName}/model/enumeration/message'
model.shared.log = '${topLevelDomain}/${companyName}/${productName}/model/log'
model.shared.util = '${topLevelDomain}/${companyName}/${productName}/model/util'

web.security.path = '${topLevelDomain}/${companyName}/${productName}/restcontroller/security'
web.user.path = '${topLevelDomain}/${companyName}/${productName}/restcontroller/user'
