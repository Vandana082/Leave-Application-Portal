import React from 'react'
import '../login-signup.css'
import userIcon from '../images/user.png'
import emailIcon from '../images/email.png'
import pwdIcon from '../images/password.png'
import idIcon from '../images/id.png'
import desigIcon from '../images/desig.png'
import { useState } from 'react'
import axios from 'axios'

const SignUp = () => {

  /*dropdown*/
  /*stores the selected option*/
  const [selectedOption, setSelectedOption] = useState('')
  /*to handle change in the selected option*/
  const handleSelectChange = (event) => {
    setSelectedOption(event.target.value)
  }



  /*sign up*/
  const [formData, setFormData] = useState({
    username: '',
    userid: '',
    email: '',
    role: '',
    password: ''
  })


  const [errors, setErrors] = useState({})
  const handleSubmit = async (e) => {
    e.preventDefault()

    //sign up validation errors
    const errors = {}
    const formData1 = new FormData()
    formData1.append('username', formData.username)
    formData1.append('userid', formData.userid)
    formData1.append('email', formData.email)
    formData1.append('role', formData.role)
    formData1.append('password', formData.password)
    formData1.append('category', selectedOption)
    try {
      await axios.post('http://localhost:8080/api/save', formData1, {
        headers: {
          'Content-Type': 'application/json'
        }})
      .then((res) => {
        if (res.data.message === "User already exists") {
          alert("User ID already exist")
          setFormData({
            username: '',
            userid: '',
            email: '',
            role: '',
            password: ''
          })
          setSelectedOption('')
        }
        if (res.data.message === "All fields are mandatory") {
          errors.noData = "All fields are mandatory"
        }
        if (res.data.message === "User ID already exist") {
          alert("User ID already exist")
          setFormData({
            username: '',
            userid: '',
            email: '',
            role: '',
            password: ''
          })
          setSelectedOption('')
        }
        if (res.data.message === "Sign up successful") {
          alert("Profile created successfully")
          console.log(formData1)
          setFormData({
            username: '',
            userid: '',
            email: '',
            role: '',
            password: ''
          })
          setSelectedOption('')
        }
        if (res.data.message === "Please enter valid email address") {
          errors.email = "Please enter valid email address"
        }
        if (res.data.message === "Password should contain at least 6 characters") {
          errors.pwdgt = "Password should contain at least 6 characters"
        }
        if (res.data.message === "Password cannot exceed more than 12 characters") {
          errors.pwdlt = "Password cannot exceed more than 12 characters"
        }
      })
      setErrors(errors)

    }
    catch (error) {
      console.log('Error submitting form', error)
    }



  }

  const handleChange = (event) => {
    setFormData({
      ...formData,
      [event.target.name]: event.target.value
    })
  }


  return (
    <div>
      <form action="POST">
        <div className="container1">

          <div className="head1">
            <div className="update">Create Profile</div>
          </div>

          <div className="inputs">
            {errors.noData && <p className='error'>{errors.noData}</p>}
            <div className="input">
              <img src={userIcon} alt="user" />
              <input type="text" name="username" value={formData.username} placeholder='User Name' autoComplete="off" onChange={handleChange} required />
            </div>
            <div className="input">
              <img src={idIcon} alt="id" />
              <input type="text" placeholder='User ID' autoComplete="off" value={formData.userid} name="userid" onChange={handleChange} required />
            </div>
            <div className="input">
              <img src={emailIcon} alt="email" />
              <input type="email" placeholder='Email ID' autoComplete="off" name="email" value={formData.email} onChange={handleChange} required />
            </div>
            {errors.email && <p className='errors'>{errors.email}</p>}
            <div className="input">
              <img src={desigIcon} alt="email" />
              <input type="text" placeholder='Designation' autoComplete="off" name="role" value={formData.role} onChange={handleChange} required />
            </div>
            <div className="input">
              <img src={pwdIcon} alt="password" />
              <input type="password" placeholder='Password' autoComplete="off" name="password" value={formData.password} onChange={handleChange} required />
            </div>
            {errors.pwdgt && <p className='errors'>{errors.pwdgt}</p>}
            {errors.pwdlt && <p className='errors'>{errors.pwdlt}</p>}
            <div className="input">
              <select value={selectedOption} name="category" onChange={handleSelectChange} required>
                <option value="">Category</option>
                <option value="Employee">Employee</option>
                <option value="Management">Managment</option>
              </select>

            </div>
          </div>

          <div className="submit-container">
            <div className="submit" onClick={handleSubmit}>Create profile</div>
          </div>

        </div></form>
    </div>
  );
}

export default SignUp