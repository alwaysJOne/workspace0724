package com.kh.jpa.repository;

import com.kh.jpa.entity.Member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);
    List<Member> findAll();
    Member findById(String userId);
}
