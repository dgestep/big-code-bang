package com.estep.webkickstart.model

model.base.path = '${projectBase}/${companyName}.${productName}/${companyName}.${productName}.model'

applogic.folder.name = '${companyName}.${productName}.applogic'
applogic.base.root = '${modelBasePath}/${companyName}.${productName}.applogic'
applogic.base.path = '${applogicBaseRoot}/src'

data.folder.name = '${companyName}.${productName}.data'
data.base.root = '${modelBasePath}/${companyName}.${productName}.data'
data.base.path = '${dataBaseRoot}/src'

shared.folder.name = '${companyName}.${productName}.shared'
shared.base.root = '${modelBasePath}/${companyName}.${productName}.shared'
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
