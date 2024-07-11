import React, {useState, useEffect} from 'react'
import Dialog from '@mui/material/Dialog';
import Button from '@mui/material/Button';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import SignUp from './signup';
import closeIcon from '../images/close.png'
import axios from 'axios'
import { saveAs } from 'file-saver';

const UserRecords = () => {

    const [data, setData] = useState([])
    const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 5
  const lastIndex = currentPage * recordsPerPage
  const firstIndex = lastIndex - recordsPerPage
  const records = data.slice(firstIndex, lastIndex)
  const nPages = Math.ceil(data.length / recordsPerPage)
  const numbers = [...Array(nPages + 1).keys()].slice(1)
    useEffect(() => {
        fetchData()
      }, [data, currentPage]);
      const fetchData = async () => {
        try {
          const response = await fetch(`http://localhost:8080/api/getUser`);
          if (!response.ok) {
            throw new Error('Failed to fetch data');
          }
          const jsonData = await response.json();
          setData(jsonData)
        } catch (error) {
          console.error('Error fetching data:', error);
        }
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


  //dialogbox
  const [open, setOpen] = useState();
  const [open1, setOpen1] = useState();
  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClickOpen1 = () => {
    setOpen1(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleClose1 = () => {
    setOpen1(false);
  };

  const handleNo = () => {
    setOpen1(false)
  }

  const [rowIndex, setRowIndex] = useState()
  const handleRowClick = (value1) => {
    console.log('Clicked row index:', value1);
    setRowIndex(value1)
  };
  const handleYes = async() => {
    try {
        const response = await axios.delete(`http://localhost:8080/api/deleteUser/${rowIndex}`);
        if(response.data.message === "deleted successfully") {
          alert(`User (${rowIndex}) deleted successfully`)
          handleClose1()
        }
      } catch (error) {
        console.error('Error fetching data:', error);
      }
  }

  //handle Download
  const handleDownload = () => {
    const csvContent = data.map(row => Object.keys(data[0]).slice(0, -1).join(",") + "\n" + Object.values(row).slice(0, -1).join(',')).join('\n');
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
    saveAs(blob, 'data.csv');
  };
    
      if (!data || data.length === 0) {
        return (<div>
          <p className='leave-records'>User Records</p>
        </div>)
      }

    return(
        <div>

        <div>
            <p className='leave-records'>User Records</p><br />
        </div>
        <button className='addNew' onClick={() => { handleClickOpen() }}>Add New</button>
        <div>
        <button className='download1' onClick={handleDownload}>Download Records</button>
        </div>
        <table className="table">
        <thead className="thead-dark">
          <tr>
            <th>User Name</th>
            <th>User ID</th>
            <th>Email</th>
            <th>Designation</th>
            <th>Department</th>
          </tr>
        </thead>
        <tbody>
          {records.map((item, index) => (
            <tr key={index} onClick={() => handleRowClick(item.userid)}>
              <td>{item.username}</td>
              <td className='userid' onClick={() => { handleClickOpen1() }}>{item.userid}</td>
              <td>{item.email}</td>
              <td>{item.role}</td>
              <td>{item.category}</td>
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
            <SignUp />
          </DialogContentText>
        </DialogContent>
      </Dialog>

      <Dialog open={open1} onClose={handleClose1}>
        <DialogTitle>Delete Profile</DialogTitle>
        <DialogContent>
          <DialogContentText>
            Do you want to delete the user ({rowIndex}) permanently?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleNo}>No</Button><Button onClick={handleYes}>Yes</Button>
        </DialogActions>
      </Dialog>


        </div>

        

    )
}

export default UserRecords