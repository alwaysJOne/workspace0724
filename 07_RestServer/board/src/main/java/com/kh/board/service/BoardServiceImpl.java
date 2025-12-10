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

    @Override
    public int delete(Long boardId) {return boardMapper.delete(boardId);}

    @Override
    public int update(Board board) {
        return boardMapper.update(board);
    }

    @Override
    public int patch(Long boardId, String title, String contents, String fileName) {
        Board existBoard = boardMapper.findOne(boardId);

        if(existBoard == null) {
            throw new RuntimeException("board_id가 정상적이지 않습니다.");
        }

        existBoard.patchUpdate(title, contents, fileName);

        return boardMapper.update(existBoard);
    }


}
