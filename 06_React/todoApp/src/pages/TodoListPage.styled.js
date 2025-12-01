import styled from "styled-components";

export const Container = styled.div`
    max-width: 800px;
    margin: 0 auto;
`

export const Title = styled.h1`
    font-size: 42px;
    font-weight: bold;
    margin-bottom: 24px;
    color: #333;
`

export const FilterButtons = styled.div`
    display: flex;
    gap: 12px;
    margin-bottom: 24px;
`

export const FilterButton = styled.button`
    padding: 8px 16px;
    border: 1px solid #e2e2e2;
    cursor: pointer;
    border-radius: 4px;
    transition: all 0.2s;
    background: ${props => props.active ? '#5833ffff' : '#ffffff'};
    color: ${props => props.active ? '#fff' : '#333'};
    outline: none;

   &:hover{
    scale: 0.98;
   }
`