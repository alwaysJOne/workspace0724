package com.kh.commu.domain.board.repository;

import com.kh.commu.domain.board.entity.BoardTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

    List<BoardTag> findByBoard_BoardId(Long boardId);

    void deleteByBoard_BoardId(Long boardId);
}

