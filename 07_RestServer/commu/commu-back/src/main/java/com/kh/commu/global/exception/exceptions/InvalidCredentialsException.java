package com.kh.commu.global.exception.exceptions;

import com.kh.commu.global.exception.BaseException;
import com.kh.commu.global.exception.ErrorCode;

/**
 * 로그인 인증 실패 시 발생하는 예외
 */
public class InvalidCredentialsException extends BaseException {
    
    public InvalidCredentialsException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}

