package com.kh.back.domain.post.dto;

import com.kh.back.domain.post.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    public static class Create {
        @NotNull(message = "카테고리는 필수입니다.")
        private Category category;

        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        private String content;
    }

    @Getter
    @NoArgsConstructor
    public static class Update {
        @NotNull(message = "카테고리는 필수입니다.")
        private Category category;

        @NotBlank(message = "제목은 필수입니다.")
        private String title;

        @NotBlank(message = "내용은 필수입니다.")
        private String content;
    }
}

