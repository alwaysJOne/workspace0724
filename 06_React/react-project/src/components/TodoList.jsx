import React from 'react'
import { CheckBox, DeleteButton, FilterContainer, ListContainer, NormalButton, TodoItem, TodoText } from './TodoList.styled'
import useTodoStore from '../store/useTodoStore'



const TodoList = () => {
    const {getFilteredTodos, setFilter, toggleTodo, deleteTodo} = useTodoStore();
  
    const todos = getFilteredTodos();
    return (
    <ListContainer>
        <FilterContainer>
            <NormalButton
                onClick={() => setFilter('all')}
            >
                전체
            </NormalButton>
            <NormalButton
                onClick={() => setFilter('active')}
            >
                진행중
            </NormalButton>
            <NormalButton
                onClick={() => setFilter('completed')}
            >
                완료
            </NormalButton>
        </FilterContainer>
        {todos.map(todo => (
            <TodoItem key={todo.id}>
                <CheckBox 
                    type='checkbox'
                    checked={todo.completed}
                    onChange={() => toggleTodo(todo.id)}
                />
                <TodoText completed={todo.completed}>{todo.text}</TodoText>
                <DeleteButton onClick={()=> deleteTodo(todo.id)}>삭제</DeleteButton>
            </TodoItem>
        ))}
    </ListContainer>
  )
}

export default TodoList