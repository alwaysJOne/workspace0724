package com.kh.jpa.dto;

import com.kh.jpa.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BoardDto {

    @Getter
    @AllArgsConstructor
    public static class Create{
        private String board_title;
        private String board_content;
        private String user_id;
        private MultipartFile file;
        private List<String> tags;

        public Board toEntity(){
            return Board.builder()
                    .boardTitle(board_title)
                    .boardContent(board_content)
                    .build();
        }
    }
}
