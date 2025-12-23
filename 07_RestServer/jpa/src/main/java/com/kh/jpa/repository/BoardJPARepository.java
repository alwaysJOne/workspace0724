package com.kh.jpa.repository;

import com.kh.jpa.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardJPARepository extends JpaRepository<Board, Long> {
}
