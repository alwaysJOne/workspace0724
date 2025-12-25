package com.kh.commu.global.exception;

import lombok.Getter;

/**
 * 모든 커스텀 예외의 부모 클래스
 * ErrorCode를 포함하여 일관된 예외 처리 가능
 */
@Getter
public abstract class BaseException extends RuntimeException {
    
    private final ErrorCode errorCode;
    
    protected BaseException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    protected BaseException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}

