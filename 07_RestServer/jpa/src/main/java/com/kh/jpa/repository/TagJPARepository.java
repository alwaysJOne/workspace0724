package com.kh.jpa.repository;

import com.kh.jpa.entity.Board;
import com.kh.jpa.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TagJPARepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByTagName(String tagName);
}
