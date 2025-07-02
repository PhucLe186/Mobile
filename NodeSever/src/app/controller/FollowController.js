const  db  = require('../../Config/sqlServer.js');
const jwt = require('jsonwebtoken');

class FollowController {
    async getFollowingList(req, res, next) {
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
        const { userId } = req.params;
        const connection = await db();

        const [users] = await connection.execute(
            `SELECT 
                u.user_id AS id, 
                u.username, 
                u.avatar_url,
                CASE 
                    WHEN f2.follower_id IS NOT NULL THEN 'Bạn bè'
                    ELSE 'Đã follow'
                END AS follow_status
                FROM follows f
                JOIN users u ON f.following_id = u.user_id
                LEFT JOIN follows f2
                    ON f2.follower_id = u.user_id AND f2.following_id = f.follower_id
                WHERE f.follower_id = ?
                ORDER BY f.followed_at DESC`,
            [userId]
        );
        const checkuser=users.map(user=>{
            return {
                ...user,
                myself: user.id===user_id? 1:0
            }
        })

        res.json( checkuser );
    } catch (error) {
        next(error);
    }
}
    async getFollowersList(req, res, next) {
        try {
            const { userId } = req.params;
            const connection = await db();
            const [users] = await connection.execute(
                 `SELECT 
                    u.user_id AS id,
                    u.username,
                    u.avatar_url,
                    CASE 
                        WHEN f2.follower_id IS NOT NULL THEN 'Bạn bè'
                        ELSE 'Follow'
                    END AS follow_status
                    FROM Follows f
                    JOIN Users u ON f.follower_id = u.user_id
                    LEFT JOIN Follows f2 
                        ON f2.follower_id = ? 
                        AND f2.following_id = u.user_id
                    WHERE f.following_id = ?
                    ORDER BY f.followed_at DESC`,
            [userId, userId]
            );
            res.json(users);
        } catch (error) {
            next(error);
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
            const userId =  parseInt(req.body.id);
        
            if(user_id===userId){
                return;
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

    // [DELETE] /api/unfollow
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

            const userId =  parseInt(req.body.id);
        
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

    // [GET] /api/users/check-follow/:followerId/:followingId
    async checkFollowStatus(req, res, next) {
        try {
            const { followerId, followingId } = req.params;

            const [[result]] = await db.execute(
                `SELECT EXISTS(
                    SELECT 1 FROM Follows
                    WHERE follower_id = ? AND following_id = ?
                ) AS isFollowing`,
                [followerId, followingId]
            );

            res.json({ success: true, data: { isFollowing: !!result.isFollowing } });
        } catch (error) {
            next(error);
        }
    }
}

module.exports = new FollowController();