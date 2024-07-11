import React, { useState, useEffect } from 'react'
import noleave from '../images/noleave.png'
import { saveAs } from 'file-saver';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import closeIcon from '../images/close.png'
import downloadIcon from '../images/download.png'


const LeaveRecords = () => {

  //Leave records
  const [status, setStatus] = useState('All');
  const [search, setSearch] = useState("")
  const [data, setData] = useState([])
  const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 5
  const lastIndex = currentPage * recordsPerPage
  const firstIndex = lastIndex - recordsPerPage
  const records = data.slice(firstIndex, lastIndex)
  const nPages = Math.ceil(data.length / recordsPerPage)
  const numbers = [...Array(nPages + 1).keys()].slice(1)
  useEffect(() => {
    fetchData(status, search)
  }, [data, status, currentPage, search]);
  const fetchData = async (status) => {
    try {
      const response = await fetch(`http://localhost:8080/api/leaveRecords?status=${status}&search=${search}`);
      if (!response.ok) {
        throw new Error('Failed to fetch data');
      }
      const jsonData = await response.json();
      setData(jsonData)
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  };



  //filter
  const handleFilterClick = (status, search) => {
    setStatus(status);
    fetchData(status, search);
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
    const csvContent = data.map(row => Object.keys(data[0]).slice(0, -1).join(",") + "\n" + Object.values(row).slice(0, -1).join(',')).join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
    saveAs(blob, 'data.csv');
  };

  const [id, setId] = useState()
  const handleRowClick = (value1) => {
    setId(value1)
    //console.log(rowIndex, id)
  };


  //dialogbox
const [open1, setOpen1] = useState();

const handleClickOpen1 = () => {
  setOpen1(true);

};

const handleClose1 = () => {
  setOpen1(false);
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


//handle Download
const handleDownloadImage = async() => {
  try {
    const response = await fetch(`http://localhost:8080/api/file/${id}`);
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

const handleSearch = (event) => {
  setSearch(event.target.value)
}

  if ((!data || data.length === 0) && search === "") {
    return <div>
      <p className='leave-records'>Leave Records 2024</p>
      <div className='filter'>
        <button className={status === 'All' ? 'active' : ''} onClick={() => handleFilterClick('All')}>All</button><button className={status === 'Pending' ? 'active' : ''} onClick={() => handleFilterClick('Pending')}>Pending</button><button className={status === 'Accepted' ? 'active' : ''} onClick={() => handleFilterClick('Accepted')}>Accepted</button><button className={status === 'Decined' ? 'active' : ''} onClick={() => handleFilterClick('Declined')}>Declined</button>
        </div>
      <img src={noleave} alt="id" className='norequest' />
    </div>
  }





  return (
    <div>

      <p className='leave-records'>Leave Records 2024</p><br />
<div>
      <div className='filter'>
        <button className={status === 'All' ? 'active' : ''} onClick={() => handleFilterClick('All')}>All</button><button className={status === 'Pending' ? 'active' : ''} onClick={() => handleFilterClick('Pending')}>Pending</button><button className={status === 'Accepted' ? 'active' : ''} onClick={() => handleFilterClick('Accepted')}>Accepted</button><button className={status === 'Decined' ? 'active' : ''} onClick={() => handleFilterClick('Declined')}>Declined</button>
        </div>
        <div><input type='text' className='search' name='search' onChange={handleSearch} value={search} placeholder='Search by UserId' autoComplete='off' /></div>
        <div>
        <button className='download' onClick={handleDownload}>Download Records</button>
        </div>
      </div>
      
        

      <table className="table">

        <thead className="thead-dark">
          <tr>
            <th>User ID</th>
            <th>User Name</th>
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
          {records && records.map((item, index) => (
            <tr key={index} onClick={() => handleRowClick(item._id)}>
              <td>{item.userid}</td>
              <td>{item.username}</td>
              <td>{item.leaveType}</td>
              <td>{item.startDate}</td>
              <td>{item.endDate}</td>
              <td>{item.msg}</td>
              <td>{item.approver}</td>
              {item.file === null ? (<td>Not available</td>) : (item.file !== null && <td><button className='view' onClick={handleClickOpen1}>View attachment</button></td>)}
              <td><span style={{ color: item.status === 'Accepted' ? 'green' : item.status === 'Declined' ? 'red' : '#161850' }}>{item.status}</span></td>
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

export default LeaveRecords