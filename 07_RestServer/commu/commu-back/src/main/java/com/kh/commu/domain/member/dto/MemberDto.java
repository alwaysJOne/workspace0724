package com.kh.commu.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.commu.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

        @NotBlank(message = "사용자 ID는 필수입니다")
        @Size(min = 4, max = 20, message = "사용자 ID는 4자이상 20자 이하여야합니다.")
        private String userId;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 한다.")
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

