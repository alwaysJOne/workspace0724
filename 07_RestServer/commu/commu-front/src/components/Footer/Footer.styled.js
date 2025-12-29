import styled from 'styled-components';
import { flexCenter } from '../../styles/mixins';

export const FooterContainer = styled.footer`
  ${flexCenter}
  width: 100%;
  padding: ${props => props.theme.spacing[8]} ${props => props.theme.spacing[6]};
  margin-top: auto;
  background: ${props => props.theme.colors.gray[50]};
  border-top: 1px solid ${props => props.theme.colors.border.main};
`;

export const FooterContent = styled.div`
  text-align: center;
  color: ${props => props.theme.colors.text.tertiary};
  font-size: ${props => props.theme.fonts.size.sm};
  
  p {
    margin-bottom: ${props => props.theme.spacing[1]};
  }
`;

