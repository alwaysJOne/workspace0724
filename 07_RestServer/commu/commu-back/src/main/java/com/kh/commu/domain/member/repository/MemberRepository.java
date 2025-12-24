package com.kh.commu.domain.member.repository;

import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.global.common.CommonEnums;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    List<Member> findByStatus(CommonEnums.Status status);

    List<Member> findByUserNameContainingAndStatus(String keyword, CommonEnums.Status status);

    Optional<Member> findByUserIdAndStatus(String userId, CommonEnums.Status status);
}

