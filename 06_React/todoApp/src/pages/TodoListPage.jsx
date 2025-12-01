import React from 'react'
import { Container } from './TodoListPage.styled'
import TodoForm from '../components/Todo/TodoForm'
import { useTodos } from '../context/TodoContext'
import { EmptyMessage, TodoListContainer } from '../components/Todo/TodoForm.styled'
import TodoItem from '../components/Todo/TodoItem'

const TodoListPage = () => {
    const {todos, addTodo} = useTodos();
    return (
        <Container>
            <TodoForm onAdd = {addTodo}/>

            <TodoListContainer>
                { todos.length === 0 ? (
                    <EmptyMessage>할일이 없습니다...</EmptyMessage>
                ) : (
                    todos.map(todo => (
                        <TodoItem
                            key={todo.id}
                            todo={todo}
                        />
                    ))
                )}
            </TodoListContainer>
        </Container>
    )
}

export default TodoListPage