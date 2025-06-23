const express = require('express');
const route = express.Router();

const authorController = require('../app/controller/AuthorController');


route.use('/:id', authorController.data);

module.exports = route;
