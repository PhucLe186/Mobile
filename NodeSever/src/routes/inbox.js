const express = require('express');
const router = express.Router();
const InboxController = require('../app/controller/InboxController');
const authMiddleware = require('../middleware/authMiddleware');

router.get('/getmessages', authMiddleware, InboxController.getMessages);
router.get('/fetchChatMessages', authMiddleware, InboxController.fetchChatMessages);



module.exports = router;