import styled from 'styled-components';
import { flexColumnCenter, buttonPrimary, buttonOutline } from '../../styles/mixins';

export const HomeContainer = styled.div`
  ${flexColumnCenter}
  min-height: calc(100vh - 300px);
  text-align: center;
`;

export const Hero = styled.div`
  margin-bottom: ${props => props.theme.spacing[12]};
`;

export const Title = styled.h1`
  font-size: ${props => props.theme.fonts.size['5xl']};
  font-weight: ${props => props.theme.fonts.weight.extrabold};
  background: linear-gradient(135deg, ${props => props.theme.colors.primary[600]}, ${props => props.theme.colors.primary[400]});
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: ${props => props.theme.spacing[4]};
  
  @media (max-width: ${props => props.theme.breakpoints.md}) {
    font-size: ${props => props.theme.fonts.size['4xl']};
  }
`;

export const Subtitle = styled.p`
  font-size: ${props => props.theme.fonts.size.xl};
  color: ${props => props.theme.colors.text.secondary};
  margin-bottom: ${props => props.theme.spacing[2]};
`;

export const Description = styled.p`
  font-size: ${props => props.theme.fonts.size.base};
  color: ${props => props.theme.colors.text.tertiary};
  max-width: 600px;
  line-height: ${props => props.theme.fonts.lineHeight.relaxed};
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[4]};
  justify-content: center;
  flex-wrap: wrap;
`;

export const PrimaryButton = styled.button`
  ${buttonPrimary}
  padding: ${props => props.theme.spacing[4]} ${props => props.theme.spacing[8]};
  font-size: ${props => props.theme.fonts.size.lg};
`;

export const SecondaryButton = styled.button`
  ${buttonOutline}
  padding: ${props => props.theme.spacing[4]} ${props => props.theme.spacing[8]};
  font-size: ${props => props.theme.fonts.size.lg};
`;

export const Features = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: ${props => props.theme.spacing[6]};
  width: 100%;
  max-width: 900px;
  margin-top: ${props => props.theme.spacing[16]};
`;

export const FeatureCard = styled.div`
  padding: ${props => props.theme.spacing[6]};
  background: ${props => props.theme.colors.white};
  border: 1px solid ${props => props.theme.colors.border.main};
  border-radius: ${props => props.theme.borderRadius.xl};
  transition: ${props => props.theme.transitions.base};
  
  &:hover {
    border-color: ${props => props.theme.colors.primary[300]};
    box-shadow: ${props => props.theme.shadows.lg};
    transform: translateY(-4px);
  }
`;

export const FeatureIcon = styled.div`
  font-size: ${props => props.theme.fonts.size['4xl']};
  margin-bottom: ${props => props.theme.spacing[4]};
`;

export const FeatureTitle = styled.h3`
  font-size: ${props => props.theme.fonts.size.xl};
  font-weight: ${props => props.theme.fonts.weight.semibold};
  color: ${props => props.theme.colors.text.primary};
  margin-bottom: ${props => props.theme.spacing[2]};
`;

export const FeatureDescription = styled.p`
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.tertiary};
  line-height: ${props => props.theme.fonts.lineHeight.relaxed};
`;

