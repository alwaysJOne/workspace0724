package com.kh.commu.global.exception;

import com.kh.commu.global.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 전역 예외 처리 핸들러
 * 모든 예외를 한 곳에서 통합 처리하여 일관된 응답 형식 제공
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * BaseException을 상속한 모든 커스텀 예외 처리
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(
            BaseException ex, 
            HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ex.getErrorCode(), 
                request.getRequestURI()
        );
        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(errorResponse);
    }

    /**
     * Validation 예외 처리 (@Valid 실패 시)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        
        // 모든 필드 에러를 수집하여 메시지 생성
        String errorMessage = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return fieldName + ": " + message;
                })
                .collect(Collectors.joining(", "));
        
        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE,
                errorMessage,
                request.getRequestURI()
        );
        
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(errorResponse);
    }

    /**
     * IllegalArgumentException 처리
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.INVALID_INPUT_VALUE,
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.getStatus())
                .body(errorResponse);
    }

    /**
     * 기타 예상하지 못한 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception ex,
            HttpServletRequest request) {
        ErrorResponse errorResponse = ErrorResponse.of(
                ErrorCode.INTERNAL_SERVER_ERROR,
                request.getRequestURI()
        );
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(errorResponse);
    }
}

