import React, { createContext, useContext, useEffect, useState } from 'react'

const TodoContext = createContext();

export const useTodos = () =>{
    const context = useContext(TodoContext);
    return context;
}

export const TodoProvider = ({children}) => {
    const [todos, setTodos] = useState(() => {
        const savedTodos = localStorage.getItem('todos');
        return savedTodos ? JSON.parse(savedTodos) : [];
    });

    useEffect(() => {
        localStorage.setItem('todos', JSON.stringify(todos));
    },[todos])

    const addTodo = (text, category) =>{
        const newTodo ={
            id: Date.now(),
            text,
            category,
            createdAt : new Date().toISOString(),
            completed : false,
        }

        setTodos(prev => [...prev, newTodo]);
        
        return newTodo;
    }

    const deleteTodo = (id) => {
        setTodos(prev => prev.filter(todo => todo.id !== id));
    }

    const updateTodo = (id, updateTodo) =>{
        setTodos(prev =>
            prev.map(todo => 
                todo.id === id ? {...todo, ...updateTodo} : todo
            )
        )
    }

    const toggleTodo = (id) => {
        setTodos(prev => 
            prev.map(todo => 
                todo.id === id ? {...todo, completed: !todo.completed} : todo
            )
        )
    }

    const getState = () => {
        const total = todos.length;
        const completed = todos.filter(todo => todo.completed).length;
        const pending = total - completed;

        const work = todos.filter(todo => todo.category === 'work').length
        const health = todos.filter(todo => todo.category === 'health').length
        const study = todos.filter(todo => todo.category === 'study').length

        const byCategory = {
            work, health, study
        }

        return {total, completed, pending, byCategory}
    }

    const value = {
        todos,
        addTodo,
        deleteTodo,
        updateTodo,
        toggleTodo,
        getState,
    }
    return (
        <TodoContext.Provider value={value}>{children}</TodoContext.Provider>
    )
}