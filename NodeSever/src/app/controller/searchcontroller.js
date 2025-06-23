const connectDB = require('../../Config/sqlServer.js');

class SearchController {
  async search(req, res) {
    try {
      const keyword = req.query.q || ''; // Lấy từ ?q=
      const connection = await connectDB();

      const [rows] = await connection.execute(
        'SELECT * FROM users WHERE name LIKE ?',
        [`%${keyword}%`]
      );

      await connection.end();
      res.json(rows);
    } catch (err) {
      console.error('Lỗi tìm kiếm:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }
}

module.exports = new SearchController();