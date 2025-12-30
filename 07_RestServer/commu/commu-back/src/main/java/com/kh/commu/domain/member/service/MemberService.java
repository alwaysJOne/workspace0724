package com.kh.commu.domain.member.service;

import com.kh.commu.domain.member.dto.MemberDto;
import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.domain.member.repository.MemberRepository;
import com.kh.commu.global.common.CommonEnums;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public String createMember(MemberDto.Request request) {
        if(memberRepository.existsById(request.getUserId())){
            throw new IllegalArgumentException("이미 존재하는 회원ID입니다.");
        }

        Member member = request.toEntity();
        memberRepository.save(member);
        return member.getUserId();
    }

    public List<MemberDto.Response> getAllMembers() {
        return memberRepository.findByStatus(CommonEnums.Status.Y)
                .stream()
                .map(MemberDto.Response::from)
                .collect(Collectors.toList());
    }

    public MemberDto.Response getMemberById(String userId) {
        Member member = memberRepository.findByUserIdAndStatus(userId, CommonEnums.Status.Y)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + userId));
        return MemberDto.Response.from(member);
    }

    @Transactional
    public MemberDto.Response updateMember(String userId, MemberDto.UpdateRequest request) {
        Member member = memberRepository.findByUserIdAndStatus(userId, CommonEnums.Status.Y)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + userId));

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다: " + userId));

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

