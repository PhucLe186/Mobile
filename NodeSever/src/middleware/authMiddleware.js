const jwt = require('jsonwebtoken');

// Middleware to authenticate JWT tokens
const authenticateToken = (req, res, next) => {
    const authHeader = req.headers['authorization'];
    const token = authHeader && authHeader.split(' ')[1];//split the header to get the token
    if(!token) return res.status(401).json({ message: 'Token Not Found' });// Check if token is present
        jwt.verify(token, process.env.JWT_SECRET, (err, user) => {
        if(err) return res.status(403).json({ message: 'Token is not valid' });// Verify the token
        req.user = user;// Attach the user to the request object
        next(); // Call the next middleware or route handler
    })
}
module.exports = authenticateToken;
