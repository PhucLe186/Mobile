const connectDB = require('../../Config/sqlServer.js');

class SearchController {
  async search(req, res) {
    try {
      const keyword = req.query.q || '';
      const connection = await connectDB();

      const [rows] = await connection.execute(
        `SELECT Users.username, Videos.caption
         FROM Videos
         JOIN Users ON Videos.user_id = Users.id
         WHERE Users.username LIKE ? OR Videos.caption LIKE ?`,
        [`%${keyword}%`, `%${keyword}%`]
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