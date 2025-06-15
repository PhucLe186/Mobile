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
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES Users(user_id) ON DELETE CASCADE
);



-- Thêm người dùng
INSERT INTO Users (username, email, password, phone, avatar_url)
VALUES
('alice', 'alice@example.com', 'password123','0969119817' ,'https://res.cloudinary.com/dm1lcegq1/image/upload/v1749828089/unnamed_reqim7.png'),
('bob', 'bob@example.com', 'securepass','0969119817','https://res.cloudinary.com/dm1lcegq1/image/upload/v1749828088/92a78ba51b1769d5e4baf07cd38dfd22_ot5chu.jpg'),
('charlie', 'charlie@example.com', 'charlie123','0969119817', 'https://res.cloudinary.com/dm1lcegq1/image/upload/v1749828088/anh-anime-sad_srlfwk.webp');

-- Thêm video
INSERT INTO Videos (user_id, video_url, caption)
VALUES
(1, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1749828284/iLoveTik.com_TikTok_Media_001_8cd079008bf2ee9a725140133951ab5a_zjev5n.mp4', 'Check this out! 😎'),
(2, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1749828286/Download_tnxdke.mp4', 'My first dance video 💃'),
(3, 'https://res.cloudinary.com/dm1lcegq1/video/upload/v1749828281/Download_1_b7fzr4.mp4', 'Travel vlog in Da Nang 🇻🇳');

-- Thêm lượt like
INSERT INTO Likes (user_id, video_id)
VALUES
(1, 2),
(2, 1),
(3, 1),
(1, 3);

-- Thêm bình luận
INSERT INTO Comments (video_id, user_id, comment)
VALUES
(1, 2, 'Wow, great video! 🔥'),
(1, 3, 'Love this! 💖'),
(2, 1, 'Nice moves!'),
(3, 1, 'I want to go there too! ✈️');

-- Thêm theo dõi
INSERT INTO Follows (follower_id, following_id)
VALUES
(1, 2),
(1, 3),
(2, 3),
(3, 1);

-- Thêm lưu video
INSERT INTO SavedVideos (user_id, video_id)
VALUES
(1, 1),
(2, 3),
(3, 2);

INSERT INTO Messages (sender_id, receiver_id, message)
VALUES
(1, 2, 'Hey Bob, how are you?'),
(2, 1, 'Hi Alice! I’m doing great 😊'),
(3, 1, 'Hey Alice, are you free for a collab?');
