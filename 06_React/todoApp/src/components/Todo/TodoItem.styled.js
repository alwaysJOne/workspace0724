import { Link } from "react-router-dom";
import styled from "styled-components";

export const TodoItemContainer = styled.li`
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 6px 12px;
    border: 1px solid #e3e3e3;

`

export const TodoContent = styled.div`
    display: flex;
    align-items: center;
    gap: 12px;
    flex: 1;
`

export const TodoText = styled(Link)`
    text-decoration: ${props => props.completed ? 'line-through' : 'none'};
    color: ${props => props.completed ? '#999' : '#24854cff'};
    cursor: pointer;
    transition: color 0.2s;

    &:hover{
        scale: 0.98;
    }
`

export const Checkbox = styled.input`
    width: 18px;
    height: 18px;
    cursor: pointer;
`

export const DeleteButton = styled.button`
    color: #a50303ff;
    background: none;
    border: none;
    cursor: pointer;
    outline: none;
    padding: 4px 8px;

    &:hover{
        scale: 0.98;
    }
`