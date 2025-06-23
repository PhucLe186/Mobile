const mysql = require('mysql2/promise');

async function connectDB() {
  try {
    const connection = await mysql.createConnection({
      host: 'localhost',
      user: 'root',
      password: '123456',
      database: 'clone_tiktok'
    });
    console.log('Kết nối thành công tới MySQL');
    return connection;
  } catch (err) {
    console.error('Kết nối thất bại:', err);
  }
}

module.exports = connectDB;
