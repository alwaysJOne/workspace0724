package com.kh.commu.domain.board.dto;

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

        private Long boardId;

        private String boardTitle;

        private String boardContent;

        private String originName;

        private String changeName;

        private Integer count;

        private String userId;

        private String userName;

        private LocalDateTime createDate;

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

