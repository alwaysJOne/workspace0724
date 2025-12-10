package com.kh.board.controller.dto.request;

import com.kh.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

public class BoardRequest {

    @Getter
    @Setter
    public static class CreateDto{
        private String title;
        private String user_id;
        private String contents;
        private String file_name;

        public Board toEntity(){
            return Board.builder()
                    .title(title)
//                    .memberEmail(user_id)
                    .contents(contents)
                    .fileName(file_name)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateDto{

        private Long board_id;
        private String title;
        private String user_id;
        private String contents;
        private String origin_name;

        public Board toEntity(){
            return Board.builder()
                    .boardId(board_id)
                    .title(title)
//                    .memberEmail(user_id)
                    .contents(contents)
                    .fileName(origin_name)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class PatchDto{
        private Long board_id;
        private String title;
        private String contents;
        private String origin_name;
    }
}
