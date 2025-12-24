package com.kh.commu.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "profile_image", length = 100)
    private String profileImage;

    @Column(name = "intro", length = 300)
    private String intro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Member member;
}

