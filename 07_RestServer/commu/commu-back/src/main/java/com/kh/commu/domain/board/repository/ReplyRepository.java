package com.kh.commu.domain.board.repository;

import com.kh.commu.domain.board.entity.Reply;
import com.kh.commu.global.common.CommonEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    List<Reply> findByBoard_BoardIdAndStatus(Long boardId, CommonEnums.Status status);
}

