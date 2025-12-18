package com.kh.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@Entity
@Table(name = "board_tag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardTagId;

    //==== 연관관계 ====
    //게시글태그 : 게시글 (N : 1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    public void changeBoard(Board board) {
        this.board = board;
    }

}
