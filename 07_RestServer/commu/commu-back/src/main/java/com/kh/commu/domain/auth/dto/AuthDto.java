package com.kh.commu.domain.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {

        @NotBlank(message = "사용자 ID는 필수입니다")
        private String userId;

        @NotBlank(message = "비밀번호는 필수입니다")
        private String userPwd;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class LoginResponse {

        private String token;
        private String userId;
        private String userName;
        private String role;
    }
}

