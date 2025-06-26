
const Video= require('./Video')
const Author= require('./author')
const Auth= require('./Auth')
const Follow= require('./Follow')
const Comment = require('./Comment')


function route(app) {
  
  app.use('/video', Video);
  app.use('/author', Author);
  app.use('/auth', Auth);
  app.use('/users', Follow);
  app.use('/comment', Comment);


}

module.exports = route;
