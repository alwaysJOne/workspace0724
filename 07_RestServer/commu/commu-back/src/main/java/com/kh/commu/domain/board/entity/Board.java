package com.kh.commu.domain.board.entity;

import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.global.common.CommonEnums;
import com.kh.commu.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "board_title", length = 100, nullable = false)
    private String boardTitle;

    @Lob
    @Column(name = "board_content", nullable = false)
    private String boardContent;

    @Column(name = "origin_name", length = 100)
    private String originName;

    @Column(name = "change_name", length = 100)
    private String changeName;

    @Column(name = "count")
    @Builder.Default
    private Integer count = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 1, nullable = false)
    @Builder.Default
    private CommonEnums.Status status = CommonEnums.Status.Y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BoardTag> boardTags = new ArrayList<>();

    public void updateBoard(String boardTitle, String boardContent, String originName, String changeName) {
        if (boardTitle != null) this.boardTitle = boardTitle;
        if (boardContent != null) this.boardContent = boardContent;
        if (originName != null) this.originName = originName;
        if (changeName != null) this.changeName = changeName;
    }

    public void incrementCount() {
        this.count++;
    }

    public void delete() {
        this.status = CommonEnums.Status.N;
    }

    public void addBoardTag(BoardTag boardTag) {
        boardTags.add(boardTag);
        boardTag.setBoard(this);
    }

    public void clearBoardTags() {
        boardTags.clear();
    }
}

