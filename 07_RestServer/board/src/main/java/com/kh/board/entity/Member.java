package com.kh.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(length = 255)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String nickname;

    @CreationTimestamp
    @Column(name = "create_at",  updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updatedAt;

    /*
    * Member : Board = 1: N의 관계 (한명의 회원은 여러개의 게시글을 작성할 수 있다.)
    * mappedBy : "member" -> Board 엔티티의 "member"필드가 외래키의 주인이다라는 의미
    * cascade : CascadeType.ALL -> 회원 삭제시 관련 게시글을 모두 삭제
    * */
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();
}