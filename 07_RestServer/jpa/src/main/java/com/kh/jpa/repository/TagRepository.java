package com.kh.jpa.repository;

import com.kh.jpa.entity.Tag;

import java.util.Optional;

public interface TagRepository {

    Optional<Tag> findById(Long tagId);
    Optional<Tag> findByTagName(String tagName);
    Tag save(Tag tag);
}
