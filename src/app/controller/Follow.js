const express = require("express");
const cors = require("cors");
const app = express();
const PORT = 3000;

let users = [
  { id: 1, name: "Alice", followers: [], following: [] },
  { id: 2, name: "Bob", followers: [], following: [] }
];

app.use(cors());
app.use(express.json());

// GET: Danh sách người đang follow
app.get("/users/:id/following", (req, res) => {
  const user = users.find(u => u.id == req.params.id);
  if (!user) return res.status(404).send("User not found");
  const following = user.following.map(id => users.find(u => u.id == id));
  res.json(following);
});

// GET: Danh sách follower
app.get("/users/:id/followers", (req, res) => {
  const user = users.find(u => u.id == req.params.id);
  if (!user) return res.status(404).send("User not found");
  const followers = users.filter(u => u.following.includes(user.id));
  res.json(followers);
});

// POST: Follow người khác
app.post("/users/:id/follow", (req, res) => {
  const userId = parseInt(req.params.id);
  const targetId = req.body.targetId;
  const user = users.find(u => u.id === userId);
  const target = users.find(u => u.id === targetId);
  if (!user || !target) return res.status(404).send("User not found");

  if (!user.following.includes(targetId)) {
    user.following.push(targetId);
    target.followers.push(userId);
  }

  res.send("Followed successfully");
});

// POST: Unfollow người khác
app.post("/users/:id/unfollow", (req, res) => {
  const userId = parseInt(req.params.id);
  const targetId = req.body.targetId;
  const user = users.find(u => u.id === userId);
  const target = users.find(u => u.id === targetId);
  if (!user || !target) return res.status(404).send("User not found");

  user.following = user.following.filter(id => id !== targetId);
  target.followers = target.followers.filter(id => id !== userId);

  res.send("Unfollowed successfully");
});

app.listen(PORT, () => {
  console.log(`✅ Server running at http://localhost:${PORT}`);
});
