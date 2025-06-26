const express = require('express');
const route = express.Router();
const authenticateToken = require('../middleware/authMiddleware.js');

const authController = require('../app/controller/AuthController');


route.use('/login', authController.Login);
route.use('/checklogin', authController.Checklogin);
route.use('/register', authController.Register);
route.use('/forgotpassword', authController.ForgotPassword);
route.use('/listvideo',authController.Videos);
route.use('/userinfo', authController.GetUserInfo);
route.use('/changepassword',authenticateToken,authController.ChangePassword)

module.exports = route;
