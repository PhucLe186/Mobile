const express = require('express');
const route = express.Router();

const testController = require('../app/controller/VideoController');

route.use('/likes', testController.Likevideo);
route.use('/local', testController.getvideobyid);
route.use('/', testController.data);

module.exports = route;
