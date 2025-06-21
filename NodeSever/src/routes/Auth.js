const express = require('express');
const route = express.Router();

const authController = require('../app/controller/AuthController');


route.use('/login', authController.Login);
route.use('/register', authController.Register);
route.use('/checklogin', authController.Checklogin);
route.use('/forgotpassword', authController.ForgotPassword);



module.exports = route;
