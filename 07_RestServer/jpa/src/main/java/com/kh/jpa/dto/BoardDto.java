package com.kh.jpa.dto;

import com.kh.jpa.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
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

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response{
        private Long board_id;
        private String board_title;
        private String board_content;
        private String origin_name;
        private String change_name;
        private Integer count;
        private String user_id;
        private String user_name;
        private LocalDateTime create_date;
        private List<String> tags;

        public static Response of(Long boardId, String boardTitle, String boardContent,
                                  String originName, String changeName, Integer count,
                                  String userId, String userName,
                                  LocalDateTime createDate, List<String> tags){
            return Response.builder()
                    .board_id(boardId)
                    .board_title(boardTitle)
                    .board_content(boardContent)
                    .origin_name(originName)
                    .change_name(changeName)
                    .count(count)
                    .user_id(userId)
                    .user_name(userName)
                    .create_date(createDate)
                    .tags(tags)
                    .build();
        }

        public static Response ofSimple(Long boardId, String boardTitle,
                                        String originName, Integer count,
                                        String userId, LocalDateTime createDate){
            return Response.builder()
                    .board_id(boardId)
                    .board_title(boardTitle)
                    .origin_name(originName)
                    .count(count)
                    .user_id(userId)
                    .create_date(createDate)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Update{
        private String board_title;
        private String board_content;
        private MultipartFile file;
        private List<String> tags;
    }
}
