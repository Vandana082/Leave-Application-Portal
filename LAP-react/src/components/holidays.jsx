import React, { useState, useEffect } from "react"


const Holidays = () => {

  //holidays

  const [data, setData] = useState([])
  const [currentPage, setCurrentPage] = useState(1);
  const recordsPerPage = 5
  const lastIndex = currentPage * recordsPerPage
  const firstIndex = lastIndex - recordsPerPage
  const records = data.slice(firstIndex, lastIndex)
  const nPages = Math.ceil(data.length / recordsPerPage)
  const numbers = [...Array(nPages + 1).keys()].slice(1)
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/holidays`);
        if (!response.ok) {
          throw new Error('Failed to fetch data');
        }
        const jsonData = await response.json();
        setData(jsonData);
        //console.log(data)
      } catch (error) {
        console.error('Error fetching data:', error);
      }
    };

    fetchData()
  }, [data, currentPage]);


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

  return (
    <div>
      <p className='leave-records'>List of Holidays 2024</p>

      <table class="table">
        <thead class="thead-dark">
          <tr>
            <th>Occasions / Festivals</th>
            <th>Date</th>
            <th>Day</th>
          </tr>
        </thead>
        <tbody>
          {records.map((item, index) => (
            <tr key={index}>
              <td>{item.occassion}</td>
              <td>{item.date}</td>
              <td>{item.day}</td>
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
    </div>
  )
}

export default Holidays