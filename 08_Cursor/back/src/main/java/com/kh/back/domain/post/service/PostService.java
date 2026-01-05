package com.kh.back.domain.post.service;

import com.kh.back.domain.post.dto.PostRequestDto;
import com.kh.back.domain.post.dto.PostResponseDto;
import com.kh.back.domain.post.entity.Category;
import com.kh.back.domain.post.entity.Post;
import com.kh.back.domain.post.repository.PostRepository;
import com.kh.back.domain.user.entity.User;
import com.kh.back.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto createPost(String email, PostRequestDto.Create requestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Post post = Post.builder()
                .user(user)
                .category(requestDto.getCategory())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        return PostResponseDto.from(postRepository.save(post));
    }

    public Page<PostResponseDto> getPosts(Pageable pageable, Category category) {
        Page<Post> posts = (category != null) 
                ? postRepository.findByCategory(category, pageable)
                : postRepository.findAll(pageable);
        return posts.map(PostResponseDto::from);
    }

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));
        return PostResponseDto.from(post);
    }

    @Transactional
    public PostResponseDto updatePost(String email, Long id, PostRequestDto.Update requestDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        if (!post.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("게시글 수정 권한이 없습니다.");
        }

        post.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getCategory());
        return PostResponseDto.from(post);
    }

    @Transactional
    public void deletePost(String email, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        if (!post.getUser().getEmail().equals(email)) {
            throw new IllegalArgumentException("게시글 삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }

    public Page<PostResponseDto> getMyPosts(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return postRepository.findByUserId(user.getId(), pageable)
                .map(PostResponseDto::from);
    }
}

