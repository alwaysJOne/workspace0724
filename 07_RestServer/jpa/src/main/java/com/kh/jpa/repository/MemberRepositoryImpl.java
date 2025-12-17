package com.kh.jpa.repository;

import com.kh.jpa.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    @PersistenceContext //EntityManager 주입
    private EntityManager em;

    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public Optional<Member> findById(String userId) {
        return Optional.ofNullable(em.find(Member.class, userId));
        //ofNullable -> 찾는 Member가 있다면 정상적으로 Optional<Member>반환 없다면 Optional<null>반환
    }

    @Override
    public void delete(Member member) {
        em.remove(member);
    }

    @Override
    public List<Member> findByUserNameContaining(String keyword) {
        String jpql = "select m from Member m where m.userName LIKE :keyword";
        return em.createQuery(jpql, Member.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

}
