
const Video= require('./Video')
const Author= require('./author')

function route(app) {
  
  app.use('/video', Video);
  app.use('/author', Author);



}

module.exports = route;
