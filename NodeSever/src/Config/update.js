const multer= require('multer');
const {CloudinaryStorage}=require('multer-storage-cloudinary');
const cloudinary=require('./Cloudinary');

const storage =new CloudinaryStorage({
    cloudinary: cloudinary,
    params:{
        allowed_formats: ['jpg', 'png'],
    }
});
console.log(" Ddax vao dday")
const upload=multer({ storage });


module.exports=upload;
