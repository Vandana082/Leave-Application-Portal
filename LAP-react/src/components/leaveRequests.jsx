import '../emp-home.css'
import React, { useState, useEffect } from 'react'
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import axios from 'axios'
import noRequest from '../images/norequests.png'
import closeIcon from '../images/close.png'
import downloadIcon from '../images/download.png'




const LeaveRequests = (props) => {

  //Date function
  const [currentDate, setCurrentDate] = useState(new Date())
  useEffect(() => {
    const timerID = setInterval(() => tick(), 1000)
    return () => {
      clearInterval(timerID)
    }
  }, [])
  const tick = () => {
    setCurrentDate(new Date())
  }


  //Leave records
  const [data, setData] = useState([])
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/leaveRequestRecords/${props.data2}`);

        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const jsonData = await response.json();
        setData(jsonData);
        
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData()
    //console.log(username.state.name)
  }, [data, props.data2]);

  //dialogbox
  const [open, setOpen] = useState();

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  //handle accept 
  const [rowIndex, setRowIndex] = useState()
  const [username, setUsername] = useState()
  const [id, setId] = useState()
  const handleRowClick = (value1, value2, value3) => {
    console.log('Clicked row index:', value1, value2, value3);
    setId(value1)
    setRowIndex(value2)
    setUsername(value3)
  };

  const handleAccept = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/api/status/${rowIndex}/Accepted`);
      console.log(response.data)
      handleClose()
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };
  //handle reject
  const handleReject = async () => {
    try {
      const response = await axios.put(`http://localhost:8080/api/status/${rowIndex}/Declined`);
      console.log(response.data)
      handleClose()
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };

  //handle file
  const [imageData, setImageData] = useState()
 useEffect(()=>{
  handleFile()
 })
const handleFile = () => {
  // Fetch image data from backend API
  fetch(`http://localhost:8080/api/file/${id}`)
  .then(response => response.arrayBuffer())
  .then(data => {
    // Convert array buffer to base64 encoded string
    const base64String = btoa(
      new Uint8Array(data).reduce((data, byte) => {
        return data + String.fromCharCode(byte);
      }, '')
    );
    setImageData(base64String);
  })
  .catch(error => {
    //console.error('Error fetching image:', error);
  });
}

//dialogbox2
const [open1, setOpen1] = useState();

const handleClickOpen1 = () => {
  setOpen1(true);

};

const handleClose1 = () => {
  setOpen1(false);
};


//handle Download
const handleDownload = async() => {
  try {
    const response = await fetch(`http://localhost:8080/api/file1/${rowIndex}`);
    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'image.jpg'; // Change the file name if needed
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    window.URL.revokeObjectURL(url);
  } catch (error) {
    console.error('Error downloading image:', error);
  }
};


  

  if (!data || data.length === 0) {
    return <div>
      <p className='leave-records'>Leave Requests as of {currentDate.toLocaleDateString()}</p>
      <img src={noRequest} alt="id" className='norequest' />
    </div>;
  }



  return (
    <div>

      <p className='leave-records'>Leave Requests as of {currentDate.toLocaleDateString()}</p>
      <table className="table">
        <thead className="thead-dark">
          <tr>
            <th>Employee ID</th>
            <th>Employee Name</th>
            <th>Type of Leave</th>
            <th>From date</th>
            <th>To date</th>
            <th>Reason</th>
            <th>Attachment</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {data.map((item, index) => (
            <tr key={index} onClick={() => handleRowClick(item._id, item.userid, item.username)}>
              <td>{item.userid}</td>
              <td>{item.username}</td>
              <td>{item.leaveType}</td>
              <td>{item.startDate}</td>
              <td>{item.endDate}</td>
              <td>{item.msg}</td>
              {item.file === null ? (<td>Not available</td>) : (item.file !== null && <td><button className='view' onClick={handleClickOpen1}>View attachment</button></td>)}
              <td><button onClick={() => { handleClickOpen() }} className='pending'>{item.status}</button></td>
            </tr>
          ))}
        </tbody>
      </table>

      
    

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Pending status</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Do you want to approve the leave request of {username} ({rowIndex})
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleAccept}>Accept</Button><Button onClick={handleReject}>Decline</Button>
        </DialogActions>
      </Dialog>


      <Dialog open={open1}>
        <DialogTitle>
        <DialogActions>
        <Button onClick={handleDownload}><img src={downloadIcon} alt="download"/></Button>
        <Button onClick={handleClose1}><img src={closeIcon} alt="close"/></Button>
        </DialogActions>
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
          <div>
              {imageData ? (
              <img
            src={`data:image/jpeg;base64,${imageData}`}
          alt="API"
        />
      ) : (
        <p>Loading image...</p>
      )}
    </div>
          </DialogContentText>
        </DialogContent>
      </Dialog>





    </div>
  )
}

export default LeaveRequests