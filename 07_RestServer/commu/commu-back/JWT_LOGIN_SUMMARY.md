# JWT 로그인 적용 완료 요약

## ✅ 구현 완료

commu-back 프로젝트에 JWT 기반 로그인 기능이 성공적으로 적용되었습니다.

---

## 📦 추가된 파일

### 도메인 (domain/auth)
- `dto/AuthDto.java` - 로그인 요청/응답 DTO
- `service/AuthService.java` - 로그인 비즈니스 로직
- `controller/AuthController.java` - 로그인 API 엔드포인트

### 보안 (global/security)
- `JwtTokenProvider.java` - JWT 토큰 생성/검증
- `JwtAuthenticationFilter.java` - JWT 인증 필터

### 설정 (global/config)
- `SecurityConfig.java` - Spring Security 설정

---

## 🔄 수정된 파일

- `build.gradle` - JWT, Security 의존성 추가
- `application.yaml` - JWT secret, expiration 설정
- `MemberService.java` - 비밀번호 BCrypt 암호화
- `GlobalExceptionHandler.java` - 인증 예외 처리 추가

---

## 🎯 API 엔드포인트

### 로그인
```http
POST /api/auth/login
Content-Type: application/json

{
  "user_id": "user01",
  "user_pwd": "password123"
}
```

**응답**:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user_id": "user01",
  "user_name": "홍길동",
  "role": "USER"
}
```

### 인증이 필요한 API 호출
```http
GET /api/board
Authorization: Bearer {token}
```

---

## 🔐 보안 설정

| 항목 | 설정값 |
|------|--------|
| 비밀번호 암호화 | BCrypt |
| JWT 알고리즘 | HS512 |
| 토큰 유효기간 | 24시간 |
| 토큰 포함 정보 | userId, role |
| 세션 | Stateless (미사용) |
| CSRF | 비활성화 |
| CORS | localhost:5173, 3000 허용 |
| **인가 (Authorization)** | **Role 기반 (USER, ADMIN)** |

---

## 📝 접근 권한 정책

### 인증 불필요 (Public)
- `POST /api/auth/login` - 로그인
- `POST /api/members` - 회원가입

### 관리자 전용 (ADMIN)
- `GET /api/members` - 회원 목록 조회
- `GET /api/members/search` - 회원 검색
- `DELETE /api/members/{userId}` - 회원 삭제

### 인증 필요 (USER, ADMIN)
- `GET /api/members/{userId}` - 회원 조회
- `PUT /api/members/{userId}` - 회원 수정
- 게시판 API 전체

---

## 🧪 테스트 시나리오

1. **회원가입**: `POST /api/members` → 비밀번호 암호화되어 저장
2. **로그인**: `POST /api/auth/login` → JWT 토큰 발급
3. **게시글 조회**: `GET /api/board` (토큰 포함) → 정상 조회
4. **토큰 없이 조회**: `GET /api/board` → 401 Unauthorized

---

## 📖 상세 문서

| 문서 | 설명 |
|------|------|
| `JWT_LOGIN_GUIDE.md` | JWT 인증+인가+최적화 통합 가이드 ⭐ |
| `ADMIN_CREATE_SQL.md` | 관리자 계정 생성 SQL |
| `API_ENDPOINT_FIX.md` | API 엔드포인트 정리 |

---

## 🚀 다음 단계 (선택사항)

1. Refresh Token 추가
2. ~~권한 관리 (ADMIN, USER 등)~~ ✅ **완료**
3. 토큰 블랙리스트 (로그아웃 처리)
4. 소셜 로그인 (OAuth2)
5. 이메일 인증
6. 본인 확인 로직 (본인만 수정 가능)

---

**적용일**: 2025-12-29  
**Spring Boot**: 3.5.9  
**JWT 라이브러리**: jjwt 0.12.3

