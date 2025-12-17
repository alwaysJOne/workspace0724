package com.kh.jpa.controller;

import com.kh.jpa.dto.MemberDto;
import com.kh.jpa.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    //회원등록 api
    @PostMapping
    public ResponseEntity<String> addMember(@RequestBody MemberDto.Create createMemberDto) {
        String userId = memberService.createMember(createMemberDto);
        //return new ResponseEntity<>(userId, HttpStatus.OK);
        return ResponseEntity.ok(userId);
    }

    //회원 전체조회
    @GetMapping
    public ResponseEntity<List<MemberDto.Response>> getAllMembers() {
        return  ResponseEntity.ok(memberService.getAllMembers());
    }

    //id로 회원조회
    @GetMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> getMember(@PathVariable String userId) {
        return ResponseEntity.ok(memberService.getMemberByUserId(userId));
    }

    //회원수정
    @PutMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> updateMember(
            @PathVariable String userId,
            @RequestBody MemberDto.Update updateMemberDto) {

        MemberDto.Response response = memberService.updateMember(userId, updateMemberDto);
        return ResponseEntity.ok(response);
    }

    //회원삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteMember(@PathVariable String userId) {
        memberService.deleteMember(userId);
        return  ResponseEntity.ok("ok");
    }

    //이름으로 회원 검색
    @GetMapping("/search")
    public ResponseEntity<List<MemberDto.Response>> searchMemberByName(@RequestParam String keyword) {
        return ResponseEntity.ok(memberService.getMembersByName(keyword));
        //keyword로 userName 키워드 검색 후 결과값 반환
    }
}
