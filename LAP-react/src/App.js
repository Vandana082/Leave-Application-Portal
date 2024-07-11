import './App.css';
import EmpHome from './empHome.jsx'
import Login from './login'
import MngtHome from './MngtHome.jsx'
import ForgotPassword  from './forgotPassword.jsx'
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
//import {useState} from 'react'

function App() {
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={<Login />}/>
          <Route path="/empHome" element={<EmpHome />}/>
          <Route path="/mngtHome" element={<MngtHome />}/>
          <Route path="/forgotPassword" element={<ForgotPassword />}/>
        </Routes>
      </Router>      
    </div>
  );
}

export default App;