
const Test= require('./Test')

function route(app) {
  
  app.use('/test', Test);



}

module.exports = route;
