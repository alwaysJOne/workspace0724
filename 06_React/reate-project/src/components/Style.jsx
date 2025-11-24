import React from 'react'
import './Style.css'; //외부css파일 import가능
import styled from 'styled-components';

const roundBoxStyle = {
    position: "absolute",
    top: 50,
    left: 50,
    width: 200,
    height: 200,
    backgroundColor: "blue",
    boarderRadius: 50,
    color: "white",
}

const RoundBox = styled.div`
    position: absolute;
    top: ${props => props.top || 50}px;
    left: 50px;
    width: 200px;
    height: 200px;
    background-color: blue;
    border-radius: 50px;
    color: white;
`



const Style = () => {
  return (
    <>
        <h3>1. Object로 css작성</h3>
        <p>인라인속성이며, 재사용성이 떨어지고 코드 복잡도가 높아짐.</p>
        <div style={{
            position: "relative",
            width: 400,
            height: 1500,
            backgroundColor: "#f1f1f1",
        }}>
            <h3>2. css-in-js로 스타일을 직접 작성</h3>
            <div style={roundBoxStyle}>
                <p>재사용이 가능하고 코드가 깔끔하다, 기존css기능 사용이 불가하고 체계적이지 못하다.</p>
                <div className='highlight'>class를 활용</div>
            </div>
            <div style={{...roundBoxStyle, top: 300}}>
                <p>3. 조건부 스타일 가능</p>
                {/* <div className={1+1 !== 2 && "highlight"}>class를 활용</div> */}
            </div>
            <RoundBox top={600}>
                <p>4. styled-components</p>
                <p>
                    js안에서 css문법 그대로 스타일을 작성할 수 있게 해주는 라이브러리
                    특정 css가 적용된 새로운 컴포넌트를 만들어 코드의 재사용성을 높여주고,
                    class명 충돌 걱정없이 사용이 가능.
                </p>
            </RoundBox>
             <RoundBox top={600}>
                <p>4. styled-components</p>
                <p>
                    js안에서 css문법 그대로 스타일을 작성할 수 있게 해주는 라이브러리
                    특정 css가 적용된 새로운 컴포넌트를 만들어 코드의 재사용성을 높여주고,
                    class명 충돌 걱정없이 사용이 가능.
                </p>
            </RoundBox>
             <RoundBox top={600}>
                <p>4. styled-components</p>
                <p>
                    js안에서 css문법 그대로 스타일을 작성할 수 있게 해주는 라이브러리
                    특정 css가 적용된 새로운 컴포넌트를 만들어 코드의 재사용성을 높여주고,
                    class명 충돌 걱정없이 사용이 가능.
                </p>
            </RoundBox>
        </div>
    </>
  )
}

export default Style