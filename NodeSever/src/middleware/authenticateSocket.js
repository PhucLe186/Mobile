const jwt = require('jsonwebtoken');

const authenticateSocket = (socket, next) => {
  const tokenRaw = socket.handshake.query.token;

  if (!tokenRaw) {
    return next(new Error('Token Not Found'));
  }
 const token = tokenRaw.startsWith('Bearer ') ? tokenRaw.slice(7) : tokenRaw;
  console.log(token)
  try {
    const user = jwt.verify(token, process.env.JWT_SECRET);
    socket.user = user;
    next();
  } catch (err) {
    return next(new Error('Token is not valid'));
  }
};

module.exports = authenticateSocket;
