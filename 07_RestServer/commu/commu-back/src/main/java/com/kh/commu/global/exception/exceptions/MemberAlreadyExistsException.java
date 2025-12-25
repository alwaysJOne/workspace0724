package com.kh.commu.global.exception.exceptions;

import com.kh.commu.global.exception.BaseException;
import com.kh.commu.global.exception.ErrorCode;

/**
 * 회원이 이미 존재할 때 발생하는 예외
 */
public class MemberAlreadyExistsException extends BaseException {
    
    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}

