import styled from 'styled-components';
import { flexBetween, buttonPrimary, buttonSecondary, buttonOutline } from '../../styles/mixins';

export const DetailContainer = styled.div`
  width: 100%;
  max-width: 800px;
  margin: 0 auto;
`;

export const Header = styled.div`
  ${flexBetween}
  margin-bottom: ${props => props.theme.spacing[6]};
  
  @media (max-width: ${props => props.theme.breakpoints.sm}) {
    flex-direction: column;
    gap: ${props => props.theme.spacing[3]};
    align-items: flex-start;
  }
`;

export const BackButton = styled.button`
  ${buttonSecondary}
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
`;

export const ButtonGroup = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[2]};
`;

export const EditButton = styled.button`
  ${buttonOutline}
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
`;

export const DeleteButton = styled.button`
  ${buttonOutline}
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
  color: ${props => props.theme.colors.danger.main};
  border-color: ${props => props.theme.colors.danger.main};
  
  &:hover:not(:disabled) {
    background: ${props => props.theme.colors.danger.light};
  }
`;

export const Article = styled.article`
  background: ${props => props.theme.colors.white};
  border: 1px solid ${props => props.theme.colors.border.main};
  border-radius: ${props => props.theme.borderRadius.xl};
  padding: ${props => props.theme.spacing[8]};
  box-shadow: ${props => props.theme.shadows.sm};
`;

export const ArticleHeader = styled.div`
  padding-bottom: ${props => props.theme.spacing[6]};
  border-bottom: 1px solid ${props => props.theme.colors.border.main};
  margin-bottom: ${props => props.theme.spacing[6]};
`;

export const Title = styled.h1`
  font-size: ${props => props.theme.fonts.size['3xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.text.primary};
  margin-bottom: ${props => props.theme.spacing[4]};
  line-height: ${props => props.theme.fonts.lineHeight.tight};
`;

export const Meta = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: ${props => props.theme.spacing[4]};
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.tertiary};
  
  span {
    display: flex;
    align-items: center;
    gap: ${props => props.theme.spacing[1]};
  }
`;

export const TagList = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: ${props => props.theme.spacing[2]};
  margin-top: ${props => props.theme.spacing[3]};
`;

export const Tag = styled.span`
  padding: ${props => props.theme.spacing[1]} ${props => props.theme.spacing[3]};
  background: ${props => props.theme.colors.primary[50]};
  color: ${props => props.theme.colors.primary[700]};
  border-radius: ${props => props.theme.borderRadius.full};
  font-size: ${props => props.theme.fonts.size.xs};
  font-weight: ${props => props.theme.fonts.weight.medium};
`;

export const Content = styled.div`
  font-size: ${props => props.theme.fonts.size.base};
  color: ${props => props.theme.colors.text.primary};
  line-height: ${props => props.theme.fonts.lineHeight.relaxed};
  white-space: pre-wrap;
  word-break: break-word;
`;

export const FileSection = styled.div`
  margin-top: ${props => props.theme.spacing[6]};
  padding-top: ${props => props.theme.spacing[6]};
  border-top: 1px solid ${props => props.theme.colors.border.main};
`;

export const FileLabel = styled.div`
  font-size: ${props => props.theme.fonts.size.sm};
  font-weight: ${props => props.theme.fonts.weight.medium};
  color: ${props => props.theme.colors.text.secondary};
  margin-bottom: ${props => props.theme.spacing[2]};
`;

export const FileLink = styled.a`
  display: inline-flex;
  align-items: center;
  gap: ${props => props.theme.spacing[2]};
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
  background: ${props => props.theme.colors.gray[100]};
  border-radius: ${props => props.theme.borderRadius.lg};
  color: ${props => props.theme.colors.primary[600]};
  font-size: ${props => props.theme.fonts.size.sm};
  text-decoration: none;
  transition: ${props => props.theme.transitions.fast};
  
  &:hover {
    background: ${props => props.theme.colors.gray[200]};
  }
`;

export const LoadingState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[16]};
  color: ${props => props.theme.colors.text.tertiary};
  font-size: ${props => props.theme.fonts.size.lg};
`;

export const ErrorState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[16]};
  color: ${props => props.theme.colors.danger.main};
  
  p {
    font-size: ${props => props.theme.fonts.size.lg};
    margin-bottom: ${props => props.theme.spacing[4]};
  }
`;

