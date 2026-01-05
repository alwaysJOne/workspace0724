package com.kh.back.domain.like.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostLikeResponseDto {
    private boolean liked;
    private int likeCount;

    public static PostLikeResponseDto of(boolean liked, int likeCount) {
        return PostLikeResponseDto.builder()
                .liked(liked)
                .likeCount(likeCount)
                .build();
    }
}

