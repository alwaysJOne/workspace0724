package com.kh.commu.domain.member.entity;

import com.kh.commu.global.common.CommonEnums;
import com.kh.commu.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id
    @Column(name = "user_id", length = 30)
    private String userId;

    @Column(name = "user_pwd", length = 100, nullable = false)
    private String userPwd;

    @Column(name = "user_name", length = 15, nullable = false)
    private String userName;

    @Column(name = "email", length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 1)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phone", length = 13)
    private String phone;

    @Column(name = "address", length = 100)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 1, nullable = false)
    @Builder.Default
    private CommonEnums.Status status = CommonEnums.Status.Y;

    public enum Gender {
        M, F
    }

    public void updateMemberInfo(String userName, String email, Gender gender, Integer age, String phone, String address) {
        if (userName != null) this.userName = userName;
        if (email != null) this.email = email;
        if (gender != null) this.gender = gender;
        if (age != null) this.age = age;
        if (phone != null) this.phone = phone;
        if (address != null) this.address = address;
    }

    public void delete() {
        this.status = CommonEnums.Status.N;
    }
}

