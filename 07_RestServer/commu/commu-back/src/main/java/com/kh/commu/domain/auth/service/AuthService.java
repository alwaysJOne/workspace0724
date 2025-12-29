package com.kh.commu.domain.auth.service;

import com.kh.commu.domain.auth.dto.AuthDto;
import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.domain.member.repository.MemberRepository;
import com.kh.commu.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    /**
     * 로그인
     */
    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        // 1. 회원 조회
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다"));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(request.getUserPwd(), member.getUserPwd())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        // 3. JWT 토큰 생성 (role 포함)
        String role = member.getRole().name();
        String token = jwtTokenProvider.generateToken(member.getUserId(), role);

        // 4. 응답 생성
        return AuthDto.LoginResponse.builder()
                .token(token)
                .userId(member.getUserId())
                .userName(member.getUserName())
                .role(role)
                .build();
    }
}

