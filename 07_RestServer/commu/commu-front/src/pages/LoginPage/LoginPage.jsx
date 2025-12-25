import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../store/authStore';
import { authService } from '../../api/services';
import * as S from './LoginPage.styled';

const LoginPage = () => {
  const navigate = useNavigate();
  const { login } = useAuthStore();
  const [formData, setFormData] = useState({
    user_id: '',
    user_pwd: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
    setError('');
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.user_id || !formData.user_pwd) {
      setError('아이디와 비밀번호를 입력해주세요.');
      return;
    }
    
    setLoading(true);
    try {
      const response = await authService.login(formData);
      login(response);
      navigate('/boards');
    } catch (err) {
      setError(err.message || '로그인에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <S.LoginContainer>
      <S.LoginBox>
        <S.Title>로그인</S.Title>
        
        <S.Form onSubmit={handleSubmit}>
          {error && <S.ErrorMessage>{error}</S.ErrorMessage>}
          
          <S.InputGroup>
            <S.Label htmlFor="user_id">아이디</S.Label>
            <S.Input
              type="text"
              id="user_id"
              name="user_id"
              value={formData.user_id}
              onChange={handleChange}
              placeholder="아이디를 입력하세요"
              autoComplete="username"
            />
          </S.InputGroup>
          
          <S.InputGroup>
            <S.Label htmlFor="user_pwd">비밀번호</S.Label>
            <S.Input
              type="password"
              id="user_pwd"
              name="user_pwd"
              value={formData.user_pwd}
              onChange={handleChange}
              placeholder="비밀번호를 입력하세요"
              autoComplete="current-password"
            />
          </S.InputGroup>
          
          <S.SubmitButton type="submit" disabled={loading}>
            {loading ? '로그인 중...' : '로그인'}
          </S.SubmitButton>
        </S.Form>
        
        <S.LinkText>
          계정이 없으신가요?{' '}
          <button onClick={() => navigate('/signup')}>회원가입</button>
        </S.LinkText>
      </S.LoginBox>
    </S.LoginContainer>
  );
};

export default LoginPage;

