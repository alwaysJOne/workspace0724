import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../store/authStore';
import * as S from './Header.styled';

const Header = () => {
  const navigate = useNavigate();
  const { isAuthenticated, user, logout } = useAuthStore();
  
  const handleLogout = () => {
    logout();
    navigate('/login');
  };
  
  return (
    <S.HeaderContainer>
      <S.Logo onClick={() => navigate('/')}>
        <h1>DevCommu</h1>
      </S.Logo>
      
      <S.Nav>
        {isAuthenticated ? (
          <>
            <S.NavButton onClick={() => navigate('/boards')}>게시판</S.NavButton>
            <S.NavButton onClick={() => navigate('/members')}>멤버</S.NavButton>
            <S.UserInfo>
              <span>
                <strong>{user?.user_name}</strong>님
              </span>
              <S.NavButton onClick={handleLogout}>로그아웃</S.NavButton>
            </S.UserInfo>
          </>
        ) : (
          <>
            <S.NavButton onClick={() => navigate('/login')}>로그인</S.NavButton>
            <S.NavButton onClick={() => navigate('/signup')}>회원가입</S.NavButton>
          </>
        )}
      </S.Nav>
    </S.HeaderContainer>
  );
};

export default Header;

