const express = require('express');
const route = express.Router();

const searchController = require('../app/controller/SearchController');

 route.get('/search', searchController.search);


module.exports = route;
