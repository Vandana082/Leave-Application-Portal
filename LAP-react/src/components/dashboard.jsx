import React, { useState, useEffect } from 'react'
import '../emp-home.css'

const Dashboard = (props) => {

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








    return (

        <div>
            <p className="para">Your Leave Balance as of {currentDate.toLocaleDateString()}</p>
            <div className="leaves">            
                <div className="leaves-list">
                    <div>
                        <h4>Casual/Sick Leave</h4>
                        <p>Currently available<h5>{props.result2}</h5></p><hr className="det-hr" />
                        <p>Accrued so far this year<h5>{props.data2}</h5></p>
                        <p>Annual allotment<h5>12</h5></p>
                    </div>
                    <div>
                        <h4>Earned Leave</h4>
                        <p>Currently available<h5>{props.result1}</h5></p><hr className="det-hr" />
                        <p>Accrued so far this year<h5>{props.data1}</h5></p>
                        <p>Annual allotment<h5>15</h5></p>
                    </div>


                </div>
            </div>

        </div>


    )
}

export default Dashboard