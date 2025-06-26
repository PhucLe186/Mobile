const db = require('../../Config/sqlServer.js');
exports.getComments = async (video_id) => {
  try {
    const connection = await db(); // Ensure you have a valid connection
    // Query to get comments for the specified video_id
    const [comments] = await connection.execute(
      `SELECT 
        c.*,
        u.username AS username,
        u.avatar_url AS avatar_url
        
        FROM comments c
        INNER JOIN users u ON c.user_id = u.user_id
        WHERE c.video_id = ? ORDER BY c.created_at DESC
        `,
      [video_id]
    );

    // Return the comments if found
    if (comments.length === 0) {
      return { success: false, message: 'No comments found for this video' };
    }
    else{
      return {
        success:true,
        data: comments.map(comment => ({
          
          comment: comment.comment,
          created_at: comment.created_at,
          username: comment.username,
          avatar_url: comment.avatar_url
        }))
      };
    }
  } catch (error) {
    console.error('Error fetching comments:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}
//Get User Avatar Service
exports.getUserInfo = async (userId) => {
  try {
    const connection = await db(); //Connect to the database
    // Query to get user avatar by userId
    const [userinfo] = await connection.execute(`
      SELECT avatar_url,username FROM users WHERE user_id = ?
      `,
      [userId]
    );

    // If a user is found, return the avatar URL
    if (userinfo.length > 0) {
      return {
        avatar_url: userinfo[0].avatar_url || 'Unknown',
        username:userinfo[0].username,
      }
    } else {
      return{
        error: 'User not found'
      }; // No user found
    }
  } catch (error) {
    console.error('Error fetching user avatar:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}

exports.sendComment = async(userId, comment, created_at, video_id) =>{
  try{
    const connection = await db();
    //check If userId is existed ?
    const [userExistedResult] =await connection.execute(`
      SELECT user_id FROM users WHERE user_id = ?
      `,[userId])
    if(userExistedResult.length === 0){
      return {
        success:false,
        message:"User not found"
      }
    }
    
    //check if video is existed?
    const [videoExistedResult] = await connection.execute(`
      SELECT video_id FROM videos WHERE video_id = ?
      `,[video_id])
    if(videoExistedResult.length === 0 ){
      return{
        success:false,
        message:"Video not found",
      }
    }

    await connection.execute(`
      INSERT INTO comments (video_id, user_id, comment,created_at) VALUES (?, ?, ?, ?)
      `,[video_id, userId, comment, created_at])

///////////////////new code Phuc add/////////////////////////////

    const [countResult] = await connection.execute(
      `SELECT COUNT(*) AS total_comments FROM comments WHERE video_id = ?`,
      [video_id]
    );

    const commentCount = countResult[0].total_comments;

    // 5. Trả về kết quả (bao gồm số lượng comment)
    return {
      success: true,
      message: "Comment added successfully",
      commentCount: commentCount
    };

  }
   
   catch (error) {
    console.error('Error during upload Comment:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}