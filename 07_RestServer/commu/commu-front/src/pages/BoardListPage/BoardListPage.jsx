import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { boardService } from '../../api/services';
import * as S from './BoardListPage.styled';

const BoardListPage = () => {
  const navigate = useNavigate();
  const [boards, setBoards] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [pagination, setPagination] = useState({
    page: 0,
    size: 10,
    totalPages: 0,
    totalElements: 0,
  });
  
  useEffect(() => {
    fetchBoards(pagination.page);
  }, [pagination.page]);
  
  const fetchBoards = async (page) => {
    setLoading(true);
    setError('');
    try {
      const response = await boardService.getBoards(page, pagination.size);
      setBoards(response.content || []);
      setPagination(prev => ({
        ...prev,
        page: response.page_number,
        totalPages: response.total_pages,
        totalElements: response.total_elements,
      }));
    } catch (err) {
      setError(err.message || '게시글을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };
  
  const handlePageChange = (newPage) => {
    setPagination(prev => ({ ...prev, page: newPage }));
  };
  
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  };
  
  if (loading && boards.length === 0) {
    return (
      <S.BoardContainer>
        <S.LoadingState>게시글을 불러오는 중...</S.LoadingState>
      </S.BoardContainer>
    );
  }
  
  return (
    <S.BoardContainer>
      <S.Header>
        <S.Title>게시판</S.Title>
        <S.WriteButton onClick={() => navigate('/boards/write')}>
          글쓰기
        </S.WriteButton>
      </S.Header>
      
      {error && <div style={{ color: 'red', marginBottom: '20px' }}>{error}</div>}
      
      {boards.length === 0 ? (
        <S.EmptyState>
          <p>아직 작성된 게시글이 없습니다.</p>
          <S.WriteButton onClick={() => navigate('/boards/write')}>
            첫 게시글 작성하기
          </S.WriteButton>
        </S.EmptyState>
      ) : (
        <>
          <S.BoardList>
            {boards.map((board) => (
              <S.BoardCard
                key={board.board_id}
                onClick={() => navigate(`/boards/${board.board_id}`)}
              >
                <S.BoardHeader>
                  <S.BoardTitle>{board.board_title}</S.BoardTitle>
                  <S.BoardMeta>
                    <span>👁 {board.count}</span>
                  </S.BoardMeta>
                </S.BoardHeader>
                
                <S.BoardContent>{board.board_content}</S.BoardContent>
                
                {board.tags && board.tags.length > 0 && (
                  <S.TagList>
                    {board.tags.map((tag, index) => (
                      <S.Tag key={index}>{tag}</S.Tag>
                    ))}
                  </S.TagList>
                )}
                
                <S.BoardMeta>
                  <span>작성자: {board.user_name}</span>
                  <span>•</span>
                  <span>{formatDate(board.create_date)}</span>
                </S.BoardMeta>
              </S.BoardCard>
            ))}
          </S.BoardList>
          
          {pagination.totalPages > 1 && (
            <S.Pagination>
              <S.PageButton
                onClick={() => handlePageChange(pagination.page - 1)}
                disabled={pagination.page === 0}
              >
                이전
              </S.PageButton>
              
              {Array.from({ length: pagination.totalPages }, (_, i) => (
                <S.PageButton
                  key={i}
                  active={i === pagination.page}
                  onClick={() => handlePageChange(i)}
                >
                  {i + 1}
                </S.PageButton>
              ))}
              
              <S.PageButton
                onClick={() => handlePageChange(pagination.page + 1)}
                disabled={pagination.page === pagination.totalPages - 1}
              >
                다음
              </S.PageButton>
            </S.Pagination>
          )}
        </>
      )}
    </S.BoardContainer>
  );
};

export default BoardListPage;

