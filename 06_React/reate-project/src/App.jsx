import { useState } from 'react'
import JavaScript from './components/JavaScript'
import './App.css'
import Product from './components/Product'
import styled from 'styled-components'

const products = [{
    product_id: 1,
    product_name: "삼성 갤럭시S25",
    price: 1000000,
    color: "블랙",
},{
    product_id: 2,
    product_name: "삼성 갤럭시S25 울트라",
    price: 1100000,
    color: "화이트",
},{
    product_id: 3,
    product_name: "아이폰 17",
    price: 1050000,
    color: "민트",
}]

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
`

const Th = styled.th`
  background: #8a8a8a;
  color: white;
  padding: 12px;
  border: 1px solid #afafaf;
`

const Tr = styled.tr`
  
`

function App() {

  return (
    <>
      {/* <JavaScript /> */}
      {/* <Style /> */}
      <Table>
        <thead>
          <tr>
            <Th>제품명</Th>
            <Th>가격</Th>
            <Th>색상</Th>
          </tr>
        </thead>
        <tbody>
          {products.map(p => <Product key={p.product_id} product={p}/>)}
        </tbody>
      </Table>
    </>
  )
}

export default App
