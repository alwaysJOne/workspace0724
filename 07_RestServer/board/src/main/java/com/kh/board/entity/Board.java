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
    private String boardId;
    private String title;
    private String contents;
    private String fileName;
    private String memberEmail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
