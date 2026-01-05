package com.kh.back.domain.post.dto;

import com.kh.back.domain.post.entity.Category;
import com.kh.back.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponseDto {
    private Long id;
    private Long userId;
    private String userNickname;
    private Category category;
    private String title;
    private String content;
    private int likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostResponseDto from(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .userId(post.getUser().getId())
                .userNickname(post.getUser().getNickname())
                .category(post.getCategory())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Getter
    @Builder
    public static class Summary {
        private Long id;
        private String userNickname;
        private Category category;
        private String title;
        private int likeCount;
        private int commentCount;
        private LocalDateTime createdAt;

        public static Summary from(Post post, int commentCount) {
            return Summary.builder()
                    .id(post.getId())
                    .userNickname(post.getUser().getNickname())
                    .category(post.getCategory())
                    .title(post.getTitle())
                    .likeCount(post.getLikeCount())
                    .commentCount(commentCount)
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}

