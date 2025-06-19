const  connectDB  = require('../../Config/sqlServer.js');
class TestController {
  async data(req, res) {
      try {
      const connection = await connectDB();
      
      const [rows] = await connection.execute(`
        SELECT 
          v.video_id,
          v.video_url,
          v.caption,
          u.username,
          u.avatar_url,
          COUNT(DISTINCT l.user_id) AS like_count,
          COUNT(DISTINCT c.comment_id) AS comment_count
        FROM Videos v
        JOIN Users u ON v.user_id = u.user_id
        LEFT JOIN Likes l ON v.video_id = l.video_id
        LEFT JOIN Comments c ON v.video_id = c.video_id
        GROUP BY v.video_id, v.video_url, v.caption, u.username, u.avatar_url
        ORDER BY v.created_at DESC
      `);

      await connection.end();
      res.json(rows);

    } catch (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
}
}


module.exports = new TestController();


