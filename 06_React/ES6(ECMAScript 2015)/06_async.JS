//기존 callback 함수
const getTest = (data, cllback) => {
    console.log("서버로 값 전달 : ", data);
    setTimeout(() => {
        console.log("서버에서 응답도착")
        cllback("성공");
    }, 3000)
}

// console.log("시작")
// getTest({ id: 30 }, (result) => {
//     console.log(result)
//     getTest({ id: 30 }, (result) => {
//         console.log(result)
//         getTest({ id: 30 }, (result) => {
//             console.log(result)
//         })
//     })
// })
// console.log("끝")

const getTest2 = (data) => {
    return new Promise((resolve, reject)=> {
        console.log("서버로 값 전달 : ", data);
        setTimeout(() => {
            console.log("서버에서 응답도착")
            resolve("성공");
        }, 3000)
    })
}

// getTest2({id: 20})
//     .then(result => {
//         console.log(result)
//         return getTest2({id: 21});
//     })
//     .then(result => {
//         console.log(result);
//     })
//     .finally(() => {
//         console.log("끝")
//     })

const asyncTest = async () => {
    try{
        const result = await getTest2({id: 1});
        console.log("첫번째 결과 : ", result);
        const result2 = await getTest2({id: 2});
        console.log("첫번째 결과 : ", result2);
        const result3 = await getTest2({id: 3});
        console.log("첫번째 결과 : ", result3);
    } catch (error){
        console.log("에러발생");
    }
}

asyncTest();