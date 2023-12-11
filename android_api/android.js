const mongoose = require('mongoose')
const Schema = mongoose.Schema
const passportLocalMongoose = require('passport-local-mongoose');

var Android = new Schema({
    androidId:{
        type: Number
    },
    androidData:{
        type: String
    }
}, { collection: 'android' })
  
Android.plugin(passportLocalMongoose);
  
module.exports = mongoose.model('Android', Android)