package com.kh.commu.domain.member.controller;

import com.kh.commu.domain.member.dto.MemberDto;
import com.kh.commu.domain.member.service.MemberService;
import com.kh.commu.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 생성 (회원가입) - 인증 불필요
     * POST /api/v1/members
     */
    @PostMapping
    public ResponseEntity<ApiResponse<String>> createMember(@Valid @RequestBody MemberDto.Request request) {
        String userId = memberService.createMember(request);
        return ResponseEntity.ok(ApiResponse.success(userId));
    }

    /**
     * 회원 목록 조회 (인증 필요)
     * GET /api/v1/members
     * GET /api/v1/members?keyword=xxx (검색)
     */
    @GetMapping
    public ResponseEntity<List<MemberDto.Response>> getMembers(
            @RequestParam(required = false) String keyword) {
        List<MemberDto.Response> members;
        
        if (keyword != null && !keyword.isEmpty()) {
            // 키워드가 있으면 검색
            members = memberService.searchMembers(keyword);
        } else {
            // 키워드가 없으면 전체 조회
            members = memberService.getAllMembers();
        }
        
        return ResponseEntity.ok(members);
    }

    /**
     * 회원 단건 조회 (인증 필요)
     * GET /api/v1/members/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> getMemberById(@PathVariable String userId) {
        MemberDto.Response response = memberService.getMemberById(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 정보 수정 (인증 필요)
     * PUT /api/v1/members/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> updateMember(
            @PathVariable String userId,
            @Valid @RequestBody MemberDto.UpdateRequest request) {
        MemberDto.Response response = memberService.updateMember(userId, request);
        return ResponseEntity.ok(response);
    }

    /**
     * 회원 삭제 (인증 필요)
     * DELETE /api/v1/members/{userId}
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteMember(@PathVariable String userId) {
        String result = memberService.deleteMember(userId);
        return ResponseEntity.ok(result);
    }
}

