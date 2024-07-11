import React, { useState, useEffect } from 'react'
import { useLocation } from 'react-router-dom';
import '../emp-home.css'
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import axios from 'axios'
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

const EditLeaveForm = (props) => {

    
    

  //datepicker
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();

  const handleEndDateChange = date => {
    setEndDate(date.toLocaleDateString());
  };

  const handleStartDateChange = date => {
    setStartDate(date.toLocaleDateString())
  }

  //counting number of days
  const countSelectedDays = () => {
    if (!startDate || !endDate) return 0;
    const start = new Date(startDate);
    const end = new Date(endDate);
    const differenceInTime = end.getTime() - start.getTime();
    const differenceInDays = differenceInTime / (1000 * 3600 * 24);
    return Math.ceil(differenceInDays) + 1;
  };



  //Userdata
  
  const location = useLocation()
  const [user, setUser] = useState({})
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/get/${location.state.id}`);
        const jsonData = await response.json();
        setUser(jsonData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData()
  }, [location.state]);


  const [data, setData] = useState({
    leaveType: '',
    msg:  '',
    approver: ''  
})


    useEffect(() => {
        const fetchLeave = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/editLeave/${props.userid}`)
                const jsonData = await response.json()     
                console.log(jsonData)     
                setData({
                    leaveType: jsonData[0].leaveType,
                    msg: jsonData[0].msg,
                    approver: jsonData[0].approver
                })
                setStartDate(jsonData[0].startDate)
                setEndDate(jsonData[0].endDate)
            }
            catch (error) {
                console.error('Error fetching data:', error);
            }
        }
        fetchLeave()
      }, [props.userid])


 

  const handleChange = (event) => {
    setData({
      ...data,
      [event.target.name]: event.target.value
    })
  }

  //File upload

  const [selectedFile, setSelectedFile] = useState(null)
  const [errorMessage, setErrorMessage] = useState('');
  const handleFileChange = (event) => {
    const file = event.target.files[0];
    const allowedTypes = ['image/jpeg', 'image/png'];
    if (file && allowedTypes.includes(file.type)) {
      setSelectedFile(file);
      setErrorMessage('');
    } else {
      setSelectedFile(null);
      setErrorMessage('Please select a JPG, PNG, or PDF file');
    }
  };


  const [errors, setErrors] = useState({})
  const handleSubmit = async (e) => {
    e.preventDefault()
    const errors = {}
    if (props.result1 === 0) {
      alert("You do not have enough earned leaves")
    }
    else if (props.result2 === 0) {
      alert("You do not have enough casual/sick leaves")
    }
    else if (data.leaveType === 'Casual_Sick_Leave' && !selectedFile) {
      setErrorMessage('Please select a file');
    }
    else {
      const formData = new FormData()
      formData.append('userid', user.userid)
      formData.append('username', user.username)
      formData.append('leaveType', data.leaveType)
      formData.append('startDate', startDate)
      formData.append('endDate', endDate)
      formData.append('count', countSelectedDays())
      formData.append('msg', data.msg)
      formData.append('approver', data.approver)
      formData.append('file', selectedFile)
      formData.append('status', "Pending")
      try {
        await axios.put(`http://localhost:8080/api/updateLeave`, formData).then((res) => {
          if (res.data.message === "All fields are mandatory") {
            errors.noData = "All fields are mandatory"
          }
          if (res.data.message === "Leave updated successfully") {
            alert("Leave updated successfully")
          }
          if (res.data.message === "Leave request not found") {
            alert("Leave request not found")
            setData({
              leaveType: '',
              msg: '',
              approver: ''
            })
            setStartDate('')
            setEndDate('')
            setSelectedFile(null)
          }
        })
        setErrors(errors)
      }

      catch (error) {
        console.log('Error submitting form', error)
        console.log(selectedFile)
      }
    }
  }



  //usernames


  const [name, setName] = useState([])
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/management/usernames/${props.username}`);
        const jsonData = await response.json();
        setName(jsonData)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    }

    fetchData()
  }, [name, props.username]);


//dialogbox
const [open, setOpen] = useState();
const handleClickOpen = () => {
  setOpen(true);

};

const handleClose1 = () => {
  setOpen(false);
};


const handleYes = async () => {
  try {
    const response = await axios.delete(`http://localhost:8080/api/deleteLeave/${props.id}`)
    if(response.data.message === "Leave request deleted successfully") {
      alert(`Leave request of ${props.userid} deleted successfully`)
      handleClose1()
    }
  }
  catch (error) {
    console.log('Error fetching data:', error)
  }
}


  return (
    <div>

      <form>
        <div className="container1">
          <div className="head1">
            <div className="text1">Update LeaveForm</div>

          </div>

          <div className="inputs1">
            {errors.noData && <p className='error'>{errors.noData}</p>}
            <div className="input1">
              <select value={data.leaveType} name="leaveType" onChange={handleChange} required>
                <option value="">Select Leave Type</option>
                <option value="Casual_Sick_Leave">Casual-Sick Leave</option>
                <option value="Earned_Leave">Earned Leave</option>
              </select>
              <div className='input3'>
                <DatePicker selected={startDate} placeholderText="From Date" name='startDate' onChange={handleStartDateChange} selectsStart autoComplete="off" /></div>
              <div className='input3'><DatePicker selected={endDate} onChange={handleEndDateChange} selectsEnd startDate={startDate} endDate={startDate} minDate={startDate} autoComplete="off" placeholderText="To Date" name='endDate' /></div>

            </div>

            <div className="input2">
              <textarea placeholder='Enter your message here' name='msg' value={data.msg} onChange={handleChange}></textarea>
            </div>
            <div>
            <div className="input4">
              <select value={data.approver} name="approver" onChange={handleChange} required>
                <option value="">Select approver</option>
                {name.map((option, index) => (<option key={index} value={option.username}>{option.username}</option>))}
              </select>
              </div>
              {data.leaveType !== 'Casual_Sick_Leave' ? ('') : (data.leaveType === 'Casual_Sick_Leave' && 
              <div className='file' >
              <input type="file" name='file' accept=".jpg,.jpeg,.png" onChange={handleFileChange}/>
              </div>)
              } {errorMessage && <p style={{ color: 'red', marginLeft: '250px', marginTop: '10px' }}>{errorMessage}</p>}
            </div>
          </div>

        
          <div className="submit-container">
            <div className="submit" onClick={() => {handleClickOpen()}}>Delete</div>
            <div className="submit" onClick={handleSubmit}>Update</div>
          </div>

        </div>

      </form>

      <Dialog open={open}>
        <DialogTitle>
          Delete leave request
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
              Do you want to delete the leave request of {props.userid}?
          </DialogContentText>
          <DialogActions>
        <Button onClick={() => {handleYes(); props.close();}}>Yes</Button>
        <Button onClick={handleClose1}>No</Button>
        </DialogActions>
        </DialogContent>
      </Dialog>



    </div>










  )
}

export default EditLeaveForm