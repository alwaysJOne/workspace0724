package com.kh.jpa.repository;

import com.kh.jpa.entity.Board;
import com.kh.jpa.entity.Member;
import com.kh.jpa.enums.CommonEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardJPARepository extends JpaRepository<Board, Long> {

    //상태값을 통한 게시글 조회
    Page<Board> findByStatus(CommonEnums.Status status, Pageable pageable);

    //작성자로 게시글 조회
    List<Board> findByMember(Member member);
    //작성자의 아이디로 조회
    List<Board> findByMemberUserId(String userId);

    //제목 또는 내용으로 게시글 검색
    List<Board> findByBoardTitleContainingOrBoardContentContaining(String title, String content);

    //조회수가 높은 순으로 게시글 조회
    List<Board> findByOrderByCountDesc();

    //특정 작성자의 활성 게시글 조회(페이징) -> JPQL로
    @Query("select b from Board b where b.member.userId = :userId and b.status = :status")
    List<Board> findByMemberAndStatus(@Param("userId") String userId, @Param("status") CommonEnums.Status status);

    //특정 태그를 가진 게시글 조회
    @Query("select distinct b from Board b " +
            "join b.boardTags bt " +
            "join bt.tag t " +
            "where t.tagName = :tagName and b.status = :status")
    //조인은 행을 기준으로 하기때문에
    //하나의 Board가 태그 갯수만큼 조회됨
    // jpql의 distinct는 같은 식별자(pk)를 가진 board는 결과리스트에 한번만 넣어라
    Page<Board> findByTagName(@Param("tagName") String tagName,
                              @Param("status") CommonEnums.Status status,
                              Pageable pageable);

}
