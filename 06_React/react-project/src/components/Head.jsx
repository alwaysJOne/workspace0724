import React from 'react'

const Head = ({type, children}) => {

    if(type === "h2"){
        return <h2>안녕하세요</h2>
    }

    return (
        <>
            <h1>안녕하세요</h1>
            {children}
        </>
    )
}

export default Head