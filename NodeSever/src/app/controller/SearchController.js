
const connectDB = require('../../Config/sqlServer.js');

class SearchController {
  async search(req, res) {
    try {
      const keyword = req.query.q || '';
      const connection = await connectDB();

      const [rows] = await connection.execute(
        `SELECT Users.username, Videos.caption
         FROM Videos
         JOIN Users ON Videos.user_id = Users.user_id
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
  async detaiSearch(req,res){
    const {keyword}=req.body;

    console.log(keyword)
    
    const connection = await connectDB();

    const [rows]= await connection.execute(
      `
      SELECT * FROM videos Join users on users.user_id=videos.user_id where users.username like ? or videos.caption like ?
      `, [keyword, keyword])

      res.status(200).json(rows)
  }
}

module.exports = new SearchController();