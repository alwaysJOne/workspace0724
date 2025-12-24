package com.kh.commu.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.commu.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberDto {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        @JsonProperty("user_id")
        @NotBlank(message = "사용자 ID는 필수입니다")
        private String userId;

        @JsonProperty("user_pwd")
        @NotBlank(message = "비밀번호는 필수입니다")
        private String userPwd;

        @JsonProperty("user_name")
        @NotBlank(message = "사용자 이름은 필수입니다")
        private String userName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("gender")
        private Member.Gender gender;

        @JsonProperty("age")
        private Integer age;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private String address;

        public Member toEntity() {
            return Member.builder()
                    .userId(userId)
                    .userPwd(userPwd)
                    .userName(userName)
                    .email(email)
                    .gender(gender)
                    .age(age)
                    .phone(phone)
                    .address(address)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UpdateRequest {

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("gender")
        private Member.Gender gender;

        @JsonProperty("age")
        private Integer age;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private String address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {

        @JsonProperty("user_id")
        private String userId;

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("email")
        private String email;

        @JsonProperty("gender")
        private Member.Gender gender;

        @JsonProperty("age")
        private Integer age;

        @JsonProperty("phone")
        private String phone;

        @JsonProperty("address")
        private String address;

        @JsonProperty("create_date")
        private LocalDateTime createDate;

        @JsonProperty("modify_date")
        private LocalDateTime modifyDate;

        public static Response from(Member member) {
            return Response.builder()
                    .userId(member.getUserId())
                    .userName(member.getUserName())
                    .email(member.getEmail())
                    .gender(member.getGender())
                    .age(member.getAge())
                    .phone(member.getPhone())
                    .address(member.getAddress())
                    .createDate(member.getCreateDate())
                    .modifyDate(member.getModifyDate())
                    .build();
        }
    }
}

