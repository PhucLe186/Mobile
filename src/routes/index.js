
const Video= require('./Video')
const Author= require('./author')
const Auth= require('./Auth')


function route(app) {
  
  app.use('/video', Video);
  app.use('/author', Author);
  app.use('/auth', Auth);


}

module.exports = route;
