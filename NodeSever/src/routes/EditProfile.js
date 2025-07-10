const express = require('express');
const route = express.Router();
const upload = require('../middleware/uploadImg');

const authenticateToken = require('../middleware/authMiddleware.js');
const EditProfileController = require('../app/controller/EditProfileController');

route.use('/avata', upload.single("image"), EditProfileController.uploadIMG)
route.use('/name', authenticateToken , EditProfileController.changeName)
route.use('/', EditProfileController.getInfor)


module.exports = route;