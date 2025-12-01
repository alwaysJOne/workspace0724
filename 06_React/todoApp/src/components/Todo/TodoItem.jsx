import React from 'react'
import { TodoItemContainer } from './TodoItem.styled'
import { Link } from 'react-router-dom'

const TodoItem = ({todo}) => {
  return (
    <TodoItemContainer>
        <input 
            type='checkbox'
        />
        <Link>
            {todo.text}
        </Link>
        <button>삭제</button>
    </TodoItemContainer>
  )
}

export default TodoItem