package com.kh.back.domain.user.controller;

import com.kh.back.domain.user.dto.UserRequestDto;
import com.kh.back.domain.user.dto.UserResponseDto;
import com.kh.back.domain.user.service.UserService;
import com.kh.back.global.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponseDto> signUp(@RequestBody @Valid UserRequestDto.SignUp requestDto) {
        return ApiResponse.success(userService.signUp(requestDto));
    }

    @PostMapping("/login")
    public ApiResponse<Map<String, String>> login(@RequestBody @Valid UserRequestDto.Login requestDto) {
        String token = userService.login(requestDto);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ApiResponse.success(response);
    }

    @GetMapping("/me")
    public ApiResponse<UserResponseDto> getMyInfo(@AuthenticationPrincipal UserDetails userDetails) {
        return ApiResponse.success(userService.getMyInfo(userDetails.getUsername()));
    }
}

