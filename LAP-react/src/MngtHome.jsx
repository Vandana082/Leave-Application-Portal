import React, { useState, useEffect } from 'react'
import { useLocation } from 'react-router-dom'
import Header from './components/header'
import './emp-home.css'
import LeaveRequests from './components/leaveRequests'
import Holidays from './components/holidays'
import LeaveRecords from './components/leaveRecords'
import ChangePassword from './components/changePassword'
import axios from 'axios'
import UserRecords from './components/userRecords'
import LeaveForm from './components/leaveForm'
import Dashboard from './components/dashboard'
import EmpLeave from './components/empLeave'

const MngtHome = () => {


    const location = useLocation()

    const [data1, setData1] = useState({})
    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/api/get/${location.state.id}`);
                const jsonData = await response.json();
                setData1(jsonData);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData()
    }, [location.state.id]);


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


    //Sidenav links
    const [active, setActive] = useState("Dashboard")
    //Earned leave details
  const [data, setData] = useState([])
  const [result1, setResult1] = useState(0)
  const [result2, setResult2] = useState(0)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/sum/${data1.userid}/Earned_Leave`);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const jsonData = await response.json();
        setData(jsonData);
        setResult1(15 - data)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };
    fetchData()
  }, [data1, result1, data, data1.userid]);


  //sick leave details
  const [data2, setData2] = useState([])
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/sum/${data1.userid}/Casual_Sick_Leave`);
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
  }, [data1, result2, data2, data1.userid]);


  




    return (

        <div>

            <Header />

            <div className="body-data">

                <div className="side-nav">
                    <img src={'https://media1.thehungryjpeg.com/thumbs2/ori_3656345_ykrqulvid8kmquzhy86g3sjunvdqvsc4z8r7ppkk_monogram-at-logo-design.jpg'} className="img-1" alt="Logo" />
                    <h2>{data1.username}</h2>
                    <p>
                        {data1.role}<br />
                        {data1.userid}<br />
                        {data1.email}
                    </p>

                    <hr className="nav-hr" />

                    <button className='btn' onClick={() => setActive("Dashboard")}>Dashboard</button><br /><br />
                    <button className='btn' onClick={() => setActive("LeaveRecords")}>All Leave Records</button><br /><br />
                    <button className='btn' onClick={() => setActive("LeaveRequests")}>Leave Requests</button><br /><br />
                    <button className='btn' onClick={() => setActive("LeaveForm")}>Apply for Leave</button><br /><br />
                    <button className='btn' onClick={() => setActive("EmpLeave")}>Your Leave Records</button><br /><br />
                    {data1.userid !== 'AT-102' ? ('') : (data1.userid === 'AT-102' && <div><button className='btn' onClick={() => setActive('UserRecords')}>User Lists</button><br /><br /></div>)}
                    <button className='btn' onClick={() => setActive("Holidays")}>Calender events</button><br /><br />
                    <button className='btn' onClick={() => setActive("ChangePassword")}>Change Password</button><br /><br />


                    <div className="logout-container">
                        <div className="logout" onClick={handleLogout}>Logout</div></div>
                </div>


                <div className="details">
                
                    {active === "Dashboard" && <Dashboard data={Dashboard} result1={result1} result2={result2} data1={data} data2={data2} category={data1.category} />}
                    {active === "LeaveRecords" && <LeaveRecords data={LeaveRecords} />}
                    {active === "LeaveRequests" && <LeaveRequests data={LeaveRequests} data2={data1.username} />}
                    {active === "LeaveForm" && <LeaveForm data={LeaveForm} data1={data1.username}/>}
                    {active === "EmpLeave" && <EmpLeave data={EmpLeave} userid={data1.userid} username={data1.username}/>}
                    {active === "UserRecords" && <UserRecords data={UserRecords} />}
                    {active === "Holidays" && <Holidays data={Holidays} />}
                    {active === "ChangePassword" && <ChangePassword data={ChangePassword} userid={data1.userid} />}
                    

                    <div>



                    </div>





                </div>












            </div>

        </div>

    )
}

export default MngtHome