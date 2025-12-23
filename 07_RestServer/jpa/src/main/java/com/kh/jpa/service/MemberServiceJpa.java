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
        return null;
    }

    @Override
    public MemberDto.Response updateMember(String userId, MemberDto.Update updateMemberDto) {
        return null;
    }

    @Override
    public void deleteMember(String userId) {

    }

    @Override
    public List<MemberDto.Response> getMembersByName(String keyword) {
        return List.of();
    }
}
