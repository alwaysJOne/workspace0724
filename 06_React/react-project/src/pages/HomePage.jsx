import React from 'react'
import styled from 'styled-components'
import { Container, Title } from '../components/styld/common'
import { Link } from 'react-router-dom'

const Button = styled(Link)`
    display: inline-block;
    background: #9f64fd;
    color: white;
    padding: 12px 24px;
    border-radius: 4px;
    text-decoration: none;
    margin: 12px;

    &:hover{
        scale: 0.98;
        color: white;
    }
`

const HomePage = () => {
  return (
    <Container>
        <Title>게시판 관리</Title>
        <Button to="/posts">게시글 목록</Button>
    </Container>
  )
}

export default HomePage