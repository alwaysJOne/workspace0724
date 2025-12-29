import styled from 'styled-components';
import { flexColumn } from '../../styles/mixins';

export const LayoutContainer = styled.div`
  ${flexColumn}
  min-height: 100vh;
`;

export const Main = styled.main`
  flex: 1;
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: ${props => props.theme.spacing[8]} ${props => props.theme.spacing[6]};
  
  @media (max-width: ${props => props.theme.breakpoints.md}) {
    padding: ${props => props.theme.spacing[6]} ${props => props.theme.spacing[4]};
  }
`;

