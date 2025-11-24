import React from 'react'

const text = "Hello, JSX";
const num = 100;

const loginUser = {
    name: "최지원",
    id: "jiwon1004",
    age: 19,
    info: "안녕하세요.",
}

const imgUrl = "https://i.namu.wiki/i/uwKtyJQdxUUgL4cU3NiJmWGeC7fm1ezwU2u5MebKQ9hw6tXWQzIzUG0CDmDtKjyIqhtHvnIZKNabLNztdJwiIQ.webp";

const numbers = [1,3,5,7,9];

const userList = [{
    name: "최지원",
    id: "jiwon1004",
    age: 19,
    info: "안녕하세요.",
},{
    name: "김지원",
    id: "jiwon1004",
    age: 19,
    info: "안녕하세요.",
},{
    name: "이지원",
    id: "jiwon1004",
    age: 19,
    info: "안녕하세요.",
}]

const getImg = () => <img src={imgUrl} alt="빵빵이" width={100} />;

const JavaScript = () => {
  return (
    <>
        <h1>React의 JS</h1>
        <h2>변수 표현 방법</h2>
        <ul>
            <li>{text}</li>
            <li>{text + " TEST"}</li>
        </ul>
        <ul>
            <li>{num}</li>
            <li>{num + 900}</li>
        </ul>
        <h2>Boolean 값</h2>
        <ul>
            <li>{true}</li>
            <li>{false}</li>
            <li>{null}</li>
            <li>{undefined}</li>
        </ul>

        <h2>Object, Array</h2>
        <ul>
            {
                // [name, id, age, info]
                Object.keys(loginUser).map((key) => 
                    <li key={key}> {key} : {loginUser[key]}; </li>
                )
                // <li> name : 최지원 </li>
                // <li> id : jiwon1004 </li>
                // <li> age : 19 </li>
                // <li> info : 안녕하세요. </li>
            }
            <li>{numbers}</li> {/*배열 그대로 출력(문자열로 자동 변환) */}
            {[<li key={1}>111</li>,<li key={2}>222</li>,<li key={3}>333</li>]}
        </ul>

        <h2>태그 속성에 값 넣기</h2>
        <ul>
            <img src={imgUrl} alt="빵빵이" width={100} />
        </ul>

        <h2>조건부 렌더링</h2>
        <ul>
            <li>{num > 10 ? "10보다 큼" : "10보다 작음"}</li>
            {num !== 10 && <li>num은 10보다 작음.</li>}
            {num === 10 && <li>num은 10보다 작음.</li>}
            {num !== 10 || <li>num은 10보다 작음.</li>}
            {num === 10 || <li>num은 10보다 작음.</li>}

            <h3>삼항 연산자</h3>
            <li>{1 + 1 === 2 ? "참이다" : "거짓이다"}</li>

            <h3>AND 연산자</h3>
            <li>{1 + 1 === 2 && "AND연산자1"}</li>
            <li>{1 + 1 !== 2 && "AND연산자2"}</li>
            {userList.length !== 0 &&
                userList.map(u => <li>{u.name}</li>)}

            <h3>OR 연산자</h3>
            <li>{1 + 1 === 2 || "OR연산자1"}</li>
            <li>{1 + 1 !== 2 || "OR연산자2"}</li>
            {userList.length === 0 ||
                userList.map(u => <li>{u.name}</li>)}
        </ul>

        <h2>함수 호출로 태그가 반환되어 올 수 있음</h2>
        <ul>
            <li>{getImg()}</li>
            <li>{getImg()}</li>
            <li>{getImg()}</li>
        </ul>
    </>
  )
}

export default JavaScript;