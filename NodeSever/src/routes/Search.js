const express = require('express');
const route = express.Router();

const searchController = require('../app/controller/SearchController');

 route.use('/search', searchController.search);
 route.use('/searchdetail', searchController.detaiSearch);


module.exports = route;
