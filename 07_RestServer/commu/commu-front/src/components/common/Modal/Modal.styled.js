import styled from 'styled-components';
import { flexCenter, fadeIn } from '../../../styles/mixins';

export const Overlay = styled.div`
  ${flexCenter}
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: ${props => props.theme.zIndex.modalBackdrop};
  ${fadeIn}
`;

export const ModalContainer = styled.div`
  background: ${props => props.theme.colors.white};
  border-radius: ${props => props.theme.borderRadius['2xl']};
  padding: ${props => props.theme.spacing[8]};
  max-width: 500px;
  width: 90%;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: ${props => props.theme.shadows['2xl']};
  z-index: ${props => props.theme.zIndex.modal};
  
  @keyframes slideIn {
    from {
      opacity: 0;
      transform: scale(0.95) translateY(-20px);
    }
    to {
      opacity: 1;
      transform: scale(1) translateY(0);
    }
  }
  animation: slideIn ${props => props.theme.transitions.base};
`;

export const ModalHeader = styled.div`
  margin-bottom: ${props => props.theme.spacing[6]};
`;

export const ModalTitle = styled.h2`
  font-size: ${props => props.theme.fonts.size['2xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.text.primary};
`;

export const ModalBody = styled.div`
  margin-bottom: ${props => props.theme.spacing[6]};
  color: ${props => props.theme.colors.text.secondary};
  line-height: ${props => props.theme.fonts.lineHeight.relaxed};
`;

export const ModalFooter = styled.div`
  display: flex;
  gap: ${props => props.theme.spacing[3]};
  justify-content: flex-end;
`;

