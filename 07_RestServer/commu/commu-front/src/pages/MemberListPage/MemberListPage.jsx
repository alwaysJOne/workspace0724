import { useState, useEffect } from 'react';
import { memberService } from '../../api/services';
import * as S from './MemberListPage.styled';

const MemberListPage = () => {
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchKeyword, setSearchKeyword] = useState('');
  
  useEffect(() => {
    fetchMembers();
  }, []);
  
  const fetchMembers = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await memberService.getMembers();
      setMembers(response || []);
    } catch (err) {
      setError(err.message || '멤버 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };
  
  const handleSearch = async () => {
    if (!searchKeyword.trim()) {
      fetchMembers();
      return;
    }
    
    setLoading(true);
    setError('');
    try {
      const response = await memberService.searchMembers(searchKeyword);
      setMembers(response || []);
    } catch (err) {
      setError(err.message || '검색에 실패했습니다.');
      setMembers([]);
    } finally {
      setLoading(false);
    }
  };
  
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };
  
  const getInitial = (name) => {
    return name ? name.charAt(0) : '?';
  };
  
  if (loading) {
    return (
      <S.MemberContainer>
        <S.LoadingState>멤버 목록을 불러오는 중...</S.LoadingState>
      </S.MemberContainer>
    );
  }
  
  return (
    <S.MemberContainer>
      <S.Title>멤버</S.Title>
      
      <S.SearchBox>
        <S.SearchInput
          type="text"
          placeholder="이름으로 검색"
          value={searchKeyword}
          onChange={(e) => setSearchKeyword(e.target.value)}
          onKeyPress={handleKeyPress}
        />
        <S.SearchButton onClick={handleSearch}>검색</S.SearchButton>
      </S.SearchBox>
      
      {error && <div style={{ color: 'red', marginBottom: '20px' }}>{error}</div>}
      
      {members.length === 0 ? (
        <S.EmptyState>
          <p>검색 결과가 없습니다.</p>
        </S.EmptyState>
      ) : (
        <S.MemberGrid>
          {members.map((member) => (
            <S.MemberCard key={member.user_id}>
              <S.MemberHeader>
                <S.Avatar>{getInitial(member.user_name)}</S.Avatar>
                <S.MemberInfo>
                  <S.MemberName>{member.user_name}</S.MemberName>
                  <S.MemberId>@{member.user_id}</S.MemberId>
                </S.MemberInfo>
              </S.MemberHeader>
              
              <S.MemberDetails>
                {member.email && (
                  <S.DetailRow>
                    <strong>이메일</strong>
                    <span>{member.email}</span>
                  </S.DetailRow>
                )}
                {member.phone && (
                  <S.DetailRow>
                    <strong>전화번호</strong>
                    <span>{member.phone}</span>
                  </S.DetailRow>
                )}
                {member.address && (
                  <S.DetailRow>
                    <strong>주소</strong>
                    <span>{member.address}</span>
                  </S.DetailRow>
                )}
                {member.age && (
                  <S.DetailRow>
                    <strong>나이</strong>
                    <span>{member.age}세</span>
                  </S.DetailRow>
                )}
              </S.MemberDetails>
            </S.MemberCard>
          ))}
        </S.MemberGrid>
      )}
    </S.MemberContainer>
  );
};

export default MemberListPage;

