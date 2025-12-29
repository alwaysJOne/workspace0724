import styled from 'styled-components';
import { flexBetween, inputBase, buttonPrimary, buttonSecondary } from '../../styles/mixins';

export const WriteContainer = styled.div`
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
`;

export const Header = styled.div`
  ${flexBetween}
  margin-bottom: ${props => props.theme.spacing[6]};
`;

export const Title = styled.h1`
  font-size: ${props => props.theme.fonts.size['3xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.text.primary};
`;

export const BackButton = styled.button`
  ${buttonSecondary}
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
`;

export const Form = styled.form`
  background: ${props => props.theme.colors.white};
  border: 1px solid ${props => props.theme.colors.border.main};
  border-radius: ${props => props.theme.borderRadius.xl};
  padding: ${props => props.theme.spacing[8]};
  box-shadow: ${props => props.theme.shadows.sm};
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[6]};
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
  
  span {
    color: ${props => props.theme.colors.danger.main};
    margin-left: ${props => props.theme.spacing[1]};
  }
`;

export const Input = styled.input`
  ${inputBase}
`;

export const Textarea = styled.textarea`
  ${inputBase}
  min-height: 300px;
  resize: vertical;
  font-family: inherit;
`;

export const FileInput = styled.input`
  display: none;
`;

export const FileButton = styled.label`
  ${buttonSecondary}
  display: inline-flex;
  align-items: center;
  gap: ${props => props.theme.spacing[2]};
  padding: ${props => props.theme.spacing[3]} ${props => props.theme.spacing[4]};
  cursor: pointer;
`;

export const FileName = styled.span`
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.secondary};
  margin-left: ${props => props.theme.spacing[3]};
`;

export const TagInput = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: ${props => props.theme.spacing[2]};
  align-items: center;
`;

export const TagItem = styled.span`
  display: inline-flex;
  align-items: center;
  gap: ${props => props.theme.spacing[2]};
  padding: ${props => props.theme.spacing[1]} ${props => props.theme.spacing[3]};
  background: ${props => props.theme.colors.primary[50]};
  color: ${props => props.theme.colors.primary[700]};
  border-radius: ${props => props.theme.borderRadius.full};
  font-size: ${props => props.theme.fonts.size.sm};
  
  button {
    display: flex;
    align-items: center;
    background: none;
    border: none;
    color: ${props => props.theme.colors.primary[600]};
    cursor: pointer;
    padding: 0;
    font-size: ${props => props.theme.fonts.size.lg};
    
    &:hover {
      color: ${props => props.theme.colors.primary[800]};
    }
  }
`;

export const TagInputField = styled.input`
  ${inputBase}
  flex: 1;
  min-width: 200px;
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[3]};
  justify-content: flex-end;
  padding-top: ${props => props.theme.spacing[4]};
  border-top: 1px solid ${props => props.theme.colors.border.main};
`;

export const SubmitButton = styled.button`
  ${buttonPrimary}
  padding: ${props => props.theme.spacing[3]} ${props => props.theme.spacing[8]};
`;

export const CancelButton = styled.button`
  ${buttonSecondary}
  padding: ${props => props.theme.spacing[3]} ${props => props.theme.spacing[8]};
`;

export const ErrorMessage = styled.div`
  padding: ${props => props.theme.spacing[3]};
  background: ${props => props.theme.colors.danger.light};
  color: ${props => props.theme.colors.danger.dark};
  border-radius: ${props => props.theme.borderRadius.lg};
  font-size: ${props => props.theme.fonts.size.sm};
`;

export const LoadingState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[12]};
  color: ${props => props.theme.colors.text.secondary};
  font-size: ${props => props.theme.fonts.size.lg};
`;

