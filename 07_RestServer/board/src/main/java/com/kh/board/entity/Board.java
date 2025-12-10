package com.kh.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "BOARD") //DB의 BOARD테이블과 매핑
public class Board {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(nullable = false, length = 255) //not null
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @CreationTimestamp
    @Column(name = "create_at",  updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    //Board : Member = N : 1 관계(다수의 게시글은 하나의 회원에 속한)
    //LAZY : 실제 member정보가 필요할 때까지 조회를 지연
    //Board테이블에 member_email이라는 컬럼을 만들어서 해당 컬럼값으로 Member테이블의 pk를 참고해라
    @JoinColumn(name = "member_email", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

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
