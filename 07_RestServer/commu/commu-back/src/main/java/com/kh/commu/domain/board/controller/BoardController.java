package com.kh.commu.domain.board.controller;

import com.kh.commu.domain.board.dto.BoardDto;
import com.kh.commu.domain.board.service.BoardService;
import com.kh.commu.global.dto.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Validated
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> createBoard(
            @Valid @RequestBody BoardDto.Request request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        
        Long boardId = boardService.createBoard(
                request.getBoardTitle(),
                request.getBoardContent(),
                request.getUserId(),
                file,
                request.getTags()
        );
        return ResponseEntity.ok(boardId);
    }

    @GetMapping
    public ResponseEntity<PageResponse<BoardDto.Response>> getAllBoards(
            @RequestParam(defaultValue = "0") @PositiveOrZero(message = "페이지 번호는 0 이상이어야 합니다") int page,
            @RequestParam(defaultValue = "5") @Positive(message = "페이지 크기는 1 이상이어야 합니다") int size,
            @RequestParam(defaultValue = "createDate,desc") String sort) {
        
        String[] sortParams = sort.split(",");
        Sort.Direction direction = sortParams.length > 1 && sortParams[1].equalsIgnoreCase("desc") 
                ? Sort.Direction.DESC : Sort.Direction.ASC;
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortParams[0]));
        PageResponse<BoardDto.Response> response = boardService.getAllBoards(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardDto.Response> getBoardById(
            @PathVariable @Positive(message = "게시글 ID는 양수여야 합니다") Long boardId) {
        BoardDto.Response response = boardService.getBoardById(boardId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<BoardDto.Response> updateBoard(
            @PathVariable @Positive(message = "게시글 ID는 양수여야 합니다") Long boardId,
            @Valid @RequestBody BoardDto.UpdateRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        
        BoardDto.Response response = boardService.updateBoard(
                boardId,
                request.getBoardTitle(),
                request.getBoardContent(),
                file,
                request.getTags()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(
            @PathVariable @Positive(message = "게시글 ID는 양수여야 합니다") Long boardId) {
        boardService.deleteBoard(boardId);
        return ResponseEntity.noContent().build();
    }
}

