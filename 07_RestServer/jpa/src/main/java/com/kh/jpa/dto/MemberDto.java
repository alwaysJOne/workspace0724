package com.kh.jpa.dto;

import com.kh.jpa.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberDto {

    @Getter @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create{

        private String user_id;
        private String user_pwd;
        private String user_name;
        private String email;
        private Member.Gender gender;
        private Integer age;
        private String phone;
        private String address;

        public Member toEntity() {
            return Member.builder().
                    userId(user_id).
                    userPwd(user_pwd).
                    userName(user_name).
                    email(email).
                    gender(gender).
                    age(age).
                    phone(phone).
                    address(address).
                    build();
        }
    }
}
