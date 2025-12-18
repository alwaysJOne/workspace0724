package com.kh.jpa.repository;

import com.kh.jpa.entity.Board;
import com.kh.jpa.enums.CommonEnums;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public Optional<Board> findById(Long id) {
        return Optional.ofNullable(em.find(Board.class, id));
    }

    @Override
    public Page<Board> findByStatus(CommonEnums.Status status, Pageable pageable) {

        String query = "select b from Board b where b.status = :status ";
        List<Board> boards = em.createQuery(query, Board.class)
                            .setParameter("status", status)
                            .setFirstResult((int) pageable.getOffset()) //시작위치
                            .setMaxResults(pageable.getPageSize()) //몇개를 가져올 것인가
                            .getResultList();

        String countQuery = "select count(b) from Board b where b.status = :status";
        Long totalCount = em.createQuery(countQuery, Long.class)
                        .setParameter("status", status)
                        .getSingleResult();

        //PageImpl : 페이징 정보와 함께 반환
        return new PageImpl<>(boards, pageable, totalCount);
    }
}
