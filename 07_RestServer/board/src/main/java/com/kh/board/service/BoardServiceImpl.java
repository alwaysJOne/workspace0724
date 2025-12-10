package com.kh.board.service;

import com.kh.board.entity.Board;
import com.kh.board.entity.Member;
import com.kh.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

//    public BoardServiceImpl(BoardMapper boardMapper) {
//        this.boardMapper = boardMapper;
//    }



    @Override
    public List<Board> findAll() {return boardMapper.findAll();}

    @Override
    public int save(Board board) {return boardMapper.save(board);}

    @Override
    public Board findOne(Long boardId) {return boardMapper.findOne(boardId);}


}
