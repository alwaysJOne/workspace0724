package com.kh.jpa.service;

import com.kh.jpa.dto.BoardDto;
import com.kh.jpa.repository.BoardJPARepository;
import com.kh.jpa.repository.MemberJPARepository;
import com.kh.jpa.repository.MemberRepository;
import com.kh.jpa.repository.TagJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BoardServiceJpa implements BoardService{

    private final MemberJPARepository memberJPARepository;
    private final BoardJPARepository boardJPARepository;
    private final TagJPARepository tagJPARepository;
    private final String FILE_PATH = "C:\\devtool\\upload";

    
    @Override
    public Long createBoard(BoardDto.Create createDto) throws IOException {
        return 0L;
    }

    @Override
    public BoardDto.Response getBoardDetail(Long boardId) {
        return null;
    }

    @Override
    public Page<BoardDto.Response> getBoardList(Pageable pageable) {
        return null;
    }

    @Override
    public BoardDto.Response updateBoard(Long boardId, BoardDto.Update updateBoardDto) throws IOException {
        return null;
    }

    @Override
    public void deleteBoard(Long boardId) {

    }
}
