
const Video= require('./Video')
const Author= require('./author')
const Auth= require('./Auth')
const Follow= require('./Follow')
const Comment = require('./Comment')
const EditProfile= require('./EditProfile')
const Search=require('./Search')
const Message = require('./inbox');

function route(app) {
  
  app.use('/video', Video);
  app.use('/author', Author);
  app.use('/auth', Auth);
  app.use('/users', Follow);
  app.use('/comment', Comment);
  app.use('/editprofile', EditProfile)
  app.use('/api',Search )
  app.use('/messages', Message);

}

module.exports = route;
