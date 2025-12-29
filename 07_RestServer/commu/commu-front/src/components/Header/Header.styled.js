import styled from 'styled-components';
import { flexBetween, buttonGhost } from '../../styles/mixins';

export const HeaderContainer = styled.header`
  ${flexBetween}
  position: sticky;
  top: 0;
  width: 100%;
  height: 70px;
  padding: 0 ${props => props.theme.spacing[6]};
  background: ${props => props.theme.colors.white};
  border-bottom: 1px solid ${props => props.theme.colors.border.main};
  box-shadow: ${props => props.theme.shadows.sm};
  z-index: ${props => props.theme.zIndex.sticky};
`;

export const Logo = styled.div`
  display: flex;
  align-items: center;
  gap: ${props => props.theme.spacing[3]};
  cursor: pointer;
  
  h1 {
    font-size: ${props => props.theme.fonts.size['2xl']};
    font-weight: ${props => props.theme.fonts.weight.bold};
    background: linear-gradient(135deg, ${props => props.theme.colors.primary[600]}, ${props => props.theme.colors.primary[400]});
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }
`;

export const Nav = styled.nav`
  display: flex;
  align-items: center;
  gap: ${props => props.theme.spacing[2]};
`;

export const NavButton = styled.button`
  ${buttonGhost}
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
  font-size: ${props => props.theme.fonts.size.base};
`;

export const UserInfo = styled.div`
  display: flex;
  align-items: center;
  gap: ${props => props.theme.spacing[4]};
  padding-left: ${props => props.theme.spacing[4]};
  border-left: 1px solid ${props => props.theme.colors.border.main};
  
  span {
    color: ${props => props.theme.colors.text.secondary};
    font-size: ${props => props.theme.fonts.size.sm};
  }
  
  strong {
    color: ${props => props.theme.colors.primary[600]};
    font-weight: ${props => props.theme.fonts.weight.semibold};
  }
`;

