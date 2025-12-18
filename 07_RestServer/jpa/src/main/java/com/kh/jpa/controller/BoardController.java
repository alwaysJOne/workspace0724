package com.kh.jpa.controller;

import com.kh.jpa.dto.BoardDto;
import com.kh.jpa.entity.Board;
import com.kh.jpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> createBoard(@ModelAttribute BoardDto.Create createBoardDto) throws IOException {
        Long boardId = boardService.createBoard(createBoardDto);
        return ResponseEntity.ok(boardId);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<?> getBoard(@PathVariable("boardId") Long boardId) throws IOException {
        return ResponseEntity.ok("BoardDto.ResponseDetail")
    }
}
