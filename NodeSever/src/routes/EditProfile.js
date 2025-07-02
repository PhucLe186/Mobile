const express = require('express');
const route = express.Router();
const upload = require('../Config/update');

const EditProfileController = require('../app/controller/EditProfileController');

route.post('/avata', upload.single("image"), EditProfileController.uploadIMG)
route.post('/', EditProfileController.getInfor)


module.exports = route;