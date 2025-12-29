import styled from 'styled-components';
import { flexColumnCenter, inputBase, buttonPrimary } from '../../styles/mixins';

export const LoginContainer = styled.div`
  ${flexColumnCenter}
  min-height: calc(100vh - 300px);
`;

export const LoginBox = styled.div`
  width: 100%;
  max-width: 400px;
  padding: ${props => props.theme.spacing[10]};
  background: ${props => props.theme.colors.white};
  border: 1px solid ${props => props.theme.colors.border.main};
  border-radius: ${props => props.theme.borderRadius['2xl']};
  box-shadow: ${props => props.theme.shadows.lg};
`;

export const Title = styled.h2`
  font-size: ${props => props.theme.fonts.size['3xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.text.primary};
  text-align: center;
  margin-bottom: ${props => props.theme.spacing[8]};
  
  background: linear-gradient(135deg, ${props => props.theme.colors.primary[600]}, ${props => props.theme.colors.primary[400]});
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[5]};
`;

export const InputGroup = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[2]};
`;

export const Label = styled.label`
  font-size: ${props => props.theme.fonts.size.sm};
  font-weight: ${props => props.theme.fonts.weight.medium};
  color: ${props => props.theme.colors.text.primary};
`;

export const Input = styled.input`
  ${inputBase}
`;

export const SubmitButton = styled.button`
  ${buttonPrimary}
  width: 100%;
  padding: ${props => props.theme.spacing[4]};
  font-size: ${props => props.theme.fonts.size.lg};
  margin-top: ${props => props.theme.spacing[2]};
`;

export const LinkText = styled.div`
  text-align: center;
  margin-top: ${props => props.theme.spacing[4]};
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.secondary};
  
  button {
    color: ${props => props.theme.colors.primary[600]};
    background: none;
    border: none;
    padding: 0;
    font-weight: ${props => props.theme.fonts.weight.medium};
    cursor: pointer;
    text-decoration: underline;
    
    &:hover {
      color: ${props => props.theme.colors.primary[700]};
    }
  }
`;

export const ErrorMessage = styled.div`
  padding: ${props => props.theme.spacing[3]};
  background: ${props => props.theme.colors.danger.light};
  color: ${props => props.theme.colors.danger.dark};
  border-radius: ${props => props.theme.borderRadius.lg};
  font-size: ${props => props.theme.fonts.size.sm};
  text-align: center;
`;

