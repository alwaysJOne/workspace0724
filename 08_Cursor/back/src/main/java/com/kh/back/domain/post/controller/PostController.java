package com.kh.back.domain.post.controller;

import com.kh.back.domain.post.dto.PostRequestDto;
import com.kh.back.domain.post.dto.PostResponseDto;
import com.kh.back.domain.post.entity.Category;
import com.kh.back.domain.post.service.PostService;
import com.kh.back.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ApiResponse<PostResponseDto> createPost(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid PostRequestDto.Create requestDto) {
        return ApiResponse.success(postService.createPost(userDetails.getUsername(), requestDto));
    }

    @GetMapping
    public ApiResponse<Page<PostResponseDto>> getPosts(
            @PageableDefault(size = 10) Pageable pageable,
            @RequestParam(required = false) Category category) {
        return ApiResponse.success(postService.getPosts(pageable, category));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponseDto> getPost(@PathVariable Long id) {
        return ApiResponse.success(postService.getPost(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<PostResponseDto> updatePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id,
            @RequestBody @Valid PostRequestDto.Update requestDto) {
        return ApiResponse.success(postService.updatePost(userDetails.getUsername(), id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long id) {
        postService.deletePost(userDetails.getUsername(), id);
        return ApiResponse.success(null);
    }

    @GetMapping("/my")
    public ApiResponse<Page<PostResponseDto>> getMyPosts(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10) Pageable pageable) {
        return ApiResponse.success(postService.getMyPosts(userDetails.getUsername(), pageable));
    }
}

