package com.kh.back.domain.like.service;

import com.kh.back.domain.like.dto.PostLikeResponseDto;
import com.kh.back.domain.like.entity.PostLike;
import com.kh.back.domain.like.repository.PostLikeRepository;
import com.kh.back.domain.post.entity.Post;
import com.kh.back.domain.post.repository.PostRepository;
import com.kh.back.domain.user.entity.User;
import com.kh.back.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostLikeResponseDto toggleLike(String email, Long postId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        boolean liked;
        if (postLikeRepository.existsByPostIdAndUserId(postId, user.getId())) {
            PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId())
                    .orElseThrow(() -> new IllegalArgumentException("좋아요를 찾을 수 없습니다."));
            postLikeRepository.delete(postLike);
            post.decrementLikeCount();
            liked = false;
        } else {
            PostLike postLike = PostLike.builder()
                    .post(post)
                    .user(user)
                    .build();
            postLikeRepository.save(postLike);
            post.incrementLikeCount();
            liked = true;
        }

        return PostLikeResponseDto.of(liked, post.getLikeCount());
    }

    public boolean isLiked(String email, Long postId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return postLikeRepository.existsByPostIdAndUserId(postId, user.getId());
    }
}

