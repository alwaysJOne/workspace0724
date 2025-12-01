import React, { useState } from 'react'
import { Container, FilterButton, FilterButtons } from './TodoListPage.styled'
import TodoForm from '../components/Todo/TodoForm'
import { useTodos } from '../context/TodoContext'
import { EmptyMessage, TodoListContainer } from '../components/Todo/TodoForm.styled'
import TodoItem from '../components/Todo/TodoItem'

const TodoListPage = () => {
    const {todos, addTodo, toggleTodo, deleteTodo} = useTodos();
    const [filter, setFilter] = useState('all');

    const filteredTodos = todos.filter(todo => {
        if(filter === 'completed') return todo.completed;
        else if(filter === 'pending') return !todo.completed;
        return true;
    })
    return (
        <Container>
            <TodoForm onAdd = {addTodo}/>

            <FilterButtons>
                <FilterButton 
                    active={filter === 'all'}
                    onClick={() => setFilter('all')}
                >전체
                </FilterButton>
                <FilterButton 
                    active={filter === 'completed'}
                    onClick={() => setFilter('completed')}
                >완료</FilterButton>
                <FilterButton 
                    active={filter === 'pending'}
                    onClick={() => setFilter('pending')}
                >미완료</FilterButton>
            </FilterButtons>


            <TodoListContainer>
                { filteredTodos.length === 0 ? (
                    <EmptyMessage>할일이 없습니다...</EmptyMessage>
                ) : (
                    filteredTodos.map(todo => (
                        <TodoItem
                            key={todo.id}
                            todo={todo}
                            onToggle={toggleTodo}
                            onDelete={deleteTodo}
                        />
                    ))
                )}
            </TodoListContainer>
        </Container>
    )
}

export default TodoListPage