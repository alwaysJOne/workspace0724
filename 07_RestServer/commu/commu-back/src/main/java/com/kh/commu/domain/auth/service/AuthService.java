package com.kh.commu.domain.auth.service;

import com.kh.commu.domain.auth.dto.AuthDto;
import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.domain.member.repository.MemberRepository;
import com.kh.commu.global.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        //1. 회원조회
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 일치하지 않습니다."));

        //2. 비밀번호 확인
        if(!passwordEncoder.matches(request.getUserPwd(), member.getUserPwd())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String userId = member.getUserId();
        String role = member.getRole().name();
        String token = jwtTokenProvider.generateToken(userId, role);

        return AuthDto.LoginResponse.builder()
                .userId(userId)
                .userName(member.getUserName())
                .role(role)
                .token(token)
                .build();
    }
}
