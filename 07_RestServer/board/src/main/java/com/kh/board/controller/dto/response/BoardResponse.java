package com.kh.board.controller.dto.response;

import com.kh.board.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class BoardResponse {

    @Getter
    @Setter
    @Builder
    public static class SimpleDto{
        private String board_id;
        private String member_email;
        private String title;
        private LocalDateTime created_at;

        public static SimpleDto of(Board board){
            return SimpleDto.builder()
                    .board_id(board.getBoardId())
                    .member_email(board.getMemberEmail())
                    .title(board.getTitle())
                    .created_at(board.getCreatedAt())
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class DetailDto{
        private String board_id;
        private String member_email;
        private String title;
        private String contents;
        private String file_name;
        private LocalDateTime created_at;

        public static DetailDto of(Board board){
            return DetailDto.builder()
                    .board_id(board.getBoardId())
                    .member_email(board.getMemberEmail())
                    .title(board.getTitle())
                    .contents(board.getContents())
                    .file_name(board.getFileName())
                    .created_at(board.getCreatedAt())
                    .build();
        }
    }
}
