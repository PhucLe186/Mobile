const  db  = require('../../Config/sqlServer.js');
const jwt = require('jsonwebtoken');

class EditProfileController {

  async getInfor(req, res){

    let userId;
      const token = req.headers['authorization']?.split(' ')[1];
      const connection = await db();
      if (!token) return res.status(401).json({ message: 'Không có token' });
      try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        userId = decoded.user_id;
      }catch (err) {
            return res.status(401).json({ message: 'Token không hợp lệ' });
      } 
    const[rows]= await connection.execute(`
      SELECT username, avatar_url FROM users WHERE user_id=?
      `, [userId])
      return res.status(200).json(rows);     
  }



  async uploadIMG(req, res) {
    console.log("đã vào đây")
     try {
        let userId;
        const token = req.headers['authorization']?.split(' ')[1];
        const file = req.file;

        console.log("this is file"+file)
        console.log("this is"+token)
        const connection = await db();

        if (!token) return res.status(401).json({ message: 'Không có token' });
        try {
           const decoded = jwt.verify(token, process.env.JWT_SECRET);
           userId = decoded.user_id;
        }catch (err) {
            return res.status(401).json({ message: 'Token không hợp lệ' });
        }  
        if (!file) return res.status(400).json({ message: 'Chưa có file' });
       
        console.log(userId)
        const img_url=file.path
        console.log(img_url)
        await connection.execute('UPDATE users SET avatar_url = ? WHERE user_id = ?', [img_url, userId]);
        await connection.end();

      return res.status(200).json({ message: 'Upload thành công' });     
  }catch(err){
return res.status(500).json({ message: 'Lỗi server', error: err.message });
  }
  }

  async changeName(req, res){
    let user_id = req.user?.user_id;
    const{name}=req.body;
    console.log(name)

    const connection= await db();
    await connection.execute(`UPDATE users SET username =? WHERE user_id=?`, [name, user_id])

    res.status(200).json({message: 'update complete'})
  }
}

module.exports = new EditProfileController();


