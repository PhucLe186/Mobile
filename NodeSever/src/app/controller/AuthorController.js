const  db  = require('../../Config/sqlServer.js');
const jwt = require('jsonwebtoken');
class AuthorController {
  async data(req, res) {
    const { id } = req.params;
    console.log( "id là",id)
      try {
      const connection = await db();
      const [rows] = await connection.execute(`
       SELECT 
        users.user_id,
        users.avatar_url,
        users.username,
        COUNT(DISTINCT likes.user_id) AS like_count,
        (SELECT COUNT(*) FROM follows WHERE following_id = users.user_id) AS follower_count,
        (SELECT COUNT(*) FROM follows WHERE follower_id = users.user_id) AS following_count,
        CASE 
            WHEN EXISTS (
                SELECT 1 FROM follows 
                WHERE follower_id = 3 AND following_id = users.user_id
            ) THEN 1 
            ELSE 0 
        END AS is_following
        FROM users
        LEFT JOIN videos ON users.user_id = videos.user_id
        LEFT JOIN likes ON videos.video_id = likes.video_id
        WHERE users.user_id = ${id}
        GROUP BY users.user_id, users.avatar_url, users.username;`);

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
  async followUser(req, res, next) {
        try {
            let user_id;
            const token = req.headers['authorization']?.split(' ')[1];
            if (!token) return res.status(401).json({ message: 'Không có token' });
            try {
                const decoded = jwt.verify(token, process.env.JWT_SECRET);
                user_id = decoded.user_id;
            } catch (err) {
                return res.status(401).json({ message: 'Token không hợp lệ' });
            } 
            const userId = req.body.user_id;
           
        
            if(user_id===userId){
                return res.status(400).json({ error: "Cannot follow yourself" });
            }
            const connection = await db();
            const [[existing]] = await connection.execute(
                `SELECT * FROM Follows WHERE follower_id = ? AND following_id = ?`,
                [user_id, userId]
            );
            if (existing) return res.status(400).json({ error: 'Already following this user' });
            await connection.execute(
                `INSERT INTO follows (follower_id, following_id) VALUES (?, ?)`,
                [user_id, userId]
            );

            res.status(201).json({ success: true, message: 'Followed successfully' });
        } catch (error) {
            next(error);
        }
    }
    async unfollowUser(req, res, next) {
        try {
            let user_id;
            const token = req.headers['authorization']?.split(' ')[1];
            if (!token) return res.status(401).json({ message: 'Không có token' });
            try {
                const decoded = jwt.verify(token, process.env.JWT_SECRET);
                user_id = decoded.user_id;
            } catch (err) {
                return res.status(401).json({ message: 'Token không hợp lệ' });
            } 

            const userId =  parseInt(req.body.user_id);
            console.log(userId)
        
            const connection = await db();
            
            const [result] = await connection.execute(
                `DELETE FROM Follows WHERE follower_id = ? AND following_id = ?`,
                [user_id, userId]
            );
            res.json({ success: true, message: 'Unfollowed successfully' });
        } catch (error) {
            next(error);
        }
    }
    async UploadVideo(req, res){
        const video = req.file;
        const { caption }= req.body;
        let user_id;
       
        if (!video||!caption) {
        return res.status(400).json({ message: 'Vui lòng tải lên video' });
        }

        const token = req.headers['authorization']?.split(' ')[1];
        if (!token) return res.status(401).json({ message: 'Không có token' });
        try {
            const decoded = jwt.verify(token, process.env.JWT_SECRET);
            user_id = decoded.user_id;
        } catch (err) {
            return res.status(401).json({ message: 'Token không hợp lệ' });
        }
        const video_url=video.path
        const connection=await db()
        await connection.execute(
            `INSERT INTO videos (user_id, video_url, caption) VALUES (?, ?, ?)`,
            [user_id, video_url, caption]
        )
       
        return res.status(200).json({ message: 'Upload thành công' });  
    
    }
}
module.exports = new AuthorController();


