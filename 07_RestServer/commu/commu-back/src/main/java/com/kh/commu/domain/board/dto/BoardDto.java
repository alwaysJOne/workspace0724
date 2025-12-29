package com.kh.commu.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.commu.domain.board.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @JsonProperty("board_title")
        @NotBlank(message = "게시글 제목은 필수입니다")
        @Size(min = 2, max = 100, message = "게시글 제목은 2자 이상 100자 이하여야 합니다")
        private String boardTitle;

        @JsonProperty("board_content")
        @NotBlank(message = "게시글 내용은 필수입니다")
        @Size(min = 10, max = 5000, message = "게시글 내용은 10자 이상 5000자 이하여야 합니다")
        private String boardContent;

        @JsonProperty("user_id")
        @NotBlank(message = "작성자 ID는 필수입니다")
        private String userId;

        @JsonProperty("tags")
        private List<String> tags;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {

        @JsonProperty("board_title")
        @Size(min = 2, max = 100, message = "게시글 제목은 2자 이상 100자 이하여야 합니다")
        private String boardTitle;

        @JsonProperty("board_content")
        @Size(min = 10, max = 5000, message = "게시글 내용은 10자 이상 5000자 이하여야 합니다")
        private String boardContent;

        @JsonProperty("tags")
        private List<String> tags;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        @JsonProperty("board_id")
        private Long boardId;

        @JsonProperty("board_title")
        private String boardTitle;

        @JsonProperty("board_content")
        private String boardContent;

        @JsonProperty("origin_name")
        private String originName;

        @JsonProperty("change_name")
        private String changeName;

        @JsonProperty("count")
        private Integer count;

        @JsonProperty("user_id")
        private String userId;

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("create_date")
        private LocalDateTime createDate;

        @JsonProperty("tags")
        private List<String> tags;

        public static Response from(Board board) {
            return Response.builder()
                    .boardId(board.getBoardId())
                    .boardTitle(board.getBoardTitle())
                    .boardContent(board.getBoardContent())
                    .originName(board.getOriginName())
                    .changeName(board.getChangeName())
                    .count(board.getCount())
                    .userId(board.getMember().getUserId())
                    .userName(board.getMember().getUserName())
                    .createDate(board.getCreateDate())
                    .tags(board.getBoardTags().stream()
                            .map(boardTag -> boardTag.getTag().getTagName())
                            .collect(Collectors.toList()))
                    .build();
        }
    }
}

