package com.kh.board.controller;

import com.kh.board.controller.dto.request.BoardRequest;
import com.kh.board.controller.dto.response.BoardResponse;
import com.kh.board.entity.Board;
import com.kh.board.entity.Member;
import com.kh.board.mapper.BoardMapper;
import com.kh.board.service.BoardService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController//모든 controller 메서드의 리턴을 ResponseBody로 처리하여 데이터를 반환한다.
@RequestMapping("/api/board")
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

    @PostMapping
    public ResponseEntity<String> createBoard(BoardRequest.CreateDto request, MultipartFile upfile) throws IOException {
        if (request == null || request.getUser_id() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(!upfile.isEmpty()){
            File file = new File("C:\\workspace\\07_RestServer\\board\\src\\main\\resources\\uploads", upfile.getOriginalFilename());
            upfile.transferTo(file);

            request.setFile_name("/uploads/"+upfile.getOriginalFilename());
        }

        Board board = request.toEntity();
        int result = boardService.save(board);

        if(result > 0){
            return new ResponseEntity<>("게시글 등록 성공", HttpStatus.OK);
        } else{
            return new ResponseEntity<>("게시글 등록 실패", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardResponse.DetailDto> getBoard(@PathVariable Long boardId){
        Board board = boardService.findOne(boardId);
        BoardResponse.DetailDto result = BoardResponse.DetailDto.of(board);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
