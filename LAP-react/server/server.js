const express = require("express")
const cors = require("cors")
const app = express()

app.use(express.json())
//app.use(urlencoded({extended:true}))
app.use(cors())

/*const api = axios.create({
      baseURL:"http://localhost:8080/api"
    })*/
    app.get('/', cors(), async(req,res) =>{
      const loginData = req.body
      try {
        const id = await collection.findOne({user_id: loginData.userid})
        if(id){
          res.json("exist")
        }
        else{
          res.json('notexist')
        }
      }
      catch(e){
        res.json('notexist')
      }
    })   

//login validation
app.post('/', async(req,res) => {
  const loginData = req.body
  try{
    const check = await collection.findOne({user_id:loginData.userid, password:loginData.pwd})
    if(check){
      res.json("exist")
    }
    else{
      res.json('notexist')
    }
  }
  catch(e){
    res.json("notexist")

  }
})    

  

//signup validation
app.post('/signup', async(req,res) => {
  const {formData, selectedOption} = req.body
  const data = {
    user_name:formData.username,
    user_id:formData.userid,
    email:formData.email,
    password:formData.pwd,
    category:selectedOption
  }
  try{
    const check = await collection.findOne({user_id:formData.userid})
    if(check){
      res.json("exist")
    }
    else{
      res.json('notexist')
      await collection.insertMany([data])
    }
  }
  catch(e){
    res.json("notexist")

  } 
})    

const mongoose = require('mongoose')
mongoose.connect('mongodb://localhost:27017/lap-db')
.then(() => console.log('MongoDB Connected'))
.catch(err => console.log(err))

const newSchema = new mongoose.Schema({
    user_name: {type:String,required:true},
    user_id: {type:String,required:true},
    email: {type:String,required:true},
    password: {type:String,required:true},
    category: {type:String, required:true}
  })

  const collection = mongoose.model('userDetails', newSchema)

  module.exports = collection

app.listen(8000,()=>console.log("app is running"))
