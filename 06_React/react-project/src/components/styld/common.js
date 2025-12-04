import styled from "styled-components";

export const Container = styled.div`
    max-width: 1000px;
    margin: 0 auto;
    padding: 24px;
    text-align: center;
`

export const Title = styled.h1`
    color: black;
    margin-bottom: 24px;
`

export const Button = styled.button`
    display: inline-block;
    background: #b260ffff;
    color: white;
    padding: 12px 24px;
    border-radius: 4px;
    text-decoration: none;
    margin: 12px;
    cursor: pointer;
    font-size: 18px;

    &:hover{
        scale: 0.98;
        color: white;
    }
`
