package com.kh.jpa.repository;

import com.kh.jpa.entity.Board;

import java.util.Optional;

public interface BoardRepository {
    Board save(Board board);
    Optional<Board> findById(Long id);
}
