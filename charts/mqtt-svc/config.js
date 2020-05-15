let config = {}

config.port = 8001

// Authentication
config.auth = false

// Base Url
// config.baseurl = '/v1/'

// Mysql Database
// config.database = ''
// config.dbHost = ''
// config.test_dbHost = ''
// config.username = ''
// config.password = ''
config.APIHost = 'http://10.70.51.54'
// config.table_prefix = 'api_'
// config.dbPort = 3306
// Key
// config.tokenKey = ''
// config.generalKey = ''
// Zone
config.timezone = 'Asia/Taipei'
// Debug
config.debug = true
config.isLocalDB = true
// Server
config.server = 'http://localhost:' + config.port + '/'
// MQTT
config.mqttPort = 1883
// Add device info default status
// config.defaultStatus = 3
// API to check login
config.userLoginUrl = '/am-svc/login/'
config.deviceLoginUrl = '/device-svc/login'

module.exports = config
