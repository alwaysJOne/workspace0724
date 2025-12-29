# JWT 로그인 기능 구현 가이드

commu-back 프로젝트에 JWT 기반 로그인 기능을 구현한 과정을 정리합니다.

---

## 📋 구현 개요

**목표**: 프론트엔드(commu-front)와 연동 가능한 JWT 기반 인증 시스템 구축  
**인증 방식**: JWT 토큰 (Refresh Token 없이 Access Token만 사용)  
**토큰 유효기간**: 24시간

---

## 🔧 구현 순서

### 1단계: 의존성 추가

**파일**: `build.gradle`

```gradle
dependencies {
    // 기존 의존성...
    implementation 'org.springframework.boot:spring-boot-starter-security'
    
    // JWT
    implementation 'io.jsonwebtoken:jjwt-api:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.12.3'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.12.3'
}
```

**이유**:
- `spring-boot-starter-security`: Spring Security 사용
- `jjwt`: JWT 토큰 생성/검증을 위한 라이브러리
- 버전 0.12.3은 최신 안정 버전으로 Java 17과 호환

---

### 2단계: JWT 설정 추가

**파일**: `src/main/resources/application.yaml`

```yaml
# JWT 설정
jwt:
  secret: commu-jwt-secret-key-for-token-generation-minimum-256-bits-required-for-HS256-algorithm
  expiration: 86400000  # 24시간 (밀리초)
```

**설명**:
- `secret`: JWT 서명에 사용되는 비밀키 (최소 256비트 필요)
- `expiration`: 토큰 만료 시간 (24시간 = 86,400,000 밀리초)

**주의**: 실제 운영 환경에서는 secret을 환경 변수로 관리해야 합니다.

---

### 3단계: JWT 유틸리티 클래스 생성

**파일**: `global/security/JwtTokenProvider.java`

```java
@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long expiration;

    // 생성자에서 secret과 expiration 주입
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiration = expiration;
    }

    // JWT 토큰 생성
    public String generateToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(userId)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 사용자 ID 추출
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

**역할**:
- JWT 토큰 생성, 검증, 정보 추출
- HS256 알고리즘 사용 (HMAC-SHA256)

---

### 4단계: Auth 도메인 구조 생성

#### 4-1. DTO 생성

**파일**: `domain/auth/dto/AuthDto.java`

```java
public class AuthDto {
    
    // 로그인 요청
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        @JsonProperty("user_id")
        @NotBlank(message = "사용자 ID는 필수입니다")
        private String userId;

        @JsonProperty("user_pwd")
        @NotBlank(message = "비밀번호는 필수입니다")
        private String userPwd;
    }

    // 로그인 응답
    @Getter
    @AllArgsConstructor
    @Builder
    public static class LoginResponse {
        @JsonProperty("token")
        private String token;

        @JsonProperty("user_id")
        private String userId;

        @JsonProperty("user_name")
        private String userName;

        @JsonProperty("role")
        private String role;
    }
}
```

**프론트엔드 연동**:
```javascript
// 프론트엔드 요청
{
  "user_id": "user01",
  "user_pwd": "password123"
}

// 백엔드 응답
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "user_id": "user01",
  "user_name": "홍길동",
  "role": "USER"
}
```

#### 4-2. Service 생성

**파일**: `domain/auth/service/AuthService.java`

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthDto.LoginResponse login(AuthDto.LoginRequest request) {
        // 1. 회원 조회
        Member member = memberRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다"));

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(request.getUserPwd(), member.getUserPwd())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다");
        }

        // 3. JWT 토큰 생성
        String token = jwtTokenProvider.generateToken(member.getUserId());

        // 4. 응답 생성
        return AuthDto.LoginResponse.builder()
                .token(token)
                .userId(member.getUserId())
                .userName(member.getUserName())
                .role("USER")
                .build();
    }
}
```

**로직 설명**:
1. DB에서 사용자 조회 (없으면 예외)
2. BCrypt로 비밀번호 검증
3. JWT 토큰 생성
4. 사용자 정보와 함께 응답

