const express = require('express');
const searchController = require('../app/controllers/searchcontroller'); 

function route(app) {
  const router = express.Router();

  router.get('/api/search', searchController.search);

  app.use('/', router);
}

module.exports = route;