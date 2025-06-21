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
    const [result] = await connection.execute(
      'INSERT INTO users (email, username, password) VALUES (?, ?, ?)',
      [email, username, password]
    );

  } catch (error) {
    console.error('Error during registration:', error);
    throw error; // Propagate the error to be handled by the controller
  }
   }
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