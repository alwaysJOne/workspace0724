import { useState } from 'react'
import JavaScript from './components/JavaScript'
import './App.css'
import Style from './components/Style'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      {/* <JavaScript /> */}
      <Style />
    </>
  )
}

export default App
