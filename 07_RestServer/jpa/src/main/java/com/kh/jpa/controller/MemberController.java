package com.kh.jpa.controller;

import com.kh.jpa.dto.MemberDto;
import com.kh.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    //회원등록 api
    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody MemberDto.Create createMemberDto) {
        String userId = memberService.createMember(createMemberDto);
//        return new ResponseEntity<>(userId, HttpStatus.OK);
        return ResponseEntity.ok(userId);
    }
}
