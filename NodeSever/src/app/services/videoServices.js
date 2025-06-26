const db = require("../../Config/sqlServer.js");

class VideoService {
  async getUserVideos(user_id) {
    if (!user_id) {
      throw new Error("User ID is required");
    }
    if(user_id <= 0) {
      throw new Error("User ID must be a positive integer");
    }
    const connection = await db();
    try {
      // Kiểm tra user có tồn tại không
      const [userRows] = await connection.execute(
        `SELECT 1 FROM users WHERE user_id = ? LIMIT 1`,
        [user_id]
      );

      if (userRows.length === 0) {
        return { error: "User not found" };
      }

      // Lấy danh sách video
      const [videos] = await connection.execute(
        `SELECT video_id, video_url FROM videos WHERE user_id = ?`,
        [user_id]
      );

      return {
        success: true,
        videos: videos.length > 0 ? videos : []
      };
    } catch (error) {
      console.error("Error fetching user videos:", error.message);
      throw new Error("Internal server error");
    } finally {
      await connection.end(); // Đảm bảo đóng kết nối
    }
  }
}

module.exports = new VideoService();