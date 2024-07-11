import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import './login-signup.css'
import Header from './components/header'
import idIcon from './images/id.png'
import emailIcon from './images/email.png'
import pwdIcon from './images/password.png'
import axios from 'axios'
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';


const ForgotPassword = () => {

    const [data, setData] = useState({
        userid: '',
        email: ''
    })

    const handleChange = (event) => {
        setData({
            ...data,
            [event.target.name]: event.target.value
        })
    }
    const [errors, setErrors] = useState({})
    const handleSubmit = async (e) => {
        e.preventDefault()
        const errors = {}
        try {
            if (data.userid === "" || data.email === "") {
                errors.noData = "All fields are mandatory"
            }
            else {
                await axios.post(`http://localhost:8080/api/forgotPassword/${data.userid}/${data.email}`)
                    .then((res) => {
                        if (res.data.message === "userid found") {
                            handleClickOpen()
                        }
                        if (res.data.message === "userid not found") {
                            alert("Data not found")
                        }

                    })

            }

        }
        catch (error) {
            console.log('Error submitting form', error)
        }
        setErrors(errors)
    }

    //new password
    const history = useNavigate()
    const [errors1, setErrors1] = useState('')
    const [pwd, setPwd] = useState()
    const ChangePwd = async (e) => {
        e.preventDefault()
        const errors1 = {}
        try {
            if (!pwd) {
                errors1.pwd = "Enter your new password"
            }

            else {
                await axios.put(`http://localhost:8080/api/changePwd/${data.userid}/${pwd}`)
                handleClose()
                alert("Password changed successfully")
                history("/")
                setData({
                    userid: '',
                    email: ''
                })
            }

        }
        catch (error) {
            console.log('Error submitting form', error)
        }
        setErrors1(errors1)
    }

    //dialogbox
    const [open, setOpen] = useState();

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };


    return (

        <div className="body">
            <div>
                <Header />
            </div>
            <div className="container">
                <div className="head">
                    <div className="update">Forgot Password</div>
                </div>

                <div className="inputs">
                    {errors.noData && <p className='error'>{errors.noData}</p>}
                    <div className="input">
                        <img src={idIcon} alt="id" />
                        <input type="text" placeholder='User ID' value={data.userid} autoComplete="off" name="userid" onChange={handleChange} required />
                    </div>
                    <div className="input">
                        <img src={emailIcon} alt="id" />
                        <input type="email" placeholder='Email' value={data.email} autoComplete="off" name="email" onChange={handleChange} required />
                    </div>


                </div>

                <div className="submit-container">
                    <div className="submit" onClick={handleSubmit} >Submit</div>
                </div>

            </div>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Change Password</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        <div>
                            {errors1.pwd && <p className='error'>{errors1.pwd}</p>}
                            <div className="input">
                                <img src={pwdIcon} alt="id" />
                                <input type="password" placeholder='New Password' value={pwd} autoComplete="off" name="newPwd" onChange={(e) => setPwd(e.target.value)} required />
                            </div>
                        </div>
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={ChangePwd}>Change Password</Button>
                </DialogActions>
            </Dialog>


        </div>

    );
}

export default ForgotPassword
