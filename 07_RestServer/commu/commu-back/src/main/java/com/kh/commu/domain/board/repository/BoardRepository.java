package com.kh.commu.domain.board.repository;

import com.kh.commu.domain.board.entity.Board;
import com.kh.commu.global.common.CommonEnums;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findByStatus(CommonEnums.Status status, Pageable pageable);

    @Query("SELECT b FROM Board b JOIN FETCH b.member WHERE b.boardId = :boardId AND b.status = :status")
    Optional<Board> findByIdWithMember(@Param("boardId") Long boardId, @Param("status") CommonEnums.Status status);

    @Query("SELECT b FROM Board b JOIN FETCH b.member LEFT JOIN FETCH b.boardTags bt LEFT JOIN FETCH bt.tag WHERE b.boardId = :boardId AND b.status = :status")
    Optional<Board> findByIdWithMemberAndTags(@Param("boardId") Long boardId, @Param("status") CommonEnums.Status status);
}

