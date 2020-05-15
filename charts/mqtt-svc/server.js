
let express = require('express')
let app = express()
let router = express.Router()
let bodyParser = require('body-parser')
let config = require('./config')
let broker = require('./modules/broker.js').getServer()
// Authentication module. 
let auth = require('http-auth')
let morgan = require('morgan')
let basic = auth.basic({
  realm: 'Node JS API',
  file: './keys.htpasswd' // gevorg:gpass, Sarah:testpass ... 
})

app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())
app.use(morgan('dev')) // log every request to the console

if (config.auth === true) {
  app.use(auth.connect(basic))
}

app.all('/*', function (req, res, next) {
  // CORS headers
  res.header('Access-Control-Allow-Origin', '*') // restrict it to the required domain
  res.header('Access-Control-Allow-Methods', 'GET,PUT,POST,DELETE')
  // Set custom headers for CORS
  res.header('Access-Control-Allow-Headers', 'Content-type,Accept,X-Access-Token,X-Key')
  next()
})

router.get('/', function (req, res) {
  res.json({ message: 'MQTT Broker is runnung!' })
})

app.use(function (req, res, next) {
  res.status(404)
  res.send({
    success: 0,
    message: 'Invalid URL'
  })
})

let server = app.listen(config.port, function () {
  console.log(server.address())
  let host = server.address().address
  let port = server.address().port
  console.log('Server listening at http://%s:%s', host, port)
})

broker.attachHttpServer(server)
