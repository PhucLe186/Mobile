const connectDB = require('../../Config/sqlServer.js');

class TestController {
  // Lấy toàn bộ dữ liệu từ bảng users
  async data(req, res) {
    try {
      const connection = await connectDB();
      const [rows] = await connection.execute('SELECT * FROM users');
      await connection.end();
      res.json(rows);
    } catch (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }

  // Tìm kiếm theo từ khoá (ví dụ tìm theo tên người dùng)
  async search(req, res) {
    try {
      const keyword = req.query.q || ''; // từ khóa lấy từ query
      const connection = await connectDB();
      const [rows] = await connection.execute(
        'SELECT * FROM users WHERE username LIKE ?',
        [`%${keyword}%`]
      );
      await connection.end();
      res.json(rows);
    } catch (err) {
      console.error('Lỗi tìm kiếm:', err);
      res.status(500).json({ error: 'Lỗi server khi tìm kiếm' });
    }
  }
}

module.exports = new TestController();
