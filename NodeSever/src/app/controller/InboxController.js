const db = require('../../Config/sqlServer.js');

class InboxController {
  //  Lấy danh sách tin nhắn
  async getMessages(req, res) {
    const user_id = req.user?.user_id;

    if (!user_id) {
      console.log("Không có user_id trong token:", req.user);
      return res.status(401).json({ error: "Cần xác thực người dùng" });
    }

    try {
      const connection = await db();
      const [rows] = await connection.execute(
        `SELECT 
    Messages.*,
    sender.username AS sender_username,
    sender.avatar_url AS sender_avatar,
    receiver.username AS receiver_username,
    receiver.avatar_url AS receiver_avatar
FROM Messages
JOIN Users AS sender ON Messages.sender_id = sender.user_id
JOIN Users AS receiver ON Messages.receiver_id = receiver.user_id
WHERE Messages.sender_id = ? OR Messages.receiver_id = ?
ORDER BY Messages.sent_at DESC;
`,
        [user_id, user_id]
      );

      if (!rows || rows.length === 0) {
        return res.status(404).json({ message: "Không có tin nhắn nào" });
      }

      return res.status(200).json({
        success: true,
        message: "Lấy tin nhắn thành công",
        data: rows
      });
    } catch (error) {
      console.error("Lỗi khi lấy tin nhắn:", error.message);
      return res.status(500).json({ error: "Lỗi server khi lấy tin nhắn" });
    }
  }

  // Gửi tin nhắn
  async sendMessage(req, res) {
    const sender_id = req.user?.user_id;
    const { receiver_id, message } = req.body;

    if (!sender_id) {
      console.log("Không có sender_id trong token:", req.user);
      return res.status(401).json({ error: "Cần xác thực người dùng" });
    }

    if (!receiver_id || !message) {
      return res.status(400).json({ error: "Thiếu receiver_id hoặc message" });
    }

    try {
      const connection = await db();
      const [result] = await connection.execute(
        `INSERT INTO Messages (sender_id, receiver_id, message, sent_at)
         VALUES (?, ?, ?, CURRENT_TIMESTAMP)`,
        [sender_id, receiver_id, message]
      );

      return res.status(201).json({
        success: true,
        message: "Gửi tin nhắn thành công",
        data: {
          message_id: result.insertId,
          sender_id,
          receiver_id,
          message,
        }
      });
    } catch (error) {
      console.error("Lỗi khi gửi tin nhắn:", error.message);
      return res.status(500).json({ error: "Lỗi server khi gửi tin nhắn" });
    }
  }
}

module.exports = new InboxController();