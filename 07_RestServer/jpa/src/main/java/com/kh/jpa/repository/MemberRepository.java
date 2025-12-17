package com.kh.jpa.repository;

import com.kh.jpa.entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void save(Member member);
    List<Member> findAll();
    Optional<Member> findById(String userId);
    void delete(Member member);
    List<Member> findByUserNameContaining(String keyword);
}
