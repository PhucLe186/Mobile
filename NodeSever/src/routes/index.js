const Test= require('./Test')
const Auth = require('./authRoute')

function route(app) {
  
  app.use('/test', Test);

  //Khang's part (login, register, logout)
  app.use('/api/auth', Auth);

}

module.exports = route;
