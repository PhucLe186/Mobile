const  db  = require('../../Config/sqlServer.js');
require("dotenv").config();
class AuthorController {
  async data(req, res) {
    const { id } = req.params;
    console.log( "id là",id)
      try {
      const connection = await db();
      
      const [rows] = await connection.execute(`
       SELECT videos.*, users.avatar_url, users.username,
       COUNT(DISTINCT likes.user_id) AS like_count,
       COUNT(DISTINCT savedvideos.user_id) AS save_count,
       COUNT(DISTINCT follows.follower_id) AS follower_count,
       COUNT(DISTINCT follows.following_id) AS following_count
        FROM videos
        JOIN Users ON videos.user_id = users.user_id
        LEFT JOIN likes ON videos.video_id = likes.video_id
        LEFT JOIN savedvideos ON videos.video_id = savedvideos.video_id
        LEFT JOIN follows ON users.user_id = follows.following_id
        WHERE users.user_id = ${id}
        GROUP BY videos.video_id;
      `);

      await connection.end();
     if (rows.length > 0) {
        res.json(rows[0]); // Trả về 1 user duy nhất
      } else {
        res.status(404).json({ error: 'Không tìm thấy user' });
      }

    } catch (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
}
}

module.exports = new AuthorController();


