package com.kh.commu.domain.board.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.commu.domain.board.entity.Board;
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

