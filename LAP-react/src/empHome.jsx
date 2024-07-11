import React, { useState, useEffect } from 'react'
import './emp-home.css'
import axios from 'axios'
import { useLocation } from 'react-router-dom'
import Header from './components/header'
import Dashboard from './components/dashboard'
import LeaveForm from './components/leaveForm'
import Holidays from './components/holidays'
import EmpLeave from './components/empLeave'
import ChangePassword from './components/changePassword'



const EmpHome = () => {

  //logout
  const handleLogout = async () => {
    try {
      await axios.post('http://localhost:8080/api/logout')
      localStorage.removeItem('token')
      window.location.href = '/'
    }
    catch (error) {
      console.log('logout failed:', error)
    }
  }




  //data
  const location = useLocation()

  const [data, setData] = useState({})
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/get/${location.state.id}`);
        const jsonData = await response.json();
        setData(jsonData);
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData()
  }, [location.state]);


  //Sidenav links
  const [active, setActive] = useState("Dashboard")


  //Earned leave details
  const [data1, setData1] = useState([])
  const [result1, setResult1] = useState(0)
  const [result2, setResult2] = useState(0)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/sum/${data.userid}/Earned_Leave`);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const jsonData = await response.json();
        setData1(jsonData);
        setResult1(15 - data1)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData()
  }, [data, result1, data1, data.userid]);


  //sick leave details
  const [data2, setData2] = useState([])
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/sum/${data.userid}/Casual_Sick_Leave`);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const jsonData = await response.json();
        setData2(jsonData);
        setResult2(12 - data2)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData()
  }, [data, result2, data2, data.userid]);





  return (
    <div>
      <div>
        <Header />
      </div>
      <div className="body-data">
        <div className="side-nav">
          <img src={'https://media1.thehungryjpeg.com/thumbs2/ori_3656345_ykrqulvid8kmquzhy86g3sjunvdqvsc4z8r7ppkk_monogram-at-logo-design.jpg'} className="img-1" alt="Logo" />
          <h2>{data.username}</h2>
          <p>
            {data.role}<br />
            {data.userid}<br />
            {data.email}
          </p>

          <hr className="nav-hr" />

          <button className='btn' onClick={() => setActive("Dashboard")}>Dashboard</button><br /><br />
          <button className='btn' onClick={() => setActive("LeaveForm", { state: { id: data.userid } })}>Apply for Leave</button><br /><br />
          <button className='btn' onClick={() => setActive("empLeave")}>Leave Records</button><br /><br />
          <button className='btn' onClick={() => setActive("Holidays")}>Calender events</button><br /><br />
          <button className='btn' onClick={() => setActive("ChangePassword")}>Change Password</button><br /><br />
          <div className="logout-container">
            <div className="logout" onClick={handleLogout}>Logout</div></div>
        </div>

        <div className="details">
          {active === "Dashboard" && <Dashboard data={Dashboard} result1={result1} result2={result2} data1={data1} data2={data2} />}
          {active === "LeaveForm" && <LeaveForm data={LeaveForm} result1={result1} result2={result2} />}
          {active === "empLeave" && <EmpLeave data={EmpLeave} userid={data.userid} username={data1.username} />}
          {active === "Holidays" && <Holidays data={Holidays} />}
          {active === "ChangePassword" && <ChangePassword data={ChangePassword} userid={data.userid} />}






        </div>
      </div>

    </div>
  );
}

export default EmpHome