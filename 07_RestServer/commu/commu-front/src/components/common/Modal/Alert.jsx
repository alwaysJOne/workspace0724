import { buttonPrimary } from '../../../styles/mixins';
import * as S from './Modal.styled';
import styled from 'styled-components';

const AlertButton = styled.button`
  ${buttonPrimary}
  min-width: 100px;
`;

const Alert = ({ isOpen, onClose, title = '알림', message }) => {
  if (!isOpen) return null;
  
  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onClose();
    }
  };
  
  return (
    <S.Overlay onClick={handleOverlayClick}>
      <S.ModalContainer>
        <S.ModalHeader>
          <S.ModalTitle>{title}</S.ModalTitle>
        </S.ModalHeader>
        <S.ModalBody>{message}</S.ModalBody>
        <S.ModalFooter>
          <AlertButton onClick={onClose}>확인</AlertButton>
        </S.ModalFooter>
      </S.ModalContainer>
    </S.Overlay>
  );
};

export default Alert;

