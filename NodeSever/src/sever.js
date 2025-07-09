require('dotenv').config();
const express = require('express');
const morgan = require('morgan');

const http = require('http');
const cors = require('cors');
const route = require('./routes');
const session = require('express-session');
const app = express();
const port = process.env.PORT || 5000;
const { Server } = require('socket.io');
const socketInit = require('./socket');


app.use(
  cors({
    origin: process.env.CLIENT_URL,
    credentials: true,
    allowedHeaders: ['Content-Type', 'Authorization']
  }),
);

app.use(express.urlencoded({extended: true}));
app.use(express.json());
app.use(morgan('combined'));
app.use(
  session({
    secret: process.env.SESSION_SECRET,
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false, httpOnly: true },
  }),
);
route(app);

const server = http.createServer(app);

// Tạo socket.io server
const io = new Server(server, {
  cors: {
    origin: '*',
    methods: ['GET', 'POST'],
  },
});
module.exports = { app, server, io };
socketInit(io); 

server.listen(port, () => {
  console.log(`chạy thành công server tại http:localhost:${port}`);
});

