package com.kh.jpa.entity;

import com.kh.jpa.enums.CommonEnums;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder @AllArgsConstructor
@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA스펙상 필수 + 외부 생성 방지
public class Member extends BaseTimeEntity {

    @Id
    @Column(length = 30)
    private String userId;

    @Column(length = 100, nullable = false)
    private String userPwd;

    @Column(length = 15, nullable = false)
    private String userName;

    @Column(length = 255)
    private String email;

    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    @Column(length = 13)
    private String phone;

    @Column(length = 100)
    private String address;

    @Column(length = 1, nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private CommonEnums.Status status = CommonEnums.Status.Y;

    // ==== 연관관계 맵핑 ====
    //cascade : Member객체 상태 자체가 삭제(변경)되면 profile에도 영향을 주겠다.
    //orphanRemoval : Member객체에서 profile의 참조값이 삭제되면 실제 DB에 반영하겠다.
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Profile profile;

    //회원 : 게시글 (1 : N)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL,  orphanRemoval = true)
    @Builder.Default
    private List<Board> boards = new ArrayList<>();

    //회원정보 전체수정 메서드
    public void putUpdate(String userName, String email, Gender gender, Integer age, String phone, String address) {
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    //회원정보 부분수정 메서드
    public void patchUpdate(String userName, String email, Gender gender, Integer age, String phone, String address) {
        if(userName != null)
            this.userName = userName;

        if(email != null)
            this.email = email;

        if(gender != null)
            this.gender = gender;

        if(age != null)
            this.age = age;

        if(phone != null)
            this.phone = phone;

        if(address != null)
            this.address = address;
    }

    public enum Gender {
        M, F
    }
}
