const multer= require('multer');
const {CloudinaryStorage}=require('multer-storage-cloudinary');
const cloudinary=require('../Config/Cloudinary');

const storage =new CloudinaryStorage({
    cloudinary: cloudinary,
    params:{
        allowed_formats: ['jpg', 'png'],
    }
});
const upload=multer({ storage });


module.exports=upload;
