const express = require('express');
const route = express.Router();

const followController = require('../app/controller/FollowController');

route.use('/:userId/following', followController.getFollowingList);
route.use('/:userId/followers', followController.getFollowersList);
route.use('/follow', followController.followUser);
route.use('/unfollow', followController.unfollowUser);


module.exports = route;
