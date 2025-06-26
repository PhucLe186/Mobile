const { create } = require('express-handlebars');
const commentServices = require('../services/commentServices.js');
require("dotenv").config();

class CommentController {
  // GET comments for a specific video
  async getComments(req, res) {
    const { video_id } = req.query; // Hoặc req.params nếu video_id nằm trong URL
    try {
      if (!video_id) {
        return res.status(400).json({ message: 'Video ID is required' });
      }

      const result = await commentServices.getComments(video_id);

      if (!result.success) {
        return res.status(404).json({ message: 'No comments found for this video' });
      }

      return res.status(200).json(result);
    } catch (error) {
      console.error('Error fetching comments:', error);
      res.status(500).json({ message: 'Internal server error' });
    }
  }

   async GetUserInfo(req, res) {
    const  user_id  = req.user?.user_id;
    
    if (!user_id) {
      console.log(req.user);
      return res.status(401).json({ error: "Cần user_id" });
     
    }
    try {
      const result = await commentServices.getUserInfo(user_id);
      if (result.error) {
        return res.status(404).json({
          error: result.error,
        });
      }
      res.status(200).json({
        message: "Lay thong tin nguoi dung thanh cong",
        avatar_url: result.avatar_url,
        username : result.username,
      });
    } catch (error) {
      console.error("Error fetching user avatar:", error);
      res.status(500).json({ error: error.message });
    }
  }

  async UploadComment(req,res){
    const user_id = req.user?.user_id
    const { video_id, created_at, comment } = req.body;
    if(!video_id){//check provided video_id
      return res.status(400).json({
        error:"Video_id is required!"
      })
    }
    if(!created_at){//check provided created_at
      return res.status(400).json({
        error:"Created Time is required!"
      })
    }
    if(!comment){//check provided comment
      return res.status(400).json({
        error:"Comment is required!"
      })
    }
    try{
      const result = await commentServices.sendComment(user_id, comment, created_at, video_id)
      return res.status(200).json({success:true, message:"Upload comment successfully!!", comment_count: result.commentCount})
    }
    catch(error){
      console.error("Error during upload user comment to server =(")
      res.status(500).json({error:error.message})
    }
  }

}

module.exports = new CommentController();
