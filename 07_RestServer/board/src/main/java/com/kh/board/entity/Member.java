package com.kh.board.entity;

import lombok.Getter;


@Getter
public class Member {
    private String email;
    private String password;
    private String nickname;
    private String createdAt;
    private String updatedAt;
}