require('dotenv').config();
const express = require('express');
const morgan = require('morgan');
const cors = require('cors');
const session = require('express-session');

const app = express();
const port = process.env.PORT || 5000;

// Middleware
app.use(
  cors({
    origin: process.env.CLIENT_URL || 'http://localhost:3000', // fallback
    credentials: true,
  }),
);
app.use(express.urlencoded({ extended: true }));
app.use(express.json());
app.use(morgan('combined'));
app.use(
  session({
    secret: process.env.SESSION_SECRET || 'mysecret',
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false, httpOnly: true },
  }),
);

// === Route setup ===
const route = require('./routes/sever'); // Đảm bảo đúng tên file trong folder routes
app.use('/api', route); // Tất cả route bắt đầu bằng /api

// === Server start ===
app.listen(port, () => {
  console.log(`✅ Server đang chạy tại: http://localhost:${port}`);
});
