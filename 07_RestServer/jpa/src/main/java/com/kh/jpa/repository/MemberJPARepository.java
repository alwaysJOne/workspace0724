package com.kh.jpa.repository;

import com.kh.jpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository를 상속받아 기본 CRUD메서드를 자동으로 제공받을 수 있음
// save(Member m) - 저장
// findById(String id) - id(pk)로 조회
// findAll() - 전체조회
// delete(Member m ) - 삭제
// count() - 개수조회
// existsByid(String id) - 존재여부

public interface MemberJPARepository extends JpaRepository<Member, String> {
    List<Member> findByUserNameContaining(String userName);

}
