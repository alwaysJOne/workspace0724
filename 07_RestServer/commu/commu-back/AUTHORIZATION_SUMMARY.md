# JWT 인증 + 인가 구현 완료 요약

## ✅ 구현 완료

commu-back 프로젝트에 **JWT 기반 인증(Authentication)**과 **Role 기반 인가(Authorization)**가 성공적으로 적용되었습니다.

---

## 🎯 주요 기능

### 1. 인증 (Authentication)
- JWT 토큰 기반 로그인
- 비밀번호 BCrypt 암호화
- 24시간 유효 토큰

### 2. 인가 (Authorization) ⭐ NEW
- **Role 기반 권한 시스템**
  - `USER`: 일반 사용자 (기본값)
  - `ADMIN`: 관리자
- **JWT에 role 정보 포함**
- **API별 세밀한 권한 제어**

---

## 📋 수정된 파일

### 엔티티
- ✅ `Member.java` - Role enum 추가, role 필드 추가

### 보안 (Security)
- ✅ `JwtTokenProvider.java` - JWT에 role 정보 포함
- ✅ `JwtAuthenticationFilter.java` - role 기반 권한 설정
- ✅ `SecurityConfig.java` - role 기반 접근 제어

### 서비스
- ✅ `AuthService.java` - 로그인 시 role 정보 반환
- ✅ `MemberService.java` - 기본 role=USER 설정 (자동)

---

## 🔐 권한 정책

### 인증 불필요 (Public)
```
POST /api/auth/login     - 로그인
POST /api/members        - 회원가입 (자동 role=USER)
```

### 관리자 전용 (ADMIN Only)
```
GET    /api/members              - 회원 목록 조회
GET    /api/members/search       - 회원 검색
DELETE /api/members/{userId}     - 회원 삭제
```

### 인증 필요 (USER + ADMIN)
```
GET  /api/members/{userId}    - 회원 조회
PUT  /api/members/{userId}    - 회원 수정
POST /api/board               - 게시글 작성
GET  /api/board               - 게시글 목록
...
```

---

## 🧪 테스트 방법

### 1. DB 스키마 업데이트
```sql
ALTER TABLE member 
ADD COLUMN role VARCHAR(10) NOT NULL DEFAULT 'USER'
AFTER status;
```

### 2. 관리자 계정 생성
```sql
INSERT INTO member (user_id, user_pwd, user_name, email, status, role, create_date, modify_date)
VALUES ('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 
        '관리자', 'admin@commu.com', 'Y', 'ADMIN', NOW(), NOW());
```
**계정**: `admin` / `admin1234`

### 3. 일반 회원 회원가입
```http
POST /api/members
Content-Type: application/json

{
  "user_id": "user01",
  "user_pwd": "password123",
  "user_name": "홍길동",
  "email": "user01@example.com"
}
```
✅ 자동으로 role=USER 할당

### 4. 로그인 테스트

**일반 회원**:
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
  "role": "USER"  ← 확인
}
```

**관리자**:
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
  "role": "ADMIN"  ← 확인
}
```

### 5. 권한 테스트

**USER 토큰으로 회원 목록 조회 (실패)**:
```http
GET /api/members
Authorization: Bearer {user_token}
```
❌ **403 Forbidden** - ADMIN 권한 필요

**ADMIN 토큰으로 회원 목록 조회 (성공)**:
```http
GET /api/members
Authorization: Bearer {admin_token}
```
✅ **200 OK** - 전체 회원 목록 반환

---

## 🔄 인증/인가 흐름

```
1. 로그인 (POST /api/auth/login)
   ↓
2. DB에서 userId, userPwd, role 확인
   ↓
3. JWT 생성 (userId + role 포함)
   ↓
4. 클라이언트가 토큰 저장
   ↓
5. API 요청 시 Authorization: Bearer {token}
   ↓
6. JwtAuthenticationFilter가 토큰 검증
   - userId 추출
   - role 추출 → ROLE_USER or ROLE_ADMIN
   ↓
7. SecurityConfig가 권한 체크
   - URL + HTTP Method + Role 확인
   - hasRole("ADMIN") 등
   ↓
8. 권한 OK → Controller 실행
   권한 NG → 403 Forbidden
```

