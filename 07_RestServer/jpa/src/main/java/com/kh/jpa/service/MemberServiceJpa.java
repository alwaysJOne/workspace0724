package com.kh.jpa.service;

import com.kh.jpa.dto.MemberDto;
import com.kh.jpa.entity.Member;
import com.kh.jpa.repository.MemberJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberServiceJpa implements  MemberService{

    private final MemberJPARepository memberJPARepository;

    @Override
    public String createMember(MemberDto.Create createMemberDto) {
        Member member = createMemberDto.toEntity();
        memberJPARepository.save(member);
        return member.getUserId();
    }

    @Override
    public List<MemberDto.Response> getAllMembers() {
        return memberJPARepository.findAll()
                .stream()
                .map(member -> MemberDto.Response.of(
                        member.getUserId(),
                        member.getUserName(),
                        member.getEmail(),
                        member.getGender(),
                        member.getAge(),
                        member.getPhone(),
                        member.getAddress(),
                        member.getCreateDate(),
                        member.getModifyDate())
                )
                .toList();
    }

    @Override
    public MemberDto.Response getMemberByUserId(String userId) {
        return memberJPARepository.findById(userId)
                .map(member -> MemberDto.Response.of(
                        member.getUserId(),
                        member.getUserName(),
                        member.getEmail(),
                        member.getGender(),
                        member.getAge(),
                        member.getPhone(),
                        member.getAddress(),
                        member.getCreateDate(),
                        member.getModifyDate())
                )
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }

    @Override
    public MemberDto.Response updateMember(String userId, MemberDto.Update updateMemberDto) {
        Member member = memberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        member.putUpdate(
                updateMemberDto.getUser_name(),
                updateMemberDto.getEmail(),
                updateMemberDto.getGender(),
                updateMemberDto.getAge(),
                updateMemberDto.getPhone(),
                updateMemberDto.getAddress()
        );

        return MemberDto.Response.of(
                member.getUserId(),
                member.getUserName(),
                member.getEmail(),
                member.getGender(),
                member.getAge(),
                member.getPhone(),
                member.getAddress(),
                member.getCreateDate(),
                member.getModifyDate()
        );
    }

    @Override
    public void deleteMember(String userId) {
        Member member = memberJPARepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        memberJPARepository.delete(member);
    }

    @Override
    public List<MemberDto.Response> getMembersByName(String keyword) {
        return  memberJPARepository.findByUserNameContaining(keyword)
                .stream()
                .map((member) -> MemberDto.Response.of(
                        member.getUserId(),
                        member.getUserName(),
                        member.getEmail(),
                        member.getGender(),
                        member.getAge(),
                        member.getPhone(),
                        member.getAddress(),
                        member.getCreateDate(),
                        member.getModifyDate()
                )).toList();
    }
}
