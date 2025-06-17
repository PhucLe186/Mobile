const db = require('../../Config/sqlServer.js');
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