const db = require('../../Config/sqlServer.js');

class InboxController {
  
  // Lấy danh sách tin nhắn liên quan đến một user
  async getMessages(req, res) {
    const { userId } = req.query; // Hoặc lấy từ req.user nếu có xác thực

    if (!userId) {
      return res.status(400).json({ error: 'Thiếu userId' });
    }

    try {
      const connection = await db();
      const [rows] = await connection.execute(
        `SELECT * FROM Messages 
         WHERE sender_id = ? OR receiver_id = ? 
         ORDER BY sent_at DESC`,
        [userId, userId]
      );
      res.status(200).json(rows);
    } catch (error) {
      console.error('Lỗi khi lấy tin nhắn:', error);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }

  // Gửi tin nhắn mới
  async sendMessage(req, res) {
    const { sender_id, receiver_id, message } = req.body;

    if (!sender_id || !receiver_id || !message) {
      return res.status(400).json({ error: 'Thiếu sender_id, receiver_id hoặc message' });
    }

    try {
      const connection = await db();
      const [result] = await connection.execute(
        `INSERT INTO Messages (sender_id, receiver_id, message)
         VALUES (?, ?, ?)`,
        [sender_id, receiver_id, message]
      );

      res.status(201).json({
        message: 'Gửi tin nhắn thành công',
        data: {
          message_id: result.insertId,
          sender_id,
          receiver_id,
          message,
        },
      });
    } catch (error) {
      console.error('Lỗi khi gửi tin nhắn:', error);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }
}

module.exports = new InboxController();
