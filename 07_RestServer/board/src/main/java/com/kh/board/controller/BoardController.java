package com.kh.board.controller;

import com.kh.board.controller.dto.response.BoardResponse;
import com.kh.board.entity.Board;
import com.kh.board.mapper.BoardMapper;
import com.kh.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController//모든 controller 메서드의 리턴을 ResponseBody로 처리하여 데이터를 반환한다.
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    //@ResponseBody
    @GetMapping
    public ResponseEntity<List<BoardResponse.SimpleDto>> getBoards(){
        //게시글 목록을 데이터베이스로부터 가져와 반환
        List<Board> boards = boardService.findAll();

        List<BoardResponse.SimpleDto> result = new ArrayList<>();
        for (Board board : boards){
            result.add(BoardResponse.SimpleDto.of(board));
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
