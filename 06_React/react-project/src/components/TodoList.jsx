import React from 'react'
import { CheckBox, DeleteButton, ListContainer, TodoItem, TodoText } from './TodoList.styled'
import useTodoStore from '../store/useTodoStore'



const TodoList = () => {
    const {todos, toggleTodo} = useTodoStore();
  return (
    <ListContainer>
        {todos.map(todo => (
            <TodoItem key={todo.id}>
                <CheckBox 
                    type='checkbox'
                    checked={todo.completed}
                    onChange={() => toggleTodo(todo.id)}
                />
                <TodoText completed={todo.completed}>{todo.text}</TodoText>
                <DeleteButton>삭제</DeleteButton>
            </TodoItem>
        ))}
    </ListContainer>
  )
}

export default TodoList