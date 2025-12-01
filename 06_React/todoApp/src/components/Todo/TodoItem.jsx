import React, { useEffect } from 'react'
import { Checkbox, DeleteButton, TodoContent, TodoItemContainer, TodoText } from './TodoItem.styled'

const TodoItem = ({todo, onToggle, onDelete}) => {

    useEffect(() => {
        console.log(todo)
    },[todo])

    return (
        <TodoItemContainer>
            <TodoContent>
                <Checkbox 
                    type='checkbox'
                    checked={todo.completed}
                    onChange={() => onToggle(todo.id)}
                />
                <TodoText to={`/todos/${todo.id}`} completed={todo.completed ? 'true' : undefined}>
                    {todo.text}
                </TodoText>
            </TodoContent>
            <DeleteButton onClick={()=> onDelete(todo.id)}>삭제</DeleteButton>
        </TodoItemContainer>
    )
}

export default TodoItem