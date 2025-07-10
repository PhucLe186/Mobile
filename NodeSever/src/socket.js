// socket.js
const db = require('./Config/sqlServer');
const authenticateSocket = require('./middleware/authenticateSocket');
const InboxController = require('./app/controller/InboxController');


module.exports = (io) => {
  io.use(authenticateSocket);

  io.on('connection', (socket) => {
    let room, rooms
    const user_id = socket.user?.user_id;
    console.log('Connected:', socket.id);

    socket.on('joinAllrooms', async()=>{
      rooms= await InboxController.getAllroomofUser({user_id: user_id})
      rooms.forEach(roomid=>{
        socket.join(roomid.toString());
        console.log(user_id +"joined" +roomid)
      })
    })
    socket.on('room', async ({ peer_id }) => {
      room = await InboxController.getOrCreateRoom({ sender_id: user_id, receiver_id: peer_id });
      socket.join(room.toString());
      console.log(user_id +"joined" +room)
    });
    socket.on('send', async ({ receiver_id, message }) => {
      if (!room) {
        console.error('Error: Room not defined. Please join a room first.');
        return;
    }
    console.log(message)
      const connection = await db();
      await connection.execute(`
        INSERT INTO messages (sender_id, receiver_id, message, room)
        VALUES (?, ?, ?, ?)
      `, [user_id, receiver_id, message, room]);
        const [rows]=await connection.execute(`SELECT * FROM users where user_id=?`, [user_id])

        
        
        
        
      io.to(room.toString()).emit('receive', {
        sender_id: user_id,
        receiver_id,
        message,
        room,
        sent_at: new Date().toISOString()
      });

      io.to(room.toString()).emit('update_receive', {
        name:rows[0]?.username,
        avata_url:rows[0]?.avatar_url,
        sender_id: user_id,
        receiver_id,
        message,
        room,
        sent_at: new Date().toISOString()
      });
    
      await connection.end();
    });
    
  });
};
