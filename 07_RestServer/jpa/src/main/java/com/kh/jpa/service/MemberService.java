package com.kh.jpa.service;

import com.kh.jpa.dto.MemberDto;

public interface MemberService {
    String createMember(MemberDto.Create createMemberDto);
}
