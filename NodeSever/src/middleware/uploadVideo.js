const multer= require('multer');
const {CloudinaryStorage}=require('multer-storage-cloudinary');
const cloudinary=require('../Config/Cloudinary');

const storage =new CloudinaryStorage({
    cloudinary: cloudinary,
    params:{
        resource_type: 'video',
        allowed_formats: ['mp4', 'mov', 'avi'],
    }
});

const upload=multer({ storage });


module.exports=upload;
