import { buttonPrimary, buttonSecondary } from '../../../styles/mixins';
import * as S from './Modal.styled';
import styled from 'styled-components';

const ConfirmButton = styled.button`
  ${buttonPrimary}
  min-width: 100px;
`;

const CancelButton = styled.button`
  ${buttonSecondary}
  min-width: 100px;
`;

const Confirm = ({ 
  isOpen, 
  onConfirm, 
  onCancel, 
  title = '확인', 
  message,
  confirmText = '확인',
  cancelText = '취소'
}) => {
  if (!isOpen) return null;
  
  const handleOverlayClick = (e) => {
    if (e.target === e.currentTarget) {
      onCancel();
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
          <CancelButton onClick={onCancel}>{cancelText}</CancelButton>
          <ConfirmButton onClick={onConfirm}>{confirmText}</ConfirmButton>
        </S.ModalFooter>
      </S.ModalContainer>
    </S.Overlay>
  );
};

export default Confirm;

