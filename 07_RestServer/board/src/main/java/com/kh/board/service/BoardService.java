package com.kh.board.service;

import com.kh.board.entity.Board;
import com.kh.board.entity.Member;

import java.util.List;

public interface BoardService {
    List<Board> findAll();
    int save(Board board);
    Board findOne(Long boardId);
    int delete(Long boardId);
    int update(Board board);
    int patch(Long boardId, String title, String contents, String fileName);
}
