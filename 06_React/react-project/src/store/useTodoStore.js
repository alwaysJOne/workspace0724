import { create } from "zustand";

const useTodoStore = create((set, get) => ({
    todos: [
        {
            id: 1,
            text: "밥먹기",
            completed: false,
        },  {
            id: 2,
            text: "잠자기",
            completed: true,
        },  {
            id: 3,
            text: "숨쉬기",
            completed: false,
        }
    ],
    filter: 'all', //all, active, completed
    setFilter: (filter) => set({filter}),
    toggleTodo: (id) => set(state => ({
        todos: state.todos.map(todo => 
            todo.id === id ? {...todo, completed: !todo.completed} : todo
        )
    })),
    deleteTodo: (id) => set(state => ({
        todos: state.todos.filter(todo => todo.id !== id)
    })),
    getFilteredTodos: () => {
        const {todos, filter} = get();

        return todos.filter(todo => {
            switch(filter){
                case "active":
                    return !todo.completed;
                case "completed":
                    return todo.completed;  
                default:
                    return true;  
            }
        });
    }
}))


export default useTodoStore;