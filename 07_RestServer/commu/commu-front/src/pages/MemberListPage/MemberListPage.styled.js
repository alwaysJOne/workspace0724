import styled from 'styled-components';
import { inputBase, card } from '../../styles/mixins';

export const MemberContainer = styled.div`
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
`;

export const Title = styled.h1`
  font-size: ${props => props.theme.fonts.size['3xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.text.primary};
  margin-bottom: ${props => props.theme.spacing[6]};
`;

export const SearchBox = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[3]};
  margin-bottom: ${props => props.theme.spacing[8]};
  
  @media (max-width: ${props => props.theme.breakpoints.sm}) {
    flex-direction: column;
  }
`;

export const SearchInput = styled.input`
  ${inputBase}
  flex: 1;
`;

export const SearchButton = styled.button`
  padding: ${props => props.theme.spacing[3]} ${props => props.theme.spacing[6]};
  background: ${props => props.theme.colors.primary[500]};
  color: ${props => props.theme.colors.white};
  border: none;
  border-radius: ${props => props.theme.borderRadius.lg};
  font-weight: ${props => props.theme.fonts.weight.medium};
  cursor: pointer;
  transition: ${props => props.theme.transitions.fast};
  
  &:hover {
    background: ${props => props.theme.colors.primary[600]};
  }
`;

export const MemberGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: ${props => props.theme.spacing[6]};
`;

export const MemberCard = styled.div`
  ${card}
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[3]};
  transition: ${props => props.theme.transitions.base};
  
  &:hover {
    box-shadow: ${props => props.theme.shadows.lg};
    transform: translateY(-2px);
  }
`;

export const MemberHeader = styled.div`
  display: flex;
  align-items: center;
  gap: ${props => props.theme.spacing[3]};
  padding-bottom: ${props => props.theme.spacing[3]};
  border-bottom: 1px solid ${props => props.theme.colors.border.light};
`;

export const Avatar = styled.div`
  width: 50px;
  height: 50px;
  border-radius: ${props => props.theme.borderRadius.full};
  background: linear-gradient(135deg, ${props => props.theme.colors.primary[400]}, ${props => props.theme.colors.primary[600]});
  display: flex;
  align-items: center;
  justify-content: center;
  color: ${props => props.theme.colors.white};
  font-size: ${props => props.theme.fonts.size.xl};
  font-weight: ${props => props.theme.fonts.weight.bold};
`;

export const MemberInfo = styled.div`
  flex: 1;
`;

export const MemberName = styled.h3`
  font-size: ${props => props.theme.fonts.size.lg};
  font-weight: ${props => props.theme.fonts.weight.semibold};
  color: ${props => props.theme.colors.text.primary};
  margin-bottom: ${props => props.theme.spacing[1]};
`;

export const MemberId = styled.p`
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.tertiary};
`;

export const MemberDetails = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[2]};
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.secondary};
`;

export const DetailRow = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[2]};
  
  strong {
    min-width: 60px;
    color: ${props => props.theme.colors.text.primary};
    font-weight: ${props => props.theme.fonts.weight.medium};
  }
`;

export const LoadingState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[16]};
  color: ${props => props.theme.colors.text.tertiary};
  font-size: ${props => props.theme.fonts.size.lg};
`;

export const EmptyState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[16]};
  color: ${props => props.theme.colors.text.tertiary};
  
  p {
    font-size: ${props => props.theme.fonts.size.lg};
  }
`;

