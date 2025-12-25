package com.kh.commu.global.dto;

import com.kh.commu.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 에러 응답 DTO
 * 일관된 에러 응답 형식 제공
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {
    
    private final int status;
    private final String message;
    private final String path;
    private final LocalDateTime timestamp;
    
    /**
     * ErrorCode 기반 ErrorResponse 생성
     */
    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                errorCode.getStatus().value(),
                errorCode.getMessage(),
                path,
                LocalDateTime.now()
        );
    }
    
    /**
     * 커스텀 메시지를 포함한 ErrorResponse 생성
     */
    public static ErrorResponse of(ErrorCode errorCode, String message, String path) {
        return new ErrorResponse(
                errorCode.getStatus().value(),
                message,
                path,
                LocalDateTime.now()
        );
    }
}

