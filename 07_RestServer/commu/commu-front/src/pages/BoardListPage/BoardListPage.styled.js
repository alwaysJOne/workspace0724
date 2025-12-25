import styled from 'styled-components';
import { flexBetween, buttonPrimary, cardHover } from '../../styles/mixins';

export const BoardContainer = styled.div`
  width: 100%;
  max-width: 1000px;
  margin: 0 auto;
`;

export const Header = styled.div`
  ${flexBetween}
  margin-bottom: ${props => props.theme.spacing[8]};
  
  @media (max-width: ${props => props.theme.breakpoints.sm}) {
    flex-direction: column;
    gap: ${props => props.theme.spacing[4]};
    align-items: flex-start;
  }
`;

export const Title = styled.h1`
  font-size: ${props => props.theme.fonts.size['3xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.text.primary};
`;

export const WriteButton = styled.button`
  ${buttonPrimary}
`;

export const BoardList = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[4]};
`;

export const BoardCard = styled.div`
  ${cardHover}
  display: flex;
  flex-direction: column;
  gap: ${props => props.theme.spacing[3]};
`;

export const BoardHeader = styled.div`
  ${flexBetween}
  gap: ${props => props.theme.spacing[4]};
`;

export const BoardTitle = styled.h3`
  font-size: ${props => props.theme.fonts.size.xl};
  font-weight: ${props => props.theme.fonts.weight.semibold};
  color: ${props => props.theme.colors.text.primary};
  flex: 1;
`;

export const BoardMeta = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[4]};
  font-size: ${props => props.theme.fonts.size.sm};
  color: ${props => props.theme.colors.text.tertiary};
  
  span {
    display: flex;
    align-items: center;
    gap: ${props => props.theme.spacing[1]};
  }
`;

export const BoardContent = styled.p`
  font-size: ${props => props.theme.fonts.size.base};
  color: ${props => props.theme.colors.text.secondary};
  line-height: ${props => props.theme.fonts.lineHeight.relaxed};
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

export const TagList = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: ${props => props.theme.spacing[2]};
`;

export const Tag = styled.span`
  padding: ${props => props.theme.spacing[1]} ${props => props.theme.spacing[3]};
  background: ${props => props.theme.colors.primary[50]};
  color: ${props => props.theme.colors.primary[700]};
  border-radius: ${props => props.theme.borderRadius.full};
  font-size: ${props => props.theme.fonts.size.xs};
  font-weight: ${props => props.theme.fonts.weight.medium};
`;

export const Pagination = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: ${props => props.theme.spacing[2]};
  margin-top: ${props => props.theme.spacing[8]};
`;

export const PageButton = styled.button`
  padding: ${props => props.theme.spacing[2]} ${props => props.theme.spacing[4]};
  background: ${props => props.active ? props.theme.colors.primary[500] : props.theme.colors.white};
  color: ${props => props.active ? props.theme.colors.white : props.theme.colors.text.primary};
  border: 1px solid ${props => props.active ? props.theme.colors.primary[500] : props.theme.colors.border.main};
  border-radius: ${props => props.theme.borderRadius.lg};
  font-weight: ${props => props.theme.fonts.weight.medium};
  cursor: pointer;
  transition: ${props => props.theme.transitions.fast};
  
  &:hover:not(:disabled) {
    background: ${props => props.active ? props.theme.colors.primary[600] : props.theme.colors.gray[100]};
  }
  
  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;

export const EmptyState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[16]} ${props => props.theme.spacing[6]};
  color: ${props => props.theme.colors.text.tertiary};
  
  p {
    font-size: ${props => props.theme.fonts.size.lg};
    margin-bottom: ${props => props.theme.spacing[4]};
  }
`;

export const LoadingState = styled.div`
  text-align: center;
  padding: ${props => props.theme.spacing[16]};
  color: ${props => props.theme.colors.text.tertiary};
  font-size: ${props => props.theme.fonts.size.lg};
`;

