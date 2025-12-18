package com.kh.jpa.service;

import com.kh.jpa.dto.BoardDto;

import java.io.IOException;

public interface BoardService {
    Long createBoard(BoardDto.Create createDto) throws IOException;
    BoardDto.Response getBoardDetail(Long boardId);
}
