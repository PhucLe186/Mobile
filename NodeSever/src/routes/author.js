const express = require('express');
const route = express.Router();
const upload = require('../middleware/uploadVideo');
const authorController = require('../app/controller/AuthorController');

route.use('/uploadvideo', upload.single('video'), authorController.UploadVideo);
route.use('/follow', authorController.followUser);
route.use('/unfollow', authorController.unfollowUser);
route.use('/:id', authorController.data);

module.exports = route;
