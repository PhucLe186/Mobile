const express = require('express');
const router = express.Router();
const InboxController = require('../app/controller/InboxController');
const authMiddleware = require('../middleware/authMiddleware');

router.get('/getMessages', authMiddleware, InboxController.getMessages);
router.post('/sendMessage', authMiddleware, InboxController.sendMessage);
router.use('/', InboxController.getMessages);

module.exports = router;
