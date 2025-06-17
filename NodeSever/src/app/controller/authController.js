const authService = require("../services/authServices");
exports.login = async (req, res) => {
  const { username, password } = req.body; //Check if username and password are provided

  try {
    // If not, return a 400 Bad Request response
    if (!username && !password) {
      return res.status(400).json({ error: "Cần tên đăng nhập và mật khẩu" });
    }
    if( !username) {
      return res.status(400).json({ error: "Thiếu tên đăng nhập" });
    }
    if( !password) {
      return res.status(400).json({ error: "Thiếu mật khẩu" });
    }
    // Call the authService to handle the login logic
    // This service should handle the authentication logic, such as checking the database
    // for the user credentials and returning the user object if successful
    const user = await authService.login(username, password);
    if (!user) {
      return res.status(401).json({ error: "Sai tên hoặc mật khẩu" });
    }

    // If the user is found, return a 200 OK response with the user data
    res.status(200).json({ 
        message: "Login successful",
        user:{
            id: user.id,
            username: user.username,
            avatar_url: user.avatar_url,
            phone:user.phone,
        } });
  } catch (error) {
    console.error("Login error:", error);
    res.status(500).json({ error: "Internal server error" });
  }
};
