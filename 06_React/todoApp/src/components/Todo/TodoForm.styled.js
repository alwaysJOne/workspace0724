import styled from "styled-components";

export const FormContainer = styled.div`
    display: flex;
    gap: 12px;
    margin-bottom: 24px;
`
export const Select = styled.select`
    padding: 12px;
    border: 1px solid #e2e2e2;
    outline: none;
    border-radius: 4px;
    background: white;
    cursor: pointer;

    &:focus{
        border-color: #5833ffff;
    }
`

export const Input = styled.input`
    flex: 1;
    padding: 12px;
    border: 1px solid #e2e2e2;
    outline: none;
    border-radius: 4px;

    &:focus{
        border-color: #5833ffff;
    }
`

export const AddButton = styled.button`
    padding: 12px 24px;
    border: none;
    color: #ffffff;
    background: #5833ffff;
    border-radius: 4px;
    cursor: pointer;

    &:hover{
        scale: 0.98;
    }
`

export const TodoListContainer = styled.ul`
    list-style: none;
` 

export const EmptyMessage = styled.div`
    text-align: center;
    padding: 36px;
    color: #999999;
    font-size: 16px;
`




