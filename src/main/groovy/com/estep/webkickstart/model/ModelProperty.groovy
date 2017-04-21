package com.estep.webkickstart.model

root.folder.name = '${companyName}.${productName}'
root.base.path = '${projectBase}/${rootFolderName}'

model.base.path = '${rootBasePath}/${rootFolderName}.model'
web.base.path = '${rootBasePath}/${rootFolderName}.webservice'

applogic.folder.name = '${rootFolderName}.applogic'
applogic.base.root = '${modelBasePath}/${rootFolderName}.applogic'
applogic.base.path = '${applogicBaseRoot}/src'

data.folder.name = '${rootFolderName}.data'
data.base.root = '${modelBasePath}/${rootFolderName}.data'
data.base.path = '${dataBaseRoot}/src'

shared.folder.name = '${rootFolderName}.shared'
shared.base.root = '${modelBasePath}/${rootFolderName}.shared'
shared.base.path = '${sharedBaseRoot}/src'

applogic.aspect = 'com/${companyName}/${productName}/model/aspect'
applogic.service.lookup = 'com/${companyName}/${productName}/model/service/lookup'
applogic.service.security = 'com/${companyName}/${productName}/model/service/security'
applogic.service.user = 'com/${companyName}/${productName}/model/service/user'

model.data.lookup = 'com/${companyName}/${productName}/model/repository/lookup'
model.data.mail = 'com/${companyName}/${productName}/model/repository/mail'
model.data.user = 'com/${companyName}/${productName}/model/repository/user'

model.shared.exception = 'com/${companyName}/${productName}/model/exception'
model.shared.criteria = 'com/${companyName}/${productName}/model/criteria'
model.shared.data = 'com/${companyName}/${productName}/model/data'
model.shared.message = 'com/${companyName}/${productName}/model/enumeration/message'
model.shared.log = 'com/${companyName}/${productName}/model/log'
model.shared.util = 'com/${companyName}/${productName}/model/util'

web.security.path = 'com/${companyName}/${productName}/restcontroller/security'
web.user.path = 'com/${companyName}/${productName}/restcontroller/user'
