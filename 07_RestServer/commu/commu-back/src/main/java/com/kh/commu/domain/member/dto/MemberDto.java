package com.kh.commu.domain.member.dto;

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

        @NotBlank(message = "사용자 ID는 필수입니다")
        @Size(min = 4, max = 30, message = "사용자 ID는 4자 이상 30자 이하여야 합니다")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 영문자와 숫자만 사용 가능합니다")
        private String userId;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하여야 합니다")
        private String userPwd;

        @NotBlank(message = "사용자 이름은 필수입니다")
        @Size(min = 2, max = 15, message = "사용자 이름은 2자 이상 15자 이하여야 합니다")
        private String userName;

        @Email(message = "이메일 형식이 올바르지 않습니다")
        @Size(max = 255, message = "이메일은 255자 이하여야 합니다")
        private String email;

        private Member.Gender gender;

        @Min(value = 0, message = "나이는 0 이상이어야 합니다")
        @Max(value = 150, message = "나이는 150 이하여야 합니다")
        private Integer age;

        @Pattern(regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$", 
                message = "전화번호 형식이 올바르지 않습니다 (예: 010-1234-5678)")
        private String phone;

        @Size(max = 100, message = "주소는 100자 이하여야 합니다")
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

        @Size(min = 2, max = 15, message = "사용자 이름은 2자 이상 15자 이하여야 합니다")
        private String userName;

        @Email(message = "이메일 형식이 올바르지 않습니다")
        @Size(max = 255, message = "이메일은 255자 이하여야 합니다")
        private String email;

        private Member.Gender gender;

        @Min(value = 0, message = "나이는 0 이상이어야 합니다")
        @Max(value = 150, message = "나이는 150 이하여야 합니다")
        private Integer age;

        @Pattern(regexp = "^(010|011|016|017|018|019)-\\d{3,4}-\\d{4}$", 
                message = "전화번호 형식이 올바르지 않습니다 (예: 010-1234-5678)")
        private String phone;

        @Size(max = 100, message = "주소는 100자 이하여야 합니다")
        private String address;
    }

    @Getter
    @AllArgsConstructor
    @Builder
    public static class Response {

        private String userId;

        private String userName;

        private String email;

        private Member.Gender gender;

        private Integer age;

        private String phone;

        private String address;

        private LocalDateTime createDate;

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

