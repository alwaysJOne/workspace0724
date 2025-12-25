package com.kh.commu.global.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JWT 토큰 검증 필터
 * 모든 요청에 대해 Authorization 헤더의 JWT 토큰을 검증하고 인증 처리
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        try {
            if (token != null) {
                // Bearer 형식 확인
                if (!token.startsWith("Bearer ")) {
                    throw new IllegalArgumentException("Bearer 형식이 아닙니다.");
                }

                // Bearer 제거하고 실제 토큰만 추출
                String jwtToken = token.substring(7);

                // 토큰 검증 및 Claims 추출
                Claims claims = tokenProvider.validateToken(jwtToken);

                // 권한 설정
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));

                // UserDetails 생성
                UserDetails userDetails = new User(claims.getSubject(), "", authorities);

                // Authentication 객체 생성 및 SecurityContext에 등록
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, jwtToken, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // 다음 필터로 진행
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            // 토큰 검증 실패 시 401 응답
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":false,\"error\":\"유효하지 않은 토큰입니다.\"}");
        }
    }
}

