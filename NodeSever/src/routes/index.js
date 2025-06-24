
const Video= require('./Video')
const Author= require('./author')
const Auth= require('./Auth')
const Inbox = require("./inbox")


function route(app) {
  
  app.use('/video', Video);
  app.use('/author', Author);
  app.use('/auth', Auth);
  app.use('/inbox',Inbox)


}

module.exports = route;