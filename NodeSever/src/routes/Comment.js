const express = require('express');
const route = express.Router();
const authenticateToken = require('../middleware/authMiddleware.js');

const CommentController = require('../app/controller/CommentController');
route.use('/getcomments', CommentController.getComments);
route.use('/getuserinfo',authenticateToken, CommentController.GetUserInfo);
route.use('/uploadcomment',authenticateToken,CommentController.UploadComment);
module.exports = route;