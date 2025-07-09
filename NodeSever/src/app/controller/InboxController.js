const db = require('../../Config/sqlServer.js');
// const  { io }  = require('../../sever.js');

// console.log(io)
// const authenticateSocket=require('../../middleware/authenticateSocket.js');
// io.use(authenticateSocket);
// let user_id = socket.user?.user_id;

// io.on('connection', (socket) => {
//   console.log('🟢 Connected:', socket.id);

//   socket.on('join_room', async ({peer_id }) => {
//     const room = await getOrCreateRoom(user_id, peer_id);
//     socket.join(room.toString());
//     console.log(`👥 User ${user_id} joined room ${room}`);
//   });

//   socket.on('send_message', async ({receiver_id, message }) => {
//     const room = await getOrCreateRoom(user_id, receiver_id);
//     const connection = await db();

//     await connection.execute(`
//       INSERT INTO messages (sender_id, receiver_id, message, room)
//       VALUES (?, ?, ?, ?)
//     `, [user_id, receiver_id, message, room]);

//     await connection.end();

//     io.to(room.toString()).emit('receive_message', {
//       sender_id,
//       receiver_id,
//       message,
//       room,
//       sent_at: new Date().toISOString()
//     });
//   });
// });
class InboxController {
  /////////////
  async getOrCreateRoom({sender_id, receiver_id }) {
  
    const user1 = Math.min(sender_id, receiver_id);
    const user2 = Math.max(sender_id, receiver_id);
    const connection=await db();
    const [rows]= await connection.execute(
      'SELECT * FROM messages WHERE ( sender_id=? AND receiver_id=? ) OR (receiver_id=? AND sender_id=? )',
      [user1 ,user2, user2, user1 ]
    )
    if(rows.length==0){
      const newRoom = Math.floor(100000 + Math.random() * 900000);
       await connection.end()
      return newRoom
    }else{
      console.log(rows[0].room);
       await connection.end()
      return rows[0].room 
    } 
  }
  ////////////////////////////////
  //  Lấy danh sách tin nhắn
  async getMessages(req, res) {
    let user_id = req.user?.user_id;

    if (!user_id) {
      console.log("Không có user_id trong token:", req.user);
      return res.status(401).json({ error: "Cần xác thực người dùng" });
    }

    try {
      const connection = await db();
      const [rows] = await connection.execute(
        `SELECT 
              Messages.*,
              
              sender.username AS sender_username,
              sender.avatar_url AS sender_avatar,
              receiver.username AS receiver_username,
              receiver.avatar_url AS receiver_avatar
              FROM Messages
              JOIN Users AS sender ON Messages.sender_id = sender.user_id
              JOIN Users AS receiver ON Messages.receiver_id = receiver.user_id
              WHERE Messages.sender_id = ? OR Messages.receiver_id = ?
              ORDER BY Messages.sent_at DESC;
            `,
        [user_id, user_id]
      );
      
      if (!rows || rows.length === 0) {
        return res.status(404).json({ message: "Không có tin nhắn nào" });
      }
      const checkvar=rows.map(infor=>{
        return{
          ...infor,
          myself: infor.sender_id === user_id ? 1 : 0
        }
      })

      return res.status(200).json({
        success: true,
        message: "Lấy tin nhắn thành công",
        data: checkvar
      });
    } catch (error) {
      console.error("Lỗi khi lấy tin nhắn:", error.message);
      return res.status(500).json({ error: "Lỗi server khi lấy tin nhắn" });
    }
  }

  async fetchChatMessages(req, res) {
    try {
       let user_id = req.user?.user_id;
       const {id}=req.query
      console.log(id)    

      const connection = await db();
      const [rows] = await connection.execute(
        `SELECT 
            messages.*,
            sender.username AS sender_username,
            sender.avatar_url AS sender_avatar,
            receiver.username AS receiver_username,
            receiver.avatar_url AS receiver_avatar,
            CASE 
            WHEN messages.sender_id = ? THEN 1
              ELSE 0
            END AS myself
            FROM messages
            JOIN users AS sender ON messages.sender_id = sender.user_id
            JOIN users AS receiver ON messages.receiver_id = receiver.user_id
            WHERE (messages.sender_id = ? AND messages.receiver_id = ?)
              OR (messages.sender_id = ? AND messages.receiver_id = ?)
            ORDER BY messages.sent_at ASC;
`,
        [user_id, user_id, id, id, user_id]
      );

      await connection.end();
      res.json(rows);
    } catch (err) {
      console.error('Lỗi tìm kiếm:', err);
      res.status(500).json({ error: 'Lỗi server' });
    }
  }
}

module.exports = new InboxController();