package com.kh.back.domain.comment.controller;

import com.kh.back.domain.comment.dto.CommentRequestDto;
import com.kh.back.domain.comment.dto.CommentResponseDto;
import com.kh.back.domain.comment.service.CommentService;
import com.kh.back.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentResponseDto> createComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId,
            @RequestBody @Valid CommentRequestDto.Create requestDto) {
        return ApiResponse.success(commentService.createComment(userDetails.getUsername(), postId, requestDto));
    }

    @GetMapping
    public ApiResponse<List<CommentResponseDto>> getComments(@PathVariable Long postId) {
        return ApiResponse.success(commentService.getComments(postId));
    }

    @PutMapping("/{commentId}")
    public ApiResponse<CommentResponseDto> updateComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto.Update requestDto) {
        return ApiResponse.success(commentService.updateComment(userDetails.getUsername(), commentId, requestDto));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Void> deleteComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long commentId) {
        commentService.deleteComment(userDetails.getUsername(), commentId);
        return ApiResponse.success(null);
    }
}

