import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import LifecycleTest from './components/LifecycleTest'
import CommentBox from './components/CommentBox'

function App() {
  // const [isView, setIsView] = useState(false);
  // const toggleButton = () => {
  //   setIsView(!isView);
  // }

  return (
    <>
      {/* {isView && <LifecycleTest />}
      <button onClick={toggleButton}>
        {isView ? "숨기기" : "보이기"}
      </button> */}

        <CommentBox />
    </>
  )
}

export default App
