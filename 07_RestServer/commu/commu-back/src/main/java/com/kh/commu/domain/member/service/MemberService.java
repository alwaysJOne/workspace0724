package com.kh.commu.domain.member.service;

import com.kh.commu.domain.member.dto.MemberDto;
import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.domain.member.repository.MemberRepository;
import com.kh.commu.global.common.CommonEnums;
import com.kh.commu.global.exception.exceptions.MemberAlreadyExistsException;
import com.kh.commu.global.exception.exceptions.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createMember(MemberDto.Request request) {
        // 중복 회원 검증
        memberRepository.findById(request.getUserId())
                .ifPresent(m -> {
                    throw new MemberAlreadyExistsException();
                });
        
        // 비밀번호 암호화
        Member member = request.toEntity();
        Member encryptedMember = Member.builder()
                .userId(member.getUserId())
                .userPwd(passwordEncoder.encode(member.getUserPwd()))
                .userName(member.getUserName())
                .email(member.getEmail())
                .gender(member.getGender())
                .age(member.getAge())
                .phone(member.getPhone())
                .address(member.getAddress())
                .status(CommonEnums.Status.Y)
                .role(Member.Role.USER)
                .build();
        
        memberRepository.save(encryptedMember);
        return encryptedMember.getUserId();
    }

    public List<MemberDto.Response> getAllMembers() {
        return memberRepository.findByStatus(CommonEnums.Status.Y)
                .stream()
                .map(MemberDto.Response::from)
                .collect(Collectors.toList());
    }

    public MemberDto.Response getMemberById(String userId) {
        Member member = memberRepository.findByUserIdAndStatus(userId, CommonEnums.Status.Y)
                .orElseThrow(() -> new MemberNotFoundException("회원 ID: " + userId));
        return MemberDto.Response.from(member);
    }

    @Transactional
    public MemberDto.Response updateMember(String userId, MemberDto.UpdateRequest request) {
        Member member = memberRepository.findByUserIdAndStatus(userId, CommonEnums.Status.Y)
                .orElseThrow(() -> new MemberNotFoundException("회원 ID: " + userId));

        member.updateMemberInfo(
                request.getUserName(),
                request.getEmail(),
                request.getGender(),
                request.getAge(),
                request.getPhone(),
                request.getAddress()
        );

        return MemberDto.Response.from(member);
    }

    @Transactional
    public String deleteMember(String userId) {
        Member member = memberRepository.findByUserIdAndStatus(userId, CommonEnums.Status.Y)
                .orElseThrow(() -> new MemberNotFoundException("회원 ID: " + userId));

        member.delete();
        return "ok";
    }

    public List<MemberDto.Response> searchMembers(String keyword) {
        return memberRepository.findByUserNameContainingAndStatus(keyword, CommonEnums.Status.Y)
                .stream()
                .map(MemberDto.Response::from)
                .collect(Collectors.toList());
    }
}

