import React, { useState } from 'react'
import { useParams } from 'react-router-dom'
import { Container, EmptyMessage, FilterButton, FilterButtons, Title, TodoListContainer } from './CategoryPage.styled';
import TodoForm from '../components/Todo/TodoForm';
import { CATEGORY_NAMES, CATEGORYS, ROUTES } from '../routes/routePaths';
import { useTodos } from '../context/TodoContext';
import TodoItem from '../components/Todo/TodoItem';

const CategoryPage = () => {
    const { category } = useParams();
    const { addTodo, todos, toggleTodo, deleteTodo } = useTodos();

    const todoByCategory = todos.filter(todo => todo.category === category);
    

  return (
     <Container>
            <Title>{CATEGORY_NAMES[category] || '카테고리'}</Title>

            <FilterButtons>
                <FilterButton 
                    to={ROUTES.CATEGORY(CATEGORYS.WORK)}
                    active={category === CATEGORYS.WORK}
                > 업무</FilterButton>
                <FilterButton 
                    to={ROUTES.CATEGORY(CATEGORYS.STUDY)}
                    active={category === CATEGORYS.STUDY}
                >학습</FilterButton>
                <FilterButton 
                    to={ROUTES.CATEGORY(CATEGORYS.HEALTH)}
                    active={category === CATEGORYS.HEALTH}
                >건강</FilterButton>
            </FilterButtons>

            <TodoForm disableOption={{category : true}} onAdd={(text) => addTodo(text, category)}/>


            <TodoListContainer>
                { todoByCategory.length === 0 ? (
                    <EmptyMessage>할일이 없습니다...</EmptyMessage>
                ) : (
                    todoByCategory.map(todo => (
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

export default CategoryPage