const  db  = require('../../Config/sqlServer.js');
const jwt = require('jsonwebtoken');
class TestController {
  async data(req, res) {
      try {
      const connection = await db();
      
      const [rows] = await connection.execute(`
        SELECT 
            v.video_id,
            v.user_id,                         
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
            GROUP BY 
            v.video_id,
            v.user_id,                           
            v.video_url,
            v.caption,
            u.username,
            u.avatar_url
            ORDER BY v.created_at DESC;
      `);

      await connection.end();
      res.json(rows);

    } catch (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
}

  async getvideobyid(req, res) {
      let userId;
      try {
         const token = req.headers['authorization']?.split(' ')[1];
         if (!token) return res.status(401).json({ message: 'Không có token' });
       try {
          const decoded = jwt.verify(token, process.env.JWT_SECRET);
          userId = decoded.user_id;
      } catch (err) {
          return res.status(401).json({ message: 'Token không hợp lệ' });
      }       
      const connection = await db();
      
      const [rows] = await connection.execute(`
         SELECT 
          v.video_id,
          v.user_id,
          v.video_url,
          v.caption,
          u.username,
          u.avatar_url,
          COUNT(DISTINCT l.user_id) AS like_count,
          COUNT(DISTINCT c.comment_id) AS comment_count,
          EXISTS (
            SELECT 1 FROM Likes l2
            WHERE l2.video_id = v.video_id AND l2.user_id = ${userId}
          ) AS liked
        FROM Videos v
        JOIN Users u ON v.user_id = u.user_id
        LEFT JOIN Likes l ON v.video_id = l.video_id
        LEFT JOIN Comments c ON v.video_id = c.video_id
        GROUP BY 
          v.video_id, v.user_id, v.video_url, v.caption,
          u.username, u.avatar_url
        ORDER BY v.created_at DESC
      `);

      const check=rows.map(user=>{
        return{
          ...user,
          meself: user.user_id===userId?1:0
        }
      })

      await connection.end();
      res.json(check);

    } catch (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
}

  async Likevideo(req, res){
    const connection = await db();
    const { video_id } = req.body;
    console.log(video_id)
    let userId;
      try {
         const token = req.headers['authorization']?.split(' ')[1];
         if (!token) return res.status(401).json({ message: 'Không có token' });
         try {
            const decoded = jwt.verify(token, process.env.JWT_SECRET);
            userId = decoded.user_id;
            console.log(userId)
          } catch (err) {
            return res.status(401).json({ message: 'Token không hợp lệ' });
          }       
          const [rows]=await connection.execute(
            `SELECT * FROM likes WHERE user_id=${userId} AND video_id=${video_id}`
          )
          if(rows.length>0){
            await connection.execute(
              `DELETE FROM likes WHERE user_id=${userId} AND video_id=${video_id}`
            )
            await connection.end();
          }
          else{
             await connection.execute(
              `INSERT INTO likes (user_id, video_id) VALUES (${userId},${video_id})`
            )
            await connection.end();
          }
      }catch (err) {
        console.error('Lỗi truy vấn:', err);
        res.status(500).json({ error: 'Lỗi server' });
    }
  }
}



module.exports = new TestController();


