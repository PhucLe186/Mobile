const express = require("express");
const router = express.Router();
const InboxController = require("../app/controller/InboxController");

router.get("/messages", InboxController.getMessages);
router.post("/send", InboxController.sendMessage);

module.exports = router;