#### 4-3. Controller 생성

**파일**: `domain/auth/controller/AuthController.java`

```java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthDto.LoginResponse> login(
            @Valid @RequestBody AuthDto.LoginRequest request) {
        AuthDto.LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
```

**엔드포인트**: `POST /api/auth/login`

---

### 5단계: JWT 필터 및 Security 설정

#### 5-1. JWT 인증 필터

**파일**: `global/security/JwtAuthenticationFilter.java`

```java
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 1. Request Header에서 JWT 토큰 추출
            String token = getJwtFromRequest(request);

            // 2. 토큰 검증
            if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
                // 3. 토큰에서 사용자 ID 추출
                String userId = jwtTokenProvider.getUserIdFromToken(token);

                // 4. SecurityContext에 인증 정보 저장
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("JWT 인증 실패", e);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
```

**동작 흐름**:
1. 모든 요청마다 실행 (`OncePerRequestFilter`)
2. `Authorization: Bearer {token}` 헤더에서 토큰 추출
3. 토큰 검증 (만료, 서명 확인)
4. 검증 성공 시 SecurityContext에 인증 정보 저장

#### 5-2. Security 설정

**파일**: `global/config/SecurityConfig.java`

```java
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CSRF 비활성화 (JWT 사용)
                .csrf(AbstractHttpConfigurer::disable)
                
                // CORS 설정
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                
                // 세션 미사용 (Stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                
                // 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 가능
                        .requestMatchers("/api/auth/**", "/api/member").permitAll()
                        // 나머지는 인증 필요
                        .anyRequest().authenticated())
                
                // JWT 필터 추가
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

**주요 설정**:
- **CSRF 비활성화**: REST API는 Stateless이므로 불필요
- **CORS 허용**: 프론트엔드 도메인 허용 (localhost:5173, 3000)
- **세션 미사용**: JWT 사용으로 세션 불필요
- **권한 설정**: 
  - `/api/auth/**`, `/api/member` (POST): 인증 불필요
  - 나머지: 인증 필요
- **BCrypt**: 비밀번호 암호화

---

### 6단계: Member 서비스에 비밀번호 암호화 추가

**파일**: `domain/member/service/MemberService.java`

```java
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;  // 추가

    @Transactional
    public String createMember(MemberDto.Request request) {
        // 중복 체크
        if (memberRepository.existsById(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 회원 ID입니다");
        }

        // 비밀번호 암호화
        Member member = Member.builder()
                .userId(request.getUserId())
                .userPwd(passwordEncoder.encode(request.getUserPwd()))  // 암호화
                .userName(request.getUserName())
                .email(request.getEmail())
                .gender(request.getGender())
                .age(request.getAge())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();

        memberRepository.save(member);
        return member.getUserId();
    }
}
```

**변경사항**:
- `PasswordEncoder` 주입
- 회원가입 시 비밀번호 암호화 (`BCryptPasswordEncoder`)
- 중복 체크 로직 추가

---

### 7단계: 전역 예외 처리 강화

**파일**: `global/exception/GlobalExceptionHandler.java`

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 인증 실패 처리 추가
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        ErrorResponse response = ErrorResponse.of("인증에 실패했습니다");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // IllegalArgumentException 처리 추가
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse response = ErrorResponse.of(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    // 기존 검증 예외 처리들...
}
```

**추가된 예외 처리**:
- 인증 실패 (401 Unauthorized)
- 비즈니스 로직 예외 (400 Bad Request)

---

## 📁 최종 파일 구조

```
commu-back/
├── src/main/java/com/kh/commu/
│   ├── domain/
│   │   ├── auth/                    # 🆕 Auth 도메인
│   │   │   ├── controller/
│   │   │   │   └── AuthController.java
│   │   │   ├── dto/
│   │   │   │   └── AuthDto.java
│   │   │   └── service/
│   │   │       └── AuthService.java
│   │   └── member/
│   │       └── service/
│   │           └── MemberService.java  # 🔄 비밀번호 암호화 추가
│   └── global/
│       ├── config/
│       │   └── SecurityConfig.java     # 🆕 Security 설정
│       ├── security/                    # 🆕 Security 패키지
│       │   ├── JwtTokenProvider.java
│       │   └── JwtAuthenticationFilter.java
│       └── exception/
│           └── GlobalExceptionHandler.java  # 🔄 예외 처리 강화
└── src/main/resources/
    └── application.yaml  # 🔄 JWT 설정 추가
```

---

## 🔄 API 흐름

### 1. 회원가입 흐름

```
Client → POST /api/member
{
  "user_id": "user01",
  "user_pwd": "password123",
  "user_name": "홍길동"
}
  ↓
MemberController → MemberService
  ↓
비밀번호 BCrypt 암호화
  ↓
DB 저장 (암호화된 비밀번호)
  ↓
Response: "user01"
```

### 2. 로그인 흐름

```
Client → POST /api/auth/login
{
  "user_id": "user01",
  "user_pwd": "password123"
}
  ↓
AuthController → AuthService
  ↓
1. DB에서 회원 조회
2. 비밀번호 검증 (BCrypt)
3. JWT 토큰 생성
  ↓
Response:
{
  "token": "eyJhbGc...",
  "user_id": "user01",
  "user_name": "홍길동",
  "role": "USER"
}
```

### 3. 인증이 필요한 API 호출 흐름

```
Client → GET /api/board
Headers: Authorization: Bearer eyJhbGc...
  ↓
JwtAuthenticationFilter
  ↓
1. 토큰 추출
2. 토큰 검증 (만료, 서명)
3. userId 추출
4. SecurityContext에 인증 정보 저장
  ↓
BoardController → BoardService
  ↓
Response: 게시글 목록
```

---

## 🧪 테스트 방법

### 1. 회원가입

```bash
POST http://localhost:8888/api/member
Content-Type: application/json

{
  "user_id": "testuser",
  "user_pwd": "Password123!",
  "user_name": "테스트유저",
  "email": "test@example.com"
}
```

**예상 응답**: `"testuser"`

### 2. 로그인

```bash
POST http://localhost:8888/api/auth/login
Content-Type: application/json

{
  "user_id": "testuser",
  "user_pwd": "Password123!"
}
```

**예상 응답**:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTcwMzgwODAwMCwiZXhwIjoxNzAzODk0NDAwfQ...",
  "user_id": "testuser",
  "user_name": "테스트유저",
  "role": "USER"
}
```

### 3. 인증이 필요한 API 호출

```bash
GET http://localhost:8888/api/board
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

**예상 응답**: 게시글 목록

### 4. 인증 실패 케이스

```bash
# 토큰 없이 호출
GET http://localhost:8888/api/board
```

**예상 응답**: 401 Unauthorized

---

## 🔐 보안 고려사항

### 1. 비밀번호 암호화
- BCrypt 사용 (단방향 해시)
- Salt 자동 생성
- 같은 비밀번호도 다른 해시값 생성

### 2. JWT Secret 관리
- 최소 256비트 길이
- 운영 환경에서는 환경 변수로 관리
- 정기적 변경 권장

### 3. CORS 설정
- 허용된 Origin만 접근 가능
- Credentials 허용 설정

### 4. 토큰 만료
- 24시간 유효
- 만료 시 재로그인 필요

### 5. HTTPS 사용 권장
- 토큰 탈취 방지
- 운영 환경에서 필수

---

## 🚨 주의사항

### 1. 토큰 저장
- 프론트엔드에서 LocalStorage에 저장 (현재 구현)
- XSS 공격 가능성 존재
- 민감한 데이터는 추가 암호화 권장

### 2. Refresh Token 없음
- Access Token만 사용
- 24시간 후 재로그인 필요
- 필요 시 Refresh Token 추가 구현

### 3. 로그아웃
- 서버 측에서 토큰 무효화 없음
- 클라이언트에서 토큰 삭제로만 처리
- 완전한 로그아웃을 위해서는 토큰 블랙리스트 구현 필요

### 4. 권한 관리
- 현재는 단순히 USER 역할만 존재
- 필요 시 ADMIN, MANAGER 등 추가 가능

---

## 📊 프론트엔드 연동

### 로그인 후 토큰 저장 (zustand)

```javascript
// authStore.js
const useAuthStore = create(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      
      login: (loginData) => {
        const { token, user_id, user_name, role } = loginData;
        set({
          token,
          user: { user_id, user_name, role },
          isAuthenticated: true,
        });
      },
    }),
    { name: 'auth-storage' }
  )
);
```

### axios 인터셉터로 토큰 자동 추가

```javascript
// axios.js
api.interceptors.request.use((config) => {
  const token = useAuthStore.getState().token;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
```

---

## 🎯 구현 완료 체크리스트

- [x] JWT 의존성 추가
- [x] JWT 설정 파일 추가
- [x] JWT 유틸리티 클래스 생성
- [x] Auth 도메인 구조 생성
- [x] JWT 필터 및 Security 설정
- [x] Member 서비스 비밀번호 암호화
- [x] 전역 예외 처리 강화
- [x] 문서화 작성

---

---

## 🎯 Role 기반 인증 + 인가 구현

### 8단계: Member 엔티티에 Role 추가

**파일**: `domain/member/entity/Member.java`

```java
@Enumerated(EnumType.STRING)
@Column(name = "role", length = 10, nullable = false)
@Builder.Default
private Role role = Role.USER;

public enum Role {
    USER,   // 일반 사용자
    ADMIN   // 관리자
}
```

**DB 스키마 변경**:
```sql
ALTER TABLE member 
ADD COLUMN role VARCHAR(10) NOT NULL DEFAULT 'USER'
AFTER status;
```

**특징**:
- 회원가입 시 자동으로 `USER` role 할당
- DB에는 `"USER"`, `"ADMIN"` 문자열로 저장

---

### 9단계: JWT에 role 정보 포함

**파일**: `global/security/JwtTokenProvider.java`

```java
// JWT 토큰 생성 (role 포함)
public String generateToken(String userId, String role) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expiration);

    return Jwts.builder()
            .subject(userId)
            .claim("role", role)  // role 정보 추가
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact();
}

// 토큰에서 role 추출
public String getRoleFromToken(String token) {
    Claims claims = getClaims(token);
    return claims.get("role", String.class);
}
```

**JWT 페이로드 예시**:
```json
{
  "sub": "admin",
  "role": "ADMIN",
  "iat": 1735459200,
  "exp": 1735545600
}
```

**AuthService 수정**:
```java
// 3. JWT 토큰 생성 (role 포함)
String role = member.getRole().name();
String token = jwtTokenProvider.generateToken(member.getUserId(), role);

// 4. 응답 생성
return AuthDto.LoginResponse.builder()
        .token(token)
        .userId(member.getUserId())
        .userName(member.getUserName())
        .role(role)  // DB의 실제 role 반환
        .build();
```

---

### 10단계: JwtAuthenticationFilter에서 권한 설정

**파일**: `global/security/JwtAuthenticationFilter.java`

```java
// 토큰에서 userId와 role 추출
String userId = jwtTokenProvider.getUserIdFromToken(token);
String role = jwtTokenProvider.getRoleFromToken(token);

// Spring Security의 GrantedAuthority에 등록 (ROLE_ 접두사 추가)
SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userId, null, Collections.singletonList(authority));

SecurityContextHolder.getContext().setAuthentication(authentication);
```

**Spring Security 컨벤션**:
- `ROLE_USER`, `ROLE_ADMIN` 형태로 저장
- `hasRole("USER")`는 내부적으로 `ROLE_USER`를 확인

---

### 11단계: SecurityConfig - role 기반 접근 제어

**파일**: `global/config/SecurityConfig.java`

```java
.authorizeHttpRequests(auth -> auth
    // 인증 없이 접근 가능
    .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
    .requestMatchers(HttpMethod.POST, "/api/members").permitAll()
    
    // 관리자 전용 API
    .requestMatchers(HttpMethod.GET, "/api/members").hasRole("ADMIN")
    .requestMatchers(HttpMethod.GET, "/api/members/search").hasRole("ADMIN")
    .requestMatchers(HttpMethod.DELETE, "/api/members/**").hasRole("ADMIN")
    
    // 본인 또는 관리자만 접근 가능
    .requestMatchers(HttpMethod.GET, "/api/members/**").authenticated()
    .requestMatchers(HttpMethod.PUT, "/api/members/**").authenticated()
    
    // 나머지는 인증 필요
    .anyRequest().authenticated()
)
```

**권한 체크 순서 주의**:
- 구체적인 규칙을 먼저 배치
- `/**` 같은 광범위한 패턴은 나중에

---

### 12단계: 관리자 계정 생성

**SQL**:
```sql
INSERT INTO member (
    user_id, user_pwd, user_name, email, status, role, create_date, modify_date
) VALUES (
    'admin',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',  -- "admin1234"
    '관리자',
    'admin@commu.com',
    'Y',
    'ADMIN',
    NOW(),
    NOW()
);
```

**계정 정보**:
- ID: `admin`
- 비밀번호: `admin1234`

---

## 📋 API 권한 매트릭스

### Auth API

| API | 인증 | 권한 | 설명 |
|-----|------|------|------|
| `POST /api/auth/login` | ❌ | 모두 | 로그인 |

### Member API

| API | 인증 | 권한 | 설명 |
|-----|------|------|------|
| `POST /api/members` | ❌ | 모두 | 회원가입 (role=USER) |
| `GET /api/members` | ✅ | ADMIN | 전체 회원 목록 조회 |
| `GET /api/members/search` | ✅ | ADMIN | 회원 검색 |
| `GET /api/members/{userId}` | ✅ | USER, ADMIN | 회원 조회 |
| `PUT /api/members/{userId}` | ✅ | USER, ADMIN | 회원 수정 |
| `DELETE /api/members/{userId}` | ✅ | ADMIN | 회원 삭제 |

### Board API

| API | 인증 | 권한 | 설명 |
|-----|------|------|------|
| `POST /api/board` | ✅ | USER, ADMIN | 게시글 작성 |
| `GET /api/board` | ✅ | USER, ADMIN | 게시글 목록 |

---

## ⚡ 성능 최적화: JWT 토큰 파싱

### 문제점: 중복 파싱

초기 구현에서는 동일한 토큰을 **3번 파싱**하는 비효율이 있었습니다.

```java
// 기존 (비효율적)
if (jwtTokenProvider.validateToken(token)) {           // 1번 파싱
    String userId = jwtTokenProvider.getUserIdFromToken(token);  // 2번 파싱
    String role = jwtTokenProvider.getRoleFromToken(token);      // 3번 파싱
}
```

### 해결책: Optional<Claims> 반환

**JwtTokenProvider 개선**:
```java
/**
 * 토큰 유효성 검증 및 Claims 반환
 * 검증 성공 시 Claims를 포함한 Optional 반환, 실패 시 Optional.empty() 반환
 */
