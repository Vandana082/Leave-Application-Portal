const mongoose = require('mongoose')
mongoose.connect('mongodb://localhost:27017/lap-db')
.then(() => console.log('MongoDB Connected'))
.catch(err => console.log(err))

const newSchema = new mongoose.Schema({
    username: {type:String,required:true},
    userid: {type:String,required:true},
    email: {type:String,required:true},
    password: {type:String,required:true}
  })

  const collection = mongoose.model('collection', newSchema)

  module.exports = collection
