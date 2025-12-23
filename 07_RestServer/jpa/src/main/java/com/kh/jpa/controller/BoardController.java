package com.kh.jpa.controller;

import com.kh.jpa.dto.BoardDto;
import com.kh.jpa.dto.PageResponse;
import com.kh.jpa.entity.Board;
import com.kh.jpa.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> createBoard(@ModelAttribute BoardDto.Create createBoardDto) throws IOException {
        Long boardId = boardService.createBoard(createBoardDto);
        return ResponseEntity.ok(boardId);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto.Response> getBoard(@PathVariable("boardId") Long boardId) {
        return ResponseEntity.ok(boardService.getBoardDetail(boardId));
    }

    /*
    page 확인하고자하는 페이지번호
    size 몇개를 가지고오겠는가
    sort 정렬을 어떻게 하겠는가? : 속성, 방향(boardTitle, desc)
     */
    @GetMapping
    public ResponseEntity<PageResponse<BoardDto.Response>> getAllBoards(
            @PageableDefault(size = 5, sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
            Page<BoardDto.Response> page = boardService.getBoardList(pageable);
            return ResponseEntity.ok(new PageResponse<>(page));
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardDto.Response> updateBoard(
            @PathVariable("boardId") Long boardId,
            @ModelAttribute BoardDto.Update updateBoard
    ) throws IOException {

        BoardDto.Response boardDto = boardService.updateBoard(boardId, updateBoard);
        return ResponseEntity.ok(boardDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
    }
}
