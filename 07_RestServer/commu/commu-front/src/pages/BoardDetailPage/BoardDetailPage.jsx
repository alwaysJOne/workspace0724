import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { boardService } from '../../api/services';
import useAuthStore from '../../store/authStore';
import { Confirm } from '../../components/common/Modal';
import useModal from '../../hooks/useModal';
import * as S from './BoardDetailPage.styled';

const BoardDetailPage = () => {
  const navigate = useNavigate();
  const { boardId } = useParams();
  const { user } = useAuthStore();
  const [board, setBoard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const deleteModal = useModal();
  
  useEffect(() => {
    fetchBoard();
  }, [boardId]);
  
  const fetchBoard = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await boardService.getBoard(boardId);
      setBoard(response);
    } catch (err) {
      setError(err.message || '게시글을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };
  
  const handleDelete = async () => {
    try {
      await boardService.deleteBoard(boardId);
      alert('게시글이 삭제되었습니다.');
      navigate('/boards');
    } catch (err) {
      alert(err.message || '게시글 삭제에 실패했습니다.');
    }
  };
  
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };
  
  const isAuthor = board && user && board.user_id === user.user_id;
  
  if (loading) {
    return (
      <S.DetailContainer>
        <S.LoadingState>게시글을 불러오는 중...</S.LoadingState>
      </S.DetailContainer>
    );
  }
  
  if (error || !board) {
    return (
      <S.DetailContainer>
        <S.ErrorState>
          <p>{error || '게시글을 찾을 수 없습니다.'}</p>
          <S.BackButton onClick={() => navigate('/boards')}>
            목록으로
          </S.BackButton>
        </S.ErrorState>
      </S.DetailContainer>
    );
  }
  
  return (
    <S.DetailContainer>
      <S.Header>
        <S.BackButton onClick={() => navigate('/boards')}>
          ← 목록으로
        </S.BackButton>
        
        {isAuthor && (
          <S.ButtonGroup>
            <S.EditButton onClick={() => navigate(`/boards/edit/${boardId}`)}>
              수정
            </S.EditButton>
            <S.DeleteButton onClick={() => deleteModal.openModal()}>
              삭제
            </S.DeleteButton>
          </S.ButtonGroup>
        )}
      </S.Header>
      
      <S.Article>
        <S.ArticleHeader>
          <S.Title>{board.board_title}</S.Title>
          
          <S.Meta>
            <span>작성자: {board.user_name}</span>
            <span>•</span>
            <span>{formatDate(board.create_date)}</span>
            <span>•</span>
            <span>👁 {board.count}</span>
          </S.Meta>
          
          {board.tags && board.tags.length > 0 && (
            <S.TagList>
              {board.tags.map((tag, index) => (
                <S.Tag key={index}>{tag}</S.Tag>
              ))}
            </S.TagList>
          )}
        </S.ArticleHeader>
        
        <S.Content>{board.board_content}</S.Content>
        
        {board.origin_name && (
          <S.FileSection>
            <S.FileLabel>첨부파일</S.FileLabel>
            <S.FileLink href="#" onClick={(e) => e.preventDefault()}>
              📎 {board.origin_name}
            </S.FileLink>
          </S.FileSection>
        )}
      </S.Article>
      
      <Confirm
        isOpen={deleteModal.isOpen}
        onConfirm={() => {
          deleteModal.closeModal();
          handleDelete();
        }}
        onCancel={deleteModal.closeModal}
        title="게시글 삭제"
        message="정말 이 게시글을 삭제하시겠습니까?"
        confirmText="삭제"
        cancelText="취소"
      />
    </S.DetailContainer>
  );
};

export default BoardDetailPage;

