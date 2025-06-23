const db = require('../../Config/sqlServer.js');

class InboxController {
  
  // Lấy danh sách tin nhắn
  async getMessages(req, res) {
    try {
      const connection = await db();
      const [rows] = await connection.execute('SELECT * FROM messages ORDER BY id DESC');
      res.status(200).json(rows);
    } catch (error) {
      console.error('Lỗi khi lấy tin nhắn:', error);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }

  // Gửi tin nhắn mới
  async sendMessage(req, res) {
    const { sender, content } = req.body;

    if (!sender || !content) {
      return res.status(400).json({ error: 'Thiếu thông tin sender hoặc content' });
    }

    try {
      const connection = await db();
      const result = await connection.execute(
        'INSERT INTO messages (sender, content) VALUES (?, ?)',
        [sender, content]
      );

      res.status(201).json({
        message: 'Gửi tin nhắn thành công',
        data: { id: result[0].insertId, sender, content }
      });
    } catch (error) {
      console.error('Lỗi khi gửi tin nhắn:', error);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }
}

module.exports = new InboxController();
