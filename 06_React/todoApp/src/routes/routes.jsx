import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { ROUTES } from './routePaths'
import Layout from '../components/Layout/Layout'
import Home from '../pages/Home.Jsx'
import TodoListPage from '../pages/TodoListPage'
import CategoryPage from '../pages/CategoryPage'

const AppRoutes = () => {
  return (
    <BrowserRouter>
        <Routes>
            <Route path={ROUTES.HOME} element={<Layout />} >
                <Route index element={<Home />}/>
                <Route path='todos' element={<TodoListPage />} />
                <Route path='category/:category' element={<CategoryPage />} />
            </Route>
        </Routes>
    </BrowserRouter>
  )
}

export default AppRoutes;