---

## 📊 JWT 토큰 구조

### 기존 (인증만)
```json
{
  "sub": "user01",
  "iat": 1735459200,
  "exp": 1735545600
}
```

### 개선 (인증 + 인가)
```json
{
  "sub": "user01",
  "role": "USER",        ← NEW!
  "iat": 1735459200,
  "exp": 1735545600
}
```

---

## ⚡ 성능 최적화

### JWT 토큰 검증 최적화

**문제**: 기존 방식은 동일한 토큰을 **3번 파싱**
```java
// 비효율적 (3번 파싱)
if (jwtTokenProvider.validateToken(token)) {  // 1번
    String userId = jwtTokenProvider.getUserIdFromToken(token);  // 2번
    String role = jwtTokenProvider.getRoleFromToken(token);  // 3번
}
```

**개선**: `validateToken()`이 Claims를 반환하여 **1번 파싱**
```java
// 효율적 (1번 파싱)
Optional<Claims> claimsOpt = jwtTokenProvider.validateToken(token);
if (claimsOpt.isPresent()) {
    Claims claims = claimsOpt.get();
    String userId = claims.getSubject();
    String role = claims.get("role", String.class);
}
```

**효과**: 토큰 파싱 횟수 **66% 감소** (3회 → 1회)

---

## 🎓 실무 적용 팁

### 1. 본인 확인 추가
현재는 URL 레벨 권한만 체크하지만, **본인 확인 로직 추가** 권장:

```java
@PutMapping("/{userId}")
public ResponseEntity<?> updateMember(
        @PathVariable String userId,
        Authentication authentication) {
    
    String currentUserId = authentication.getName();
    boolean isAdmin = authentication.getAuthorities().stream()
        .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    
    if (!currentUserId.equals(userId) && !isAdmin) {
        throw new AccessDeniedException("본인 정보만 수정할 수 있습니다");
    }
    
    // ...
}
```

### 2. 다중 Role 추가
```java
public enum Role {
    USER,
    MANAGER,   // 매니저 추가
    ADMIN
}

// SecurityConfig
.hasAnyRole("MANAGER", "ADMIN")
```

### 3. 메서드 레벨 권한
```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/api/members")
public ResponseEntity<?> getAllMembers() {
    // ...
}
```

---

## 📖 문서

| 문서 | 설명 |
|------|------|
| `JWT_LOGIN_GUIDE.md` | JWT 인증+인가+최적화 통합 가이드 ⭐ |
| `ADMIN_CREATE_SQL.md` | 관리자 계정 생성 방법 |
| `DB_SCHEMA_UPDATE.sql` | DB 스키마 변경 SQL |
| `API_ENDPOINT_FIX.md` | API 엔드포인트 정리 |
| `JWT_LOGIN_GUIDE.md` | JWT 로그인 기본 가이드 |

---

## ✅ 체크리스트

- [x] Member 엔티티 Role enum 추가
- [x] JWT에 role 정보 포함
- [x] JwtAuthenticationFilter role 권한 설정
- [x] SecurityConfig role 기반 접근 제어
- [x] AuthService role 정보 반환
- [x] 회원가입 시 기본 role=USER
- [x] 관리자 계정 생성 SQL 작성
- [x] DB 스키마 업데이트 SQL 작성
- [x] 문서화 완료

---

## 🚨 주의사항

1. **프로덕션 환경**에서는 기본 관리자 계정(`admin/admin1234`) 사용 금지
2. 관리자 비밀번호는 **강력한 암호**로 변경
3. `ROLE_` 접두사는 Spring Security 컨벤션 (자동 추가됨)
4. SecurityConfig의 `requestMatchers` 순서 중요 (구체적인 규칙 먼저)

---

**적용일**: 2025-12-29  
**Spring Boot**: 3.5.9  
**Spring Security**: 6.x  
**JWT**: jjwt 0.12.3

---

## 🎉 완료!

이제 commu-back은 **실무 수준의 인증/인가 시스템**을 갖추었습니다!

- ✅ 안전한 JWT 인증
- ✅ Role 기반 권한 관리
- ✅ API별 세밀한 접근 제어
- ✅ 관리자/일반 사용자 구분

