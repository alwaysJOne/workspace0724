import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import theme from './styles/theme';
import GlobalStyle from './styles/GlobalStyle';
import Layout from './components/Layout';
import ProtectedRoute from './components/ProtectedRoute';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import SignupPage from './pages/SignupPage';
import BoardListPage from './pages/BoardListPage';
import BoardDetailPage from './pages/BoardDetailPage';
import BoardWritePage from './pages/BoardWritePage';
import MemberListPage from './pages/MemberListPage';

function App() {
  return (
    <ThemeProvider theme={theme}>
      <GlobalStyle />
      <BrowserRouter>
        <Layout>
          <Routes>
            {/* 공개 라우트 */}
            <Route path="/" element={<HomePage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignupPage />} />
            
            {/* 보호된 라우트 */}
            <Route
              path="/boards"
              element={
                <ProtectedRoute>
                  <BoardListPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/boards/:boardId"
              element={
                <ProtectedRoute>
                  <BoardDetailPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/boards/write"
              element={
                <ProtectedRoute>
                  <BoardWritePage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/boards/edit/:boardId"
              element={
                <ProtectedRoute>
                  <BoardWritePage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/members"
              element={
                <ProtectedRoute>
                  <MemberListPage />
                </ProtectedRoute>
              }
            />
            
            {/* 404 페이지 */}
            <Route path="*" element={<Navigate to="/" replace />} />
          </Routes>
        </Layout>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
