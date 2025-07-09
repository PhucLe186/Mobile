// socket.js
const db = require('./Config/sqlServer');
const authenticateSocket = require('./middleware/authenticateSocket');
const InboxController = require('./app/controller/InboxController');


module.exports = (io) => {
  io.use(authenticateSocket);

  io.on('connection', (socket) => {
    let room
    const user_id = socket.user?.user_id;
    console.log('Connected:', socket.id);

    socket.on('room', async ({ peer_id }) => {
      room = await InboxController.getOrCreateRoom({ sender_id: user_id, receiver_id: peer_id });
      socket.join(room.toString());
      console.log(`User ${peer_id} joined room ${room}`);
    });
    socket.on('send', async ({ receiver_id, message }) => {
      console.log(receiver_id+message+room)
      const connection = await db();
      await connection.execute(`
        INSERT INTO messages (sender_id, receiver_id, message, room)
        VALUES (?, ?, ?, ?)
      `, [user_id, receiver_id, message, room]);
      await connection.end();

      io.to(room.toString()).emit('receive', {
        sender_id: user_id,
        receiver_id,
        message,
        room,
        sent_at: new Date().toISOString()
      });
    });
  });
};
