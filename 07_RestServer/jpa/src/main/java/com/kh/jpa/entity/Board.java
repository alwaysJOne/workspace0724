package com.kh.jpa.entity;

import com.kh.jpa.enums.CommonEnums;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder @AllArgsConstructor
@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(length = 100, nullable = false)
    private String boardTitle;

    //@Lob : 대용량 텍스트 데이터(CLOB) 매핑
    @Column(nullable = false)
    @Lob
    private String boardContent;

    @Column(length = 100)
    private String originName;

    @Column(length = 100)
    private String changeName;

    //@Builder.Default : 빌드패턴으로 객체생서시 count값이 없다면 기본값을 사용한다.
    @Builder.Default
    private Integer count = 0;

    @Column(length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CommonEnums.Status status = CommonEnums.Status.Y;

    //==== 연관관계 ====
    //게시글 : 회원 N : 1 -> 연관관계 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_writer", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "board",orphanRemoval = true,cascade = CascadeType.ALL)
    @Builder.Default
    private List<BoardTag> boardTags =  new ArrayList<>();

    public void changeMember(Member member) {
        this.member = member;

        if(!member.getBoards().contains(this))
            member.getBoards().add(this);
    }

    public void changeFile(String originName, String changeName) {
        if(originName != null) this.originName = originName;
        if(changeName != null) this.changeName = changeName;
    }

    public void addTag(Tag tag){
        BoardTag boardTag = BoardTag.builder()
                .tag(tag)
                .build();

        boardTag.changeBoard(this);
        this.boardTags.add(boardTag);
    }
}
