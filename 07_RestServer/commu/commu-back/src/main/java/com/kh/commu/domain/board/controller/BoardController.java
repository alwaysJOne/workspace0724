package com.kh.commu.domain.board.controller;

import com.kh.commu.domain.board.dto.BoardDto;
import com.kh.commu.domain.board.service.BoardService;
import com.kh.commu.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> createBoard(
            @RequestParam("board_title") String boardTitle,
            @RequestParam("board_content") String boardContent,
            @RequestParam("user_id") String userId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "tags", required = false) List<String> tags) {
        
        Long boardId = boardService.createBoard(boardTitle, boardContent, userId, file, tags);
        return ResponseEntity.ok(boardId);
    }

    @GetMapping
    public ResponseEntity<PageResponse<BoardDto.Response>> getAllBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createDate,desc") String sort) {
        
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        PageResponse<BoardDto.Response> response = boardService.getAllBoards(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto.Response> getBoardById(@PathVariable Long boardId) {
        BoardDto.Response response = boardService.getBoardById(boardId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardDto.Response> updateBoard(
            @PathVariable Long boardId,
            @RequestParam(value = "board_title", required = false) String boardTitle,
            @RequestParam(value = "board_content", required = false) String boardContent,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "tags", required = false) List<String> tags) {
        
        BoardDto.Response response = boardService.updateBoard(boardId, boardTitle, boardContent, file, tags);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}

