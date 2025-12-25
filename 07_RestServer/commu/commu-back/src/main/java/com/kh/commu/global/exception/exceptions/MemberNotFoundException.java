package com.kh.commu.global.exception.exceptions;

import com.kh.commu.global.exception.BaseException;
import com.kh.commu.global.exception.ErrorCode;

/**
 * 회원을 찾을 수 없을 때 발생하는 예외
 */
public class MemberNotFoundException extends BaseException {
    
    public MemberNotFoundException() {
        super(ErrorCode.MEMBER_NOT_FOUND);
    }
    
    public MemberNotFoundException(String message) {
        super(ErrorCode.MEMBER_NOT_FOUND, message);
    }
}

