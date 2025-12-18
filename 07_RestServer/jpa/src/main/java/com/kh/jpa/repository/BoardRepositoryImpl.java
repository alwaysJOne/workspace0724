package com.kh.jpa.repository;

import com.kh.jpa.entity.Board;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BoardRepositoryImpl implements BoardRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Board save(Board board) {
        em.persist(board);
        return board;
    }
}
