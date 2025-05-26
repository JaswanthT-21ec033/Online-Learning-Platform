import { useState } from 'react'
import { Route, Routes } from 'react-router'
import Login from "./components/auth/Login"
import Profile from "./components/auth/Profile"
import StudentDashboard from './components/student/Studentdashboard'
import InstructorDashboard from './components/instructor/InstructorDashboard'

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element ={<Login/>} />
        <Route path="/student" element={<StudentDashboard/>} />
        <Route path="/profile" element={<Profile/>} />
        <Route path="instructor" element={<InstructorDashboard/>}/>
      </Routes>
    </div>
  )
}

export default App
