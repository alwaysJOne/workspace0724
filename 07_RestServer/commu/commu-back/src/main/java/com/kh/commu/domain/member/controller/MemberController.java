package com.kh.commu.domain.member.controller;

import com.kh.commu.domain.member.dto.MemberDto;
import com.kh.commu.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> registerMember(@Valid @RequestBody MemberDto.Request request) {
        String userId = memberService.createMember(request);
        return ResponseEntity.ok(userId);
    }

    @GetMapping
    public ResponseEntity<List<MemberDto.Response>> getAllMembers() {
        List<MemberDto.Response> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> getMemberById(@PathVariable String userId) {
        MemberDto.Response response = memberService.getMemberById(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> updateMember(
            @PathVariable String userId,
            @Valid @RequestBody MemberDto.UpdateRequest request) {
        MemberDto.Response response = memberService.updateMember(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteMember(@PathVariable String userId) {
        String result = memberService.deleteMember(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MemberDto.Response>> searchMembers(@RequestParam String keyword) {
        List<MemberDto.Response> members = memberService.searchMembers(keyword);
        return ResponseEntity.ok(members);
    }
}

