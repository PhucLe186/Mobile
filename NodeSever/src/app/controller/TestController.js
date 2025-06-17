const connectDB = require("../../Config/sqlServer.js");
class TestController {
  async data(req, res) {
    try {
      const connection = await connectDB();
      const [rows] = await connection.execute("SELECT * FROM users");
      await connection.end();
      res.json(rows);
    } catch (err) {
      console.error("Lỗi truy vấn:", err);
      res.status(500).json({ error: "Lỗi server" });
    }
  }
}

module.exports = new TestController();
