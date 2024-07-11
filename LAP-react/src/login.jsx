import React, { useState } from 'react'
import './login-signup.css'
import idIcon from './images/id.png'
import pwdIcon from './images/password.png'
import Header from './components/header'
import { useNavigate, Link } from 'react-router-dom'
import axios from 'axios'


const Login = () => {




  //login 
  const [loginData, setLoginData] = useState({
    userid: '',
    pwd: ''
  })


  const handleChange = (event) => {
    setLoginData({
      ...loginData,
      [event.target.name]: event.target.value
    })
  }

  const history = useNavigate()
  const [errors, setErrors] = useState({})
  const handleSubmit = async (e) => {
    e.preventDefault()
    //sign up validation errors
    const errors = {}
    const formData = new FormData()
    formData.append('userid', loginData.userid)
    formData.append('password', loginData.pwd)
    try {
      await axios.post('http://localhost:8080/api/login', formData)
      .then((res) => {
        if (res.data.message === "Login as Employee") {
          history('/EmpHome', { state: { id: loginData.userid } })

        }
        else if (res.data.message === "Login as Management") {
          history('/MngtHome', { state: { id: loginData.userid } })

        }
        else if (res.data.message === "Password incorrect") {
          errors.pwd = "Incorrect User ID or Password"
        }
        else if (res.data.message === "All fields are mandatory") {
          errors.noData = "All fields are mandatory"
        }

      })
      setErrors(errors)

    }
    catch (error) {
      console.log('Error submitting form', error)
    }
    /* setLoginData({
       userid:'',
       pwd:''
     })
     */


  }








  return (

    <div className="body">
      <div>
        <Header />
      </div>
      <div className="container">
        <div className="head">
          <div className="text">Login</div>
        </div>

        <div className="inputs">
          {errors.noData && <p className='error'>{errors.noData}</p>}
          {errors.pwd && <p className='error'>{errors.pwd}</p>}
          <div className="input">
            <img src={idIcon} alt="id" />
            <input type="text" placeholder='User ID' autoComplete="off" value={loginData.userid} name="userid" onChange={handleChange} required />
          </div>
          <div className="input">
            <img src={pwdIcon} alt="password" />
            <input type="password" placeholder='Password' autoComplete="off" name="pwd" value={loginData.pwd} onChange={handleChange} required />
          </div>


        </div>

        <div className="forgot-pwd">Forgot password? <Link to="/forgotPassword"><span>Click here!</span></Link></div>
        <div className="submit-container">
          <div className="submit" onClick={handleSubmit}>Login</div>
        </div>

      </div>


    </div>

  );
}

export default Login
