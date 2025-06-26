const db = require('../../Config/sqlServer.js');
//Login Service
exports.login = async (username, password) => {
  try {
    const connection = await db(); // Ensure you have a valid connection
    // Query to find the user by username and password
    const [rows] = await connection.execute(
      'SELECT * FROM users WHERE username = ? AND password = ?',
      [username, password]
    );

    // If a user is found, return the user object
    if (rows.length > 0) {
      return rows[0];
    } else {
      return null; // No user found
    }
  } catch (error) {
    console.error('Error during login:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}
//Register Service
exports.register = async (email, username, password) => {
  try {
    const connection = await db(); // Ensure you have a valid connection
    // Query to check if the user already exists
    const [existingUser] = await connection.execute(
      'SELECT * FROM users WHERE username = ? LIMIT 1',
      [username]
    );
    const [existingEmail] = await connection.execute(
      'SELECT * FROM users WHERE email = ? LIMIT 1',
      [email]
    );
    // If the user already exists, throw an error
    if (existingUser.length > 0 ) {
      throw new Error('User already exists');
    }
    if (existingEmail.length > 0) {
      throw new Error('Email already exists');
    }

    // Insert the new user into the database
    await connection.execute(
      'INSERT INTO users (email, username, password) VALUES (?, ?, ?)',
      [email, username, password]
    );

  } catch (error) {
    console.error('Error during registration:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}
//Forgot Password Service
exports.forgotPassword = async (email,username) => {
  try {
    const connection = await db(); // Ensure you have a valid connection
    // Query to check if the user exists
    const [existingEmail] = await connection.execute(
      'SELECT * FROM users WHERE email = ? LIMIT 1',
      [email]
    );
    const [existingUser] = await connection.execute(
      'SELECT * FROM users WHERE username = ? LIMIT 1',
      [username]
    );

    // If the user does not exist, throw an error
    if (existingEmail.length === 0) {
      throw new Error('Email không tồn tại');
    }
    if (existingUser.length === 0) {
      throw new Error('tên người dùng không tồn tại');
    }
    // Return the user's password
    return existingUser[0].password;
  } catch (error) {
    console.error('Error during password recovery:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}
exports.getUserInfo = async (userId) => {
  try {
    const connection = await db(); //Connect to the database
    // Query to get user information by userId
    const [user_info] = await connection.execute(`
      SELECT
        u.username,
        u.avatar_url,
        COUNT(DISTINCT f.follower_id) AS follower_count,
        COUNT(DISTINCT CONCAT(l.user_id, '-', l.video_id)) AS total_likes_received
      FROM users u
      LEFT JOIN follows f ON u.user_id = f.following_id
      LEFT JOIN videos v ON u.user_id = v.user_id
      LEFT JOIN likes l ON v.video_id = l.video_id
      WHERE u.user_id = ?
      GROUP BY u.user_id
      `,
      [userId]
    );

    // If a user is found, return the user object
    if (user_info.length > 0) {
      return {
        data: {
          username: user_info[0].username || 'Unknown',
          avatar_url: user_info[0].avatar_url || 'Unknown',
          number_of_followers: user_info[0].follower_count || 0,
          number_of_likes: user_info[0].total_likes_received || 0
        }
      }
    } else {
      return{
        error: 'User not found'
      }; // No user found
    }
  } catch (error) {
    console.error('Error fetching user info:', error);
    throw error; // Propagate the error to be handled by the controller
  }
}
exports.changePassword = async(user_id, oldPass, newPass)=>{
  try{
    const connection = await db()
    const [rows] = await connection.execute(`
      SELECT * FROM users WHERE user_id = ? AND password = ?
      `,[user_id,oldPass])
    if(rows.length === 0 ){
      return{
        success:false,
        message:"Old Password was wrong!"}
    }
    await connection.execute(`
      UPDATE users SET password = ? WHERE user_id = ?
      `,[newPass,user_id])
    return {
      success:true,
      message:"Changing Password Successfully"}
  }
  catch(error){
      throw error
  }
}

