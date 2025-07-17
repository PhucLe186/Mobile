CREATE SCHEMA `clone_tiktok` ;
USE clone_tiktok;

-- Bảng người dùng
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    password TEXT,
    avatar_url TEXT,
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng video
CREATE TABLE Videos (
    video_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    video_url TEXT,
    caption TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    view_count INT DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Bảng like video
CREATE TABLE Likes (
    user_id INT,
    video_id INT,
    liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
);

-- Bảng bình luận video
CREATE TABLE Comments (
    comment_id INT AUTO_INCREMENT PRIMARY KEY,
    video_id INT,
    user_id INT,
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE
);

-- Bảng theo dõi người dùng
CREATE TABLE Follows (
    follower_id INT,
    following_id INT,
    followed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (follower_id, following_id),
    FOREIGN KEY (follower_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (following_id) REFERENCES Users(user_id) ON DELETE CASCADE
);


-- Bảng lưu video
CREATE TABLE SavedVideos (
    user_id INT,
    video_id INT,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, video_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (video_id) REFERENCES Videos(video_id) ON DELETE CASCADE
); 
-- Bảng tin nhắn 
CREATE TABLE Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT,
    receiver_id INT,
    message TEXT,
    room INT DEFAULT NULL, // xác dịnh đang chat với ai
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id) ON DELETE CASCADE
);



-- USERS
INSERT INTO Users (username, email, password, avatar_url, phone) VALUES
('alice', 'alice@example.com', 'pass123', 'https://example.com/ava1.jpg', '0123456789'),
('bob', 'bob@example.com', 'pass456', 'https://example.com/ava2.jpg', '0987654321'),
('charlie', 'charlie@example.com', 'pass789', 'https://example.com/ava3.jpg', '0111222333'),
('david', 'david@example.com', 'pass111', 'https://example.com/ava4.jpg', '0333444555'),
('eva', 'eva@example.com', 'pass222', 'https://example.com/ava5.jpg', '0444555666'),
('frank', 'frank@example.com', 'pass333', 'https://example.com/ava6.jpg', '0555666777'),
('grace', 'grace@example.com', 'pass444', 'https://example.com/ava7.jpg', '0666777888'),
('henry', 'henry@example.com', 'pass555', 'https://example.com/ava8.jpg', '0777888999'),
('irene', 'irene@example.com', 'pass666', 'https://example.com/ava9.jpg', '0888999000'),
('jack', 'jack@example.com', 'pass777', 'https://example.com/ava10.jpg', '0999000111');

-- VIDEOS
INSERT INTO Videos (user_id, video_url, caption) VALUES
(1, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1749828284/iLoveTik.com_TikTok_Media_001_8cd079008bf2ee9a725140133951ab5a_zjev5n.mp4', 'Check this out! 😎'),
(2, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1749828286/Download_tnxdke.mp4', 'My first dance video 💃'),
(3, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1749828281/Download_1_b7fzr4.mp4', 'Travel vlog in Da Nang 🇻🇳');
(4, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584458/Download_2_v1adsd.mp4', 'Dancing in the rain'),
(5, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584458/Download_4_djb7tp.mp4', 'Guitar solo'),
(6, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584458/Download_1_x23qyp.mp4', 'Cooking pasta'),
(7, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584458/Download_6_aa2n4p.mp4', 'My travel vlog'),
(8, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584462/Download_3_d9fcvn.mp4', 'Drawing challenge'),
(9, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584462/Download_3_d9fcvn.mp4', 'Gaming highlights'),
(10,'https://res.cloudinary.com/dm1lcegq1/video/upload/v1750584463/Download_5_wdsys0.mp4', 'Satisfying ASMR');

-- LIKES
INSERT INTO Likes (user_id, video_id) VALUES
(1, 2), (1, 3), (2, 1), (2, 4), (3, 5),
(4, 6), (5, 7), (6, 8), (7, 9), (8, 10);

-- COMMENTS
INSERT INTO Comments (video_id, user_id, comment) VALUES
(1, 2, 'Nice video!'), (2, 3, 'Well done!'), (3, 1, 'Cute cat!'),
(4, 5, 'Amazing dance'), (5, 4, 'Great performance!'),
(6, 6, 'I want the recipe!'), (7, 7, 'Beautiful place!'),
(8, 8, 'Awesome art!'), (9, 9, 'LOL 🤣'), (10, 10, 'So relaxing');

-- FOLLOWS
INSERT INTO Follows (follower_id, following_id) VALUES
(1, 2), (2, 3), (3, 4), (4, 5), (5, 6),
(6, 7), (7, 8), (8, 9), (9, 10), (10, 1);

-- SAVED VIDEOS
INSERT INTO SavedVideos (user_id, video_id) VALUES
(1, 5), (2, 6), (3, 7), (4, 8), (5, 9),
(6, 10), (7, 1), (8, 2), (9, 3), (10, 4);

-- MESSAGES
INSERT INTO Messages (sender_id, receiver_id, message) VALUES
(1, 2, 'Hey!'), (2, 3, 'What\'s up?'), (3, 4, 'Nice video!'),
(4, 5, 'Hello 👋'), (5, 6, 'Let\'s collab'), (6, 7, 'Cool!'),
(7, 8, 'How did you do that?'), (8, 9, 'GG WP!'), (9, 10, 'Follow me!'), (10, 1, 'Yo!');
