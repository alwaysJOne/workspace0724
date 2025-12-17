package com.kh.jpa.repository;

import com.kh.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext //EntityManager 주입
    private EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }
}
