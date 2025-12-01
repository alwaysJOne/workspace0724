import { useState } from 'react'
import './App.css'
import AppRoutes from './routes/routes'
import { TodoProvider } from './context/TodoContext'

function App() {

  return (
    <TodoProvider>
      <AppRoutes />
    </TodoProvider>
  )
}

export default App
