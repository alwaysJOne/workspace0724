import { useNavigate } from 'react-router-dom';
import useAuthStore from '../../store/authStore';
import * as S from './HomePage.styled';

const HomePage = () => {
  const navigate = useNavigate();
  const { isAuthenticated } = useAuthStore();
  
  const handleGetStarted = () => {
    if (isAuthenticated) {
      navigate('/boards');
    } else {
      navigate('/signup');
    }
  };
  
  return (
    <S.HomeContainer>
      <S.Hero>
        <S.Title>DevCommu</S.Title>
        <S.Subtitle>개발자를 위한 커뮤니티</S.Subtitle>
        <S.Description>
          함께 성장하고, 지식을 나누며, 더 나은 개발 문화를 만들어가는 곳
        </S.Description>
      </S.Hero>
      
      <S.ButtonGroup>
        <S.PrimaryButton onClick={handleGetStarted}>
          시작하기
        </S.PrimaryButton>
        {!isAuthenticated && (
          <S.SecondaryButton onClick={() => navigate('/login')}>
            로그인
          </S.SecondaryButton>
        )}
      </S.ButtonGroup>
      
      <S.Features>
        <S.FeatureCard>
          <S.FeatureIcon>💬</S.FeatureIcon>
          <S.FeatureTitle>자유로운 소통</S.FeatureTitle>
          <S.FeatureDescription>
            개발 관련 질문과 답변, 경험을 자유롭게 공유하세요
          </S.FeatureDescription>
        </S.FeatureCard>
        
        <S.FeatureCard>
          <S.FeatureIcon>📚</S.FeatureIcon>
          <S.FeatureTitle>지식 공유</S.FeatureTitle>
          <S.FeatureDescription>
            유용한 개발 팁과 노하우를 커뮤니티와 함께 나누세요
          </S.FeatureDescription>
        </S.FeatureCard>
        
        <S.FeatureCard>
          <S.FeatureIcon>🚀</S.FeatureIcon>
          <S.FeatureTitle>함께 성장</S.FeatureTitle>
          <S.FeatureDescription>
            동료 개발자들과 함께 성장하는 즐거움을 경험하세요
          </S.FeatureDescription>
        </S.FeatureCard>
      </S.Features>
    </S.HomeContainer>
  );
};

export default HomePage;

