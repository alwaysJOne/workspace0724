package com.kh.jpa.entity;

import com.kh.jpa.enums.CommonEnums;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public enum Gender {
        M, F
    }
}
