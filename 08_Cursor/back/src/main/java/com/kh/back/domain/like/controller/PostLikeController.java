package com.kh.back.domain.like.controller;

import com.kh.back.domain.like.dto.PostLikeResponseDto;
import com.kh.back.domain.like.service.PostLikeService;
import com.kh.back.global.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/like")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ApiResponse<PostLikeResponseDto> toggleLike(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        return ApiResponse.success(postLikeService.toggleLike(userDetails.getUsername(), postId));
    }

    @GetMapping
    public ApiResponse<Boolean> isLiked(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long postId) {
        return ApiResponse.success(postLikeService.isLiked(userDetails.getUsername(), postId));
    }
}

