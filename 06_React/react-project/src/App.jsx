import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom'
import HomePage from './pages/HomePage'
import PostListPage from './pages/PostListPage'

function App() {

  return (
    <Router>
      <Routes>
        <Route path='/' element={<HomePage />}/>
        <Route path="/posts" element={<PostListPage />} />
      </Routes>
    </Router>
  )
}

export default App
