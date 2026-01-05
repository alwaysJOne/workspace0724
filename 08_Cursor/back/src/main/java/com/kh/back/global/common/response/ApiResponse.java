package com.kh.back.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ApiError error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static ApiResponse<?> error(String message, int status) {
        return ApiResponse.builder()
                .success(false)
                .error(new ApiError(message, status))
                .build();
    }

    @Getter
    @AllArgsConstructor
    public static class ApiError {
        private String message;
        private int status;
    }
}

