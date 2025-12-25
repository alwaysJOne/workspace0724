import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { memberService } from '../../api/services';
import * as S from './SignupPage.styled';

const SignupPage = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    user_id: '',
    user_pwd: '',
    user_name: '',
    email: '',
    gender: '',
    age: '',
    phone: '',
    address: '',
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
    
    // 필수 필드 검증
    if (!formData.user_id || !formData.user_pwd || !formData.user_name) {
      setError('필수 항목을 모두 입력해주세요.');
      return;
    }
    
    // 아이디 검증
    if (formData.user_id.length < 4 || formData.user_id.length > 30) {
      setError('아이디는 4-30자 사이여야 합니다.');
      return;
    }
    
    // 비밀번호 검증
    if (formData.user_pwd.length < 8 || formData.user_pwd.length > 100) {
      setError('비밀번호는 8-100자 사이여야 합니다.');
      return;
    }
    
    // 이름 검증
    if (formData.user_name.length < 2 || formData.user_name.length > 15) {
      setError('이름은 2-15자 사이여야 합니다.');
      return;
    }
    
    setLoading(true);
    try {
      // 빈 문자열을 null로 변환
      const submitData = {
        ...formData,
        age: formData.age ? parseInt(formData.age) : undefined,
        gender: formData.gender || undefined,
        email: formData.email || undefined,
        phone: formData.phone || undefined,
        address: formData.address || undefined,
      };
      
      await memberService.register(submitData);
      alert('회원가입이 완료되었습니다.');
      navigate('/login');
    } catch (err) {
      setError(err.message || '회원가입에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <S.SignupContainer>
      <S.SignupBox>
        <S.Title>회원가입</S.Title>
        
        <S.Form onSubmit={handleSubmit}>
          {error && <S.ErrorMessage>{error}</S.ErrorMessage>}
          
          <S.InputGroup>
            <S.Label htmlFor="user_id">
              아이디<span>*</span>
            </S.Label>
            <S.Input
              type="text"
              id="user_id"
              name="user_id"
              value={formData.user_id}
              onChange={handleChange}
              placeholder="4-30자의 영문, 숫자"
              autoComplete="username"
            />
          </S.InputGroup>
          
          <S.InputGroup>
            <S.Label htmlFor="user_pwd">
              비밀번호<span>*</span>
            </S.Label>
            <S.Input
              type="password"
              id="user_pwd"
              name="user_pwd"
              value={formData.user_pwd}
              onChange={handleChange}
              placeholder="8-100자"
              autoComplete="new-password"
            />
          </S.InputGroup>
          
          <S.InputGroup>
            <S.Label htmlFor="user_name">
              이름<span>*</span>
            </S.Label>
            <S.Input
              type="text"
              id="user_name"
              name="user_name"
              value={formData.user_name}
              onChange={handleChange}
              placeholder="2-15자"
            />
          </S.InputGroup>
          
          <S.InputGroup>
            <S.Label htmlFor="email">이메일</S.Label>
            <S.Input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="example@email.com"
              autoComplete="email"
            />
          </S.InputGroup>
          
          <S.Row>
            <S.InputGroup>
              <S.Label htmlFor="gender">성별</S.Label>
              <S.Select
                id="gender"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
              >
                <option value="">선택</option>
                <option value="M">남성</option>
                <option value="F">여성</option>
              </S.Select>
            </S.InputGroup>
            
            <S.InputGroup>
              <S.Label htmlFor="age">나이</S.Label>
              <S.Input
                type="number"
                id="age"
                name="age"
                value={formData.age}
                onChange={handleChange}
                placeholder="나이"
                min="0"
                max="150"
              />
            </S.InputGroup>
          </S.Row>
          
          <S.InputGroup>
            <S.Label htmlFor="phone">전화번호</S.Label>
            <S.Input
              type="tel"
              id="phone"
              name="phone"
              value={formData.phone}
              onChange={handleChange}
              placeholder="010-1234-5678"
              autoComplete="tel"
            />
          </S.InputGroup>
          
          <S.InputGroup>
            <S.Label htmlFor="address">주소</S.Label>
            <S.Input
              type="text"
              id="address"
              name="address"
              value={formData.address}
              onChange={handleChange}
              placeholder="주소"
            />
          </S.InputGroup>
          
          <S.SubmitButton type="submit" disabled={loading}>
            {loading ? '가입 중...' : '회원가입'}
          </S.SubmitButton>
        </S.Form>
        
        <S.LinkText>
          이미 계정이 있으신가요?{' '}
          <button onClick={() => navigate('/login')}>로그인</button>
        </S.LinkText>
      </S.SignupBox>
    </S.SignupContainer>
  );
};

export default SignupPage;

