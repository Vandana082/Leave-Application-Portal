import React, { useState, useEffect } from 'react'
import noleave from '../images/noleave.png'
import { saveAs } from 'file-saver';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import DialogActions from '@mui/material/DialogActions';
import Button from '@mui/material/Button';
import closeIcon from '../images/close.png'
import EditLeaveForm from './editLeaveForm';
import downloadIcon from '../images/download.png'



const EmpLeave = (props) => {

  //Leave records
  const [status, setStatus] = useState('All');
  const [data, setData] = useState([])
  const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 5
  const lastIndex = currentPage * recordsPerPage
  const firstIndex = lastIndex - recordsPerPage
  const records = data.slice(firstIndex, lastIndex)
  const nPages = Math.ceil(data.length / recordsPerPage)
  const numbers = [...Array(nPages + 1).keys()].slice(1)
  useEffect(() => {
    fetchData(status)
  }, [data, status, props.userid]);
  const fetchData = async (status) => {
    try {
      const response = await fetch(`http://localhost:8080/api/empLeave/${props.userid}?status=${status}`);
      if (!response.ok) {
        throw new Error('Failed to fetch data');
      }
      const jsonData = await response.json();
      setData(jsonData);
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  //filter
  const handleFilterClick = (status) => {
    setStatus(status);
    fetchData(status);
  };

  //pagination
  const prePage = () => {
    if (currentPage !== 1) {
      setCurrentPage(currentPage - 1)
    }
  }

  const changeCPage = (id) => {
    setCurrentPage(id)
  }

  const nextPage = () => {
    if (currentPage !== nPages) {
      setCurrentPage(currentPage + 1)
    }
  }


  //handle Download
  const handleDownload = () => {
    const csvContent = data.map(row => Object.values(row).slice(0, -1).join(',')).join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
    saveAs(blob, 'data.csv');
  };

  //handle accept 
  const [rowIndex, setRowIndex] = useState()
  const [id, setId] = useState()
  const handleRowClick = (value1, value2, value3, value4) => {
    setRowIndex(value1)
    setId(value3)
    if (value2 === "Pending") {
      setOpen(true)
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

   //dialogbox
   const [open, setOpen] = useState();
 
   const handleClose = () => {
     setOpen(false);
   };

   const [open1, setOpen1] = useState();

const handleClickOpen1 = () => {
  setOpen1(true);

};

const handleClose1 = () => {
  setOpen1(false);
};


   //handle Download
const handleDownloadImage = async() => {
  try {
    const response = await fetch(`http://localhost:8080/api/file/${id}`);
    const blob = await response.blob();
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = 'image.jpg';
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
      <p className='leave-records'>Leave Records 2024</p>
      <div className='filter1'>
        <button className={status === 'All' ? 'active' : ''} onClick={() => handleFilterClick('All')}>All</button><button className={status === 'Pending' ? 'active' : ''} onClick={() => handleFilterClick('Pending')}>Pending</button><button className={status === 'Accepted' ? 'active' : ''} onClick={() => handleFilterClick('Accepted')}>Accepted</button><button className={status === 'Decined' ? 'active' : ''} onClick={() => handleFilterClick('Declined')}>Declined</button>
      </div>
      <img src={noleave} alt="id" className='norequest' />
    </div>;
  }



  return (
    <div>
      <p className='leave-records'>Leave Records 2024</p>
      <div className='filter1'>
        <button className={status === 'All' ? 'active' : ''} onClick={() => handleFilterClick('All')}>All</button><button className={status === 'Pending' ? 'active' : ''} onClick={() => handleFilterClick('Pending')}>Pending</button><button className={status === 'Accepted' ? 'active' : ''} onClick={() => handleFilterClick('Accepted')}>Accepted</button><button className={status === 'Decined' ? 'active' : ''} onClick={() => handleFilterClick('Declined')}>Declined</button>
      </div>
      <div>
        <button className='download1' onClick={handleDownload}>Download Records</button>
        </div>
      <table class="table">
        <thead class="thead-dark">
          <tr>
            <th>Type of Leave</th>
            <th>From date</th>
            <th>To date</th>
            <th>Reason</th>
            <th>Approved by</th>
            <th>Attachment</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody>
          {records.map((item, index) => (
            <tr key={index} className={item.status === "Pending" ? 'pointer' : ''}>
              <td onClick={()=> {handleRowClick(item.userid, item.status, item._id, null)}}>{item.leaveType}</td>
              <td onClick={()=> {handleRowClick(item.userid, item.status, item._id, null)}}>{item.startDate}</td>
              <td onClick={()=> {handleRowClick(item.userid, item.status, item._id, null)}}>{item.endDate}</td>
              <td onClick={()=> {handleRowClick(item.userid, item.status, item._id, null)}}>{item.msg}</td>
              <td onClick={()=> {handleRowClick(item.userid, item.status, item._id, null)}}>{item.approver}</td>
              {item.file === null ? (<td onClick={() => {handleRowClick(item.userid, item.status, null, null)}}>Not available</td>) : (item.file !== null && <td onClick={() => {handleRowClick(null, null, item._id, null)}}><button className='view' onClick={handleClickOpen1}>View attachment</button></td>)}
              <td onClick={()=> {handleRowClick(item.userid, item.status, item._id, null)}}><span style={{ color: item.status === 'Accepted' ? 'green' : item.status === 'Declined' ? 'red' : '#161850' }}>{item.status}</span></td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className='pagination'>
        <div className='page-items'>
          <button className='page-link' onClick={prePage}>Prev</button>
        </div>
        {numbers.map((n, i) => (
          <div className={`page-item ${currentPage === n ? 'active' : ''}`} key={i}>
            <button className='page-item' onClick={() => changeCPage(n)}>{n}</button>
          </div>
        ))}
        <div className='page-items'>
          <button className='page-link' onClick={nextPage}>Next</button>
        </div>
      </div>


      <Dialog open={open}>
      <DialogTitle>
        <DialogActions>
            <Button onClick={handleClose}><img src={closeIcon} alt="close"/></Button>
            </DialogActions>
            </DialogTitle>
        <DialogContent>
          <DialogContentText>
            <EditLeaveForm userid={rowIndex} username={props.username} id={id} close={handleClose}/>
          </DialogContentText>
        </DialogContent>
      </Dialog>

      <Dialog open={open1}>
        <DialogTitle>
        <DialogActions>
        <Button onClick={handleDownloadImage}><img src={downloadIcon} alt="download"/></Button>
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

export default EmpLeave