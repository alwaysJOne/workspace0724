import React, { createContext, useContext, useState } from 'react'

const TodoContext = createContext();

export const useTodos = () =>{
    const context = useContext(TodoContext);
    return context;
}

export const TodoProvider = ({children}) => {
    const [todos, setTodos] = useState([]);

    const addTodo = (text, category) =>{
        const newTodo ={
            id: Date.now(),
            text,
            category,
            createdAt : new Date().toISOString(),
            completed : false,
        }

        setTodos(prev => [...prev, newTodo]);
        console.log(todos)
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

    const value = {
        todos,
        addTodo,
        deleteTodo,
        updateTodo,
        toggleTodo,
    }
    return (
        <TodoContext.Provider value={value}>{children}</TodoContext.Provider>
    )
}