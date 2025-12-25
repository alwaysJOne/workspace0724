import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { boardService } from '../../api/services';
import useAuthStore from '../../store/authStore';
import * as S from './BoardWritePage.styled';

const BoardWritePage = () => {
  const navigate = useNavigate();
  const { getUserId } = useAuthStore();
  const [formData, setFormData] = useState({
    board_title: '',
    board_content: '',
  });
  const [file, setFile] = useState(null);
  const [tags, setTags] = useState([]);
  const [tagInput, setTagInput] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value,
    }));
    setError('');
  };
  
  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
    }
  };
  
  const handleTagInputChange = (e) => {
    setTagInput(e.target.value);
  };
  
  const handleTagInputKeyDown = (e) => {
    if (e.key === 'Enter' && tagInput.trim()) {
      e.preventDefault();
      if (!tags.includes(tagInput.trim())) {
        setTags([...tags, tagInput.trim()]);
      }
      setTagInput('');
    }
  };
  
  const removeTag = (tagToRemove) => {
    setTags(tags.filter(tag => tag !== tagToRemove));
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!formData.board_title.trim() || !formData.board_content.trim()) {
      setError('제목과 내용을 입력해주세요.');
      return;
    }
    
    setLoading(true);
    try {
      const submitFormData = new FormData();
      submitFormData.append('board_title', formData.board_title);
      submitFormData.append('board_content', formData.board_content);
      submitFormData.append('user_id', getUserId());
      
      if (file) {
        submitFormData.append('file', file);
      }
      
      tags.forEach(tag => {
        submitFormData.append('tags', tag);
      });
      
      const boardId = await boardService.createBoard(submitFormData);
      alert('게시글이 작성되었습니다.');
      navigate(`/boards/${boardId}`);
    } catch (err) {
      setError(err.message || '게시글 작성에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <S.WriteContainer>
      <S.Header>
        <S.Title>글쓰기</S.Title>
        <S.BackButton onClick={() => navigate('/boards')}>
          ← 목록으로
        </S.BackButton>
      </S.Header>
      
      <S.Form onSubmit={handleSubmit}>
        {error && <S.ErrorMessage>{error}</S.ErrorMessage>}
        
        <S.InputGroup>
          <S.Label htmlFor="board_title">
            제목<span>*</span>
          </S.Label>
          <S.Input
            type="text"
            id="board_title"
            name="board_title"
            value={formData.board_title}
            onChange={handleChange}
            placeholder="제목을 입력하세요"
          />
        </S.InputGroup>
        
        <S.InputGroup>
          <S.Label htmlFor="board_content">
            내용<span>*</span>
          </S.Label>
          <S.Textarea
            id="board_content"
            name="board_content"
            value={formData.board_content}
            onChange={handleChange}
            placeholder="내용을 입력하세요"
          />
        </S.InputGroup>
        
        <S.InputGroup>
          <S.Label htmlFor="file">첨부파일</S.Label>
          <div>
            <S.FileButton htmlFor="file">
              📎 파일 선택
            </S.FileButton>
            <S.FileInput
              type="file"
              id="file"
              onChange={handleFileChange}
            />
            {file && <S.FileName>{file.name}</S.FileName>}
          </div>
        </S.InputGroup>
        
        <S.InputGroup>
          <S.Label htmlFor="tags">태그</S.Label>
          <S.TagInput>
            {tags.map((tag, index) => (
              <S.TagItem key={index}>
                {tag}
                <button type="button" onClick={() => removeTag(tag)}>
                  ×
                </button>
              </S.TagItem>
            ))}
            <S.TagInputField
              type="text"
              id="tags"
              value={tagInput}
              onChange={handleTagInputChange}
              onKeyDown={handleTagInputKeyDown}
              placeholder="태그 입력 후 Enter"
            />
          </S.TagInput>
        </S.InputGroup>
        
        <S.ButtonGroup>
          <S.CancelButton type="button" onClick={() => navigate('/boards')}>
            취소
          </S.CancelButton>
          <S.SubmitButton type="submit" disabled={loading}>
            {loading ? '작성 중...' : '작성하기'}
          </S.SubmitButton>
        </S.ButtonGroup>
      </S.Form>
    </S.WriteContainer>
  );
};

export default BoardWritePage;

