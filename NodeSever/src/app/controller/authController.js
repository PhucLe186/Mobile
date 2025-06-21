const  db  = require('../../Config/sqlServer.js');
const jwt = require('jsonwebtoken');
const authService = require("../services/authServices");
require('dotenv').config();

class AuthController {
  
  async Login(req, res) {

    const connection = await db(); 
    const { username, password } = req.body;
    try {

    if (!username && !password) {
      return res.status(400).json({ error: "Cần tên đăng nhập và mật khẩu" });
    }
    if( !username) {
      return res.status(400).json({ error: "Thiếu tên đăng nhập" });
    }
    if( !password) {
      return res.status(400).json({ error: "Thiếu mật khẩu" });
    }
    const [rows] = await connection.execute(
      `SELECT * FROM users WHERE username = '${username}'`,
    );

    if (rows.length === 0) {
      return res.status(400).json({ error: "Tài khoản không tồn tại" });
    } 

     const user=rows[0]
     if(user.password !==password ){
         return res.status(400).json({ error: "Nhập sai mật khẩu! vui lòng nhập lại" });
     }
     const token=jwt.sign({ user_id:user.user_id, email: user.email}, process.env.JWT_SECRET)

     res.status(200).json({ success: "Đăng nhập thành công",token});

    } catch (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
}

async Register(req, res) {
  const { email,username, password } = req.body;

  try {
    // If not, return a 400 Bad Request response
    if (!email || !username || !password) {
      return res.status(400).json({ error: "Cần đầy đủ thông tin" });
    }
    if(!email.endsWith("@gmail.com")) {
      return res.status(400).json({ error: "Email phải kết thúc bằng @gmail.com" });
    }

    // Call the authService to handle the registration logic
    // This service should handle the registration logic, such as checking if the user already exists
    // and creating a new user in the database if not
    await authService.register(email, username, password);

    // If the user is successfully registered, return a 201 Created response with the user data
    res.status(201).json({
        message: "Registration successful",
    });
  } catch (error) {
    console.error("Registration error:", error);
    res.status(500).json({ error: error.message });
  }
}

async ForgotPassword(req, res) {
    const {email,username} = req.body;
    if (!email || !username) {
        return res.status(400).json({ error: "Cần đầy đủ thông tin" });
      }
      if (!email.endsWith("@gmail.com")) {
        return res
          .status(400)
          .json({ error: "Email phải kết thúc bằng @gmail.com" });
      }
    try{
      // Call the authService to handle the forgot password logic
      const password = await authService.forgotPassword(email, username);
      res.status(200).json({
        message: "successfully forgot password request",
        password:password,
      });
    }
    catch (error) {
      console.error("Forgot password error:", error);
      res.status(500).json({ error: error.message });
    }
  };


 async Checklogin(req, res) {
    const authHeader = req.headers['authorization'];
    if (!authHeader) return res.status(401).json({ message: 'Không có token' });
    console.log(authHeader)

    const token = authHeader.split(' ')[1];

    console.log(token)
    jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
    if (err) return res.status(403).json({ message: 'Token không hợp lệ' });
    res.json({ login: true, user });
  });
}

};


module.exports = new AuthController();


