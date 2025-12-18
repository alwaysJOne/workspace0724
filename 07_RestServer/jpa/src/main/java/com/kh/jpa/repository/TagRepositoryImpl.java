package com.kh.jpa.repository;

import com.kh.jpa.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<Tag> findById(Long tagId) {
        return Optional.ofNullable(em.find(Tag.class, tagId));
    }

    @Override
    public Optional<Tag> findByTagName(String tagName) {
        String jpql = "select t from Tag t where t.tagName = :tagName";
        List<Tag> tags = em.createQuery(jpql, Tag.class)
                            .setParameter("tagName", tagName)
                            .getResultList();
        
        // 결과가 없으면 Optional.empty(), 있으면 첫번째 결과를 Optional에 담아서 반환
        return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public Tag save(Tag tag) {
        em.persist(tag);
        return tag;
    }
}
