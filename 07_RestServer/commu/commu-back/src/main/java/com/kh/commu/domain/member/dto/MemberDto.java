package com.kh.commu.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kh.commu.domain.member.entity.Member;
import jakarta.validation.constraints.*;
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
        @Size(min = 4, max = 20, message = "사용자 ID는 4자 이상 20자 이하여야 합니다")
        @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "사용자 ID는 영문, 숫자, 언더스코어만 사용 가능합니다")
        private String userId;

        @JsonProperty("user_pwd")
        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", 
                message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
        private String userPwd;

        @JsonProperty("user_name")
        @NotBlank(message = "사용자 이름은 필수입니다")
        @Size(min = 2, max = 20, message = "사용자 이름은 2자 이상 20자 이하여야 합니다")
        private String userName;

        @JsonProperty("email")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @JsonProperty("gender")
        private Member.Gender gender;

        @JsonProperty("age")
        @Min(value = 14, message = "나이는 14세 이상이어야 합니다")
        @Max(value = 120, message = "나이는 120세 이하여야 합니다")
        private Integer age;

        @JsonProperty("phone")
        @Pattern(regexp = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다")
        private String phone;

        @JsonProperty("address")
        @Size(max = 200, message = "주소는 200자 이하여야 합니다")
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
        @Size(min = 2, max = 20, message = "사용자 이름은 2자 이상 20자 이하여야 합니다")
        private String userName;

        @JsonProperty("email")
        @Email(message = "올바른 이메일 형식이 아닙니다")
        private String email;

        @JsonProperty("gender")
        private Member.Gender gender;

        @JsonProperty("age")
        @Min(value = 14, message = "나이는 14세 이상이어야 합니다")
        @Max(value = 120, message = "나이는 120세 이하여야 합니다")
        private Integer age;

        @JsonProperty("phone")
        @Pattern(regexp = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다")
        private String phone;

        @JsonProperty("address")
        @Size(max = 200, message = "주소는 200자 이하여야 합니다")
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

