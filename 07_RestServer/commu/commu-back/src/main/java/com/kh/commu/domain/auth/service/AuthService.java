package com.kh.commu.domain.auth.service;

import com.kh.commu.domain.auth.dto.AuthDto;
import com.kh.commu.domain.member.entity.Member;
import com.kh.commu.domain.member.repository.MemberRepository;
import com.kh.commu.global.common.CommonEnums;
import com.kh.commu.global.exception.exceptions.InvalidCredentialsException;
import com.kh.commu.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 인증 서비스
 * 로그인, 로그아웃 등 인증 관련 비즈니스 로직 처리
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * 로그인 처리 및 JWT 토큰 발급
     */
    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        // 사용자 조회
        Member member = memberRepository.findByUserIdAndStatus(request.getUserId(), CommonEnums.Status.Y)
                .orElseThrow(InvalidCredentialsException::new);

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.getUserPwd(), member.getUserPwd())) {
            throw new InvalidCredentialsException();
        }

        // JWT 토큰 생성
        String token = tokenProvider.createToken(member.getUserId(), member.getRole().name());

        return AuthDto.LoginResponse.builder()
                .token(token)
                .userId(member.getUserId())
                .userName(member.getUserName())
                .role(member.getRole().name())
                .build();
    }
}