public Optional<Claims> validateToken(String token) {
    try {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return Optional.of(claims);
    } catch (Exception e) {
        return Optional.empty();
    }
}
```

**JwtAuthenticationFilter 개선**:
```java
// 개선 (1번 파싱)
if (StringUtils.hasText(token)) {
    Optional<Claims> claimsOpt = jwtTokenProvider.validateToken(token);
    
    if (claimsOpt.isPresent()) {
        Claims claims = claimsOpt.get();
        String userId = claims.getSubject();
        String role = claims.get("role", String.class);
        
        // SecurityContext 설정
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, null, 
                    Collections.singletonList(authority));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
```

**효과**:
- 토큰 파싱 횟수: **3회 → 1회** (66% 감소)
- 검증과 데이터 추출을 한 번에 처리
- `Optional` 사용으로 안전한 null 처리

### 왜 Optional을 사용했나?

**대안 1: 예외 던지기**
```java
public Claims validateToken(String token) throws JwtException {
    return Jwts.parser()...
}
```
단점: 예외 처리 코드가 복잡해짐

**대안 2: null 반환**
```java
public Claims validateToken(String token) {
    try {
        return Jwts.parser()...
    } catch (Exception e) {
        return null;  // 위험!
    }
}
```
단점: NPE 위험

**선택: Optional 반환**
```java
public Optional<Claims> validateToken(String token) {
    try {
        return Optional.of(claims);
    } catch (Exception e) {
        return Optional.empty();
    }
}
```
장점:
- 명시적인 "값이 없을 수 있음" 표현
- NPE 방지
- 함수형 프로그래밍 스타일
- Java 8+ 표준

---

## 🔄 최종 인증/인가 흐름

```
1. 클라이언트: 로그인 요청 (ID/PW)
   ↓
2. AuthService: ID/PW 검증 + DB에서 role 조회
   ↓
3. JwtTokenProvider: JWT 생성 (userId + role 포함)
   ↓
4. 클라이언트: JWT 토큰 저장
   ↓
5. 클라이언트: API 요청 (Authorization: Bearer {token})
   ↓
6. JwtAuthenticationFilter: 
   - JWT 검증 및 Claims 추출 (1번 파싱)
   - userId, role 추출
   - SecurityContext에 Authentication 저장 (ROLE_USER or ROLE_ADMIN)
   ↓
7. SecurityConfig:
   - URL + HTTP Method + Role 확인
   - hasRole("ADMIN") 등으로 권한 체크
   ↓
8. 컨트롤러: 비즈니스 로직 실행
   ↓
9. 응답 반환
```

---

## 🧪 테스트 시나리오 (인증 + 인가)

### 1. 일반 회원 (USER)

**회원가입**:
```http
POST /api/members
{
  "user_id": "user01",
  "user_pwd": "password123",
  "user_name": "홍길동"
}
```
✅ role은 자동으로 `USER`로 설정

**로그인**:
```http
POST /api/auth/login
{
  "user_id": "user01",
  "user_pwd": "password123"
}
```
응답:
```json
{
  "token": "eyJ...",
  "user_id": "user01",
  "user_name": "홍길동",
  "role": "USER"
}
```

**회원 목록 조회 시도**:
```http
GET /api/members
Authorization: Bearer {user_token}
```
❌ **403 Forbidden** - ADMIN 권한 필요

### 2. 관리자 (ADMIN)

**로그인**:
```http
POST /api/auth/login
{
  "user_id": "admin",
  "user_pwd": "admin1234"
}
```
응답:
```json
{
  "token": "eyJ...",
  "user_id": "admin",
  "user_name": "관리자",
  "role": "ADMIN"
}
```

**회원 목록 조회**:
```http
GET /api/members
Authorization: Bearer {admin_token}
```
✅ **200 OK** - 전체 회원 목록 반환

**회원 삭제**:
```http
DELETE /api/members/user01
Authorization: Bearer {admin_token}
```
✅ **200 OK** - 회원 삭제 완료

---

## 💡 실무 적용 팁

### 1. 본인 확인 로직

현재는 URL 레벨 권한만 체크합니다. 실무에서는 **본인 확인**이 필요합니다.

```java
@PutMapping("/{userId}")
public ResponseEntity<MemberDto.Response> updateMember(
        @PathVariable String userId,
        @Valid @RequestBody MemberDto.UpdateRequest request,
        Authentication authentication) {
    
    String currentUserId = authentication.getName();
    
    // 본인 또는 관리자만 수정 가능
    if (!currentUserId.equals(userId) && 
        !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
        throw new AccessDeniedException("본인 정보만 수정할 수 있습니다");
    }
    
    return ResponseEntity.ok(memberService.updateMember(userId, request));
}
```

### 2. 다중 Role

```java
public enum Role {
    USER,
    MANAGER,  // 매니저 추가
    ADMIN
}

// SecurityConfig
.requestMatchers(HttpMethod.POST, "/api/board").hasAnyRole("USER", "MANAGER", "ADMIN")
.requestMatchers(HttpMethod.DELETE, "/api/board/**").hasAnyRole("MANAGER", "ADMIN")
```

### 3. 캐싱 추가 (성능 향상)

```java
@Cacheable(value = "jwtClaims", key = "#token")
public Optional<Claims> validateToken(String token) {
    // ...
}
```

### 4. 구체적인 로깅

```java
public Optional<Claims> validateToken(String token) {
    try {
        Claims claims = Jwts.parser()...
        return Optional.of(claims);
    } catch (ExpiredJwtException e) {
        log.debug("토큰 만료: {}", e.getMessage());
        return Optional.empty();
    } catch (JwtException e) {
        log.debug("토큰 검증 실패: {}", e.getMessage());
        return Optional.empty();
    }
}
```

---

## 🚨 주의사항

### 1. ROLE_ 접두사

Spring Security는 `ROLE_` 접두사를 자동으로 추가합니다:
- DB: `"USER"`, `"ADMIN"`
- JWT: `"USER"`, `"ADMIN"`
- Filter: `new SimpleGrantedAuthority("ROLE_" + role)` ← **여기서 추가**
- SecurityConfig: `hasRole("USER")` ← **내부적으로 ROLE_USER 확인**

### 2. 권한 체크 순서

SecurityConfig의 `requestMatchers` 순서가 중요합니다:

```java
// ❌ 잘못된 예
.requestMatchers("/api/members/**").authenticated()
.requestMatchers(HttpMethod.DELETE, "/api/members/**").hasRole("ADMIN")  // 실행 안됨!

// ✅ 올바른 예
.requestMatchers(HttpMethod.DELETE, "/api/members/**").hasRole("ADMIN")
.requestMatchers("/api/members/**").authenticated()
```

### 3. 프로덕션 환경

- 기본 관리자 계정(`admin/admin1234`) 사용 금지
- 강력한 비밀번호로 변경
- JWT secret을 환경 변수로 관리
- HTTPS 사용 필수

---

## 📊 최종 파일 구조

```
commu-back/
├── src/main/java/com/kh/commu/
│   ├── domain/
│   │   ├── auth/
│   │   │   ├── controller/AuthController.java
│   │   │   ├── dto/AuthDto.java
│   │   │   └── service/AuthService.java
│   │   └── member/
│   │       ├── entity/Member.java  # Role enum 추가
│   │       └── service/MemberService.java
│   └── global/
│       ├── config/SecurityConfig.java  # Role 기반 접근 제어
│       └── security/
│           ├── JwtTokenProvider.java  # Optional<Claims> 반환
│           └── JwtAuthenticationFilter.java  # 최적화된 파싱
└── src/main/resources/
    └── application.yaml
```

---

## ✅ 최종 체크리스트

**기본 JWT 인증**:
- [x] JWT 의존성 추가
- [x] JWT 설정 파일 추가
- [x] JWT 유틸리티 클래스 생성
- [x] Auth 도메인 구조 생성
- [x] JWT 필터 및 Security 설정
- [x] Member 서비스 비밀번호 암호화
- [x] 전역 예외 처리 강화

**Role 기반 인가**:
- [x] Member 엔티티에 Role enum 추가
- [x] JWT에 role 정보 포함
- [x] JwtAuthenticationFilter role 권한 설정
- [x] SecurityConfig role 기반 접근 제어
- [x] 관리자 계정 생성 SQL

**성능 최적화**:
- [x] validateToken() Optional<Claims> 반환
- [x] 토큰 파싱 3회 → 1회 감소
- [x] Optional 패턴 적용

---

**작성일**: 2025-12-29  
**Spring Boot 버전**: 3.5.9  
**Java 버전**: 17  
**최종 업데이트**: 2025-12-29 (인증+인가+최적화 통합)

