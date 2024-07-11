import React, { useState } from 'react'
import pwdIcon from '../images/password.png'
import axios from 'axios'


const ChangePassword = (props) => {

    //change password
    const [password, setPassword] = useState({
        current: '',
        new: '',
    })
    const handleChange = (event) => {
        setPassword({
            ...password,
            [event.target.name]: event.target.value
        })
    }
    const [errors, setErrors] = useState({})
    const handleSubmit = async (e) => {
        e.preventDefault()
        const errors = {}
        try {
            if (password.current === "" || password.new === "") {
                errors.data = "All fields are mandatory"
                console.log(errors.data)
            }
            else {
                const response = await axios.put(`http://localhost:8080/api/changePassword/${props.userid}/${password.current}/${password.new}`);
                console.log(response.data)
                if (response.data.message === "Wrong password") {
                    alert("Current password not matching")
                    setPassword({
                        current: '',
                        new: ''
                    })
                }
                if (response.data.message === "Password changed") {
                    alert("Password changed successfully")
                    setPassword({
                        current: '',
                        new: ''
                    })
                }
            }
            setErrors(errors)
        }
        catch (error) {
            console.error('Error fetching data:', error);
        }

    }






    return (
        <div>
            <div className="container">
                <div className="head">
                    <div className="update">Change Password</div>
                </div>

                <div className="inputs">
                    {errors.data && <p className='error'>{errors.data}</p>}
                    <div className="input">
                        <img src={pwdIcon} alt="id" />
                        <input type="password" placeholder='Current password' value={password.current} autoComplete="off" name="current" onChange={handleChange} required />
                    </div>
                    <div className="input">
                        <img src={pwdIcon} alt="password" />
                        <input type="password" placeholder='New password' value={password.new} autoComplete="off" name="new" onChange={handleChange} required />
                    </div>
                </div>

                <div className="submit-container">
                    <div className="submit" onClick={handleSubmit}>Update</div>
                </div>

            </div>
        </div>
    )
}

export default ChangePassword