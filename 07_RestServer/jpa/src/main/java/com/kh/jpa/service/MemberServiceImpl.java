package com.kh.jpa.service;

import com.kh.jpa.dto.MemberDto;
import com.kh.jpa.entity.Member;
import com.kh.jpa.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public String createMember(MemberDto.Create createMemberDto) {
        Member member = createMemberDto.toEntity();
        memberRepository.save(member); //member는 영속상태
        return member.getUserId();
    }

    @Override
    public List<MemberDto.Response> getAllMembers() {
        /*
        List<MemberDto.Response>  responseList = new ArrayList<>();
        for (Member member : memberList) {

            MemberDto.Response  response = MemberDto.Response.of(
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

            responseList.add(response);
        }
         */
        return memberRepository.findAll()
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
        return memberRepository.findById(userId)
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
        //findById는 Optional리턴함
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        //영속상태의 member를 수정하기때문에 트랜잭션 완료시점에 실제 update문이 절잘된다.
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
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        memberRepository.delete(member);
    }

    @Override
    public List<MemberDto.Response> getMembersByName(String keyword) {
        return memberRepository.findByUserNameContaining(keyword)
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
