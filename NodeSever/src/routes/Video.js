const express = require('express');
const route = express.Router();

const testController = require('../app/controller/TestController');


route.use('/', testController.data);

module.exports = route;
