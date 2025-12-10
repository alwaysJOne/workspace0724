package com.kh.board.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Board {
    private Long boardId;
    private String title;
    private String contents;
    private String fileName;
    private String memberEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void patchUpdate(String title, String contents, String fileName) {
        if(title != null) {
            this.title = title;
        }

        if(contents != null) {
            this.contents = contents;
        }

        if(fileName != null) {
            this.fileName = fileName;
        }
    }
}
