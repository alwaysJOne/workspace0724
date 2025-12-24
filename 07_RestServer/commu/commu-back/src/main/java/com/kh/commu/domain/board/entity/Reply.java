package com.kh.commu.domain.board.entity;

import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.global.common.CommonEnums;
import com.kh.commu.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Reply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_no")
    private Long replyNo;

    @Column(name = "reply_content", length = 400, nullable = false)
    private String replyContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 1, nullable = false)
    @Builder.Default
    private CommonEnums.Status status = CommonEnums.Status.Y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Member member;
}

