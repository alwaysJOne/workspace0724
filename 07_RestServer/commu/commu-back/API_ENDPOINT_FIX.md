# API 엔드포인트 수정 완료

프론트엔드(commu-front)와 백엔드(commu-back)의 API 엔드포인트를 일치시켰습니다.

---

## 🔧 수정된 내용

### 1. Member API 경로 통일

**최종 경로**: `/api/members` (버전 제거, 복수형 사용)

### 2. Auth API 경로 통일

**최종 경로**: `/api/auth` (버전 제거)

### 3. Security 설정 수정

**변경 전**:
```java
.requestMatchers("/api/auth/**", "/api/member").permitAll()
```

**변경 후**:
```java
.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
.requestMatchers(HttpMethod.POST, "/api/members").permitAll()
```

**이유**: 
- 회원가입과 로그인만 인증 없이 접근 가능하도록 명시
- POST 메서드만 허용 (GET, PUT, DELETE는 인증 필요)

### 4. Board API 파라미터 수정

**변경 전**: JSON RequestBody + 파일  
**변경 후**: multipart/form-data (모든 파라미터를 @RequestParam으로 처리)

**이유**: 프론트엔드가 `multipart/form-data` 형식으로 전송

---

## 📋 최종 API 엔드포인트

### Auth API

| 메서드 | 경로 | 인증 | 권한 | 설명 |
|--------|------|------|------|------|
| POST | `/api/auth/login` | ❌ | - | 로그인 |

### Member API

| 메서드 | 경로 | 인증 | 권한 | 설명 |
|--------|------|------|------|------|
| POST | `/api/members` | ❌ | - | 회원가입 (기본 role: USER) |
| GET | `/api/members` | ✅ | ADMIN | 회원 목록 조회 |
| GET | `/api/members?keyword={keyword}` | ✅ | ADMIN | 회원 검색 |
| GET | `/api/members/{userId}` | ✅ | USER, ADMIN | 회원 단건 조회 |
| PUT | `/api/members/{userId}` | ✅ | USER, ADMIN | 회원 정보 수정 |
| DELETE | `/api/members/{userId}` | ✅ | ADMIN | 회원 삭제 |

### Board API

| 메서드 | 경로 | 인증 | 설명 |
|--------|------|------|------|
| POST | `/api/board` | ✅ | 게시글 작성 (multipart) |
| GET | `/api/board` | ✅ | 게시글 목록 조회 (페이징) |
| GET | `/api/board/{boardId}` | ✅ | 게시글 단건 조회 |
| PATCH | `/api/board/{boardId}` | ✅ | 게시글 수정 (multipart) |
| DELETE | `/api/board/{boardId}` | ✅ | 게시글 삭제 |

---

## 🧪 테스트 방법

### 1. 회원가입 (인증 불필요) ✅

```http
POST http://localhost:8888/api/members
Content-Type: application/json

{
  "user_id": "testuser",
  "user_pwd": "Password123!",
  "user_name": "테스트유저",
  "email": "test@example.com"
}
```

**예상 결과**: 200 OK, `"testuser"` 반환

### 2. 로그인 (인증 불필요) ✅

```http
POST http://localhost:8888/api/auth/login
Content-Type: application/json

{
  "user_id": "testuser",
  "user_pwd": "Password123!"
}
```

**예상 결과**: 200 OK
```json
{
  "token": "eyJhbGc...",
  "user_id": "testuser",
  "user_name": "테스트유저",
  "role": "USER"
}
```

### 3. 회원 목록 조회 (인증 필요) ✅

```http
GET http://localhost:8888/api/members
Authorization: Bearer {token}
```

**예상 결과**: 200 OK, 회원 목록 반환

### 4. 게시글 작성 (인증 필요, multipart) ✅

```http
POST http://localhost:8888/api/board
Authorization: Bearer {token}
Content-Type: multipart/form-data

board_title: 제목입니다
board_content: 내용입니다
user_id: testuser
tags: 태그1
tags: 태그2
file: (파일 선택)
```

**예상 결과**: 200 OK, 게시글 ID 반환

---

## 🚨 주의사항

### 403 Forbidden 에러가 발생하는 경우

**원인**: Security 설정에서 허용되지 않은 경로/메서드

**해결**:
1. 경로가 정확한지 확인 (`/api/...`)
2. POST 메서드를 사용하는지 확인 (회원가입, 로그인)
3. 인증이 필요한 API는 `Authorization: Bearer {token}` 헤더 포함

### 401 Unauthorized 에러가 발생하는 경우

**원인**: JWT 토큰 문제

**해결**:
1. 토큰이 헤더에 포함되었는지 확인
2. 토큰 형식 확인 (`Bearer {token}`)
3. 토큰이 만료되지 않았는지 확인 (24시간)
4. 로그인을 다시 시도하여 새 토큰 발급

### CORS 에러가 발생하는 경우

**원인**: 프론트엔드 도메인이 허용되지 않음

**해결**: `SecurityConfig`에서 CORS 설정 확인
- `localhost:5173` (Vite)
- `localhost:3000` (Create React App)

---

## ✅ 점검 완료

- [x] Member API 경로 통일 (`/api/members`)
- [x] Auth API 경로 통일 (`/api/auth`)
- [x] Security 설정 수정 (POST 메서드만 permitAll)
- [x] Board API multipart 처리
- [x] 프론트엔드/백엔드 모두 버전(v1) 제거
- [x] 프론트엔드 API 문서와 일치 확인
- [x] **Role 기반 인가(Authorization) 구현**
- [x] **JWT에 role 정보 포함**

---

## 🔐 인증 + 인가 구현

**상세 문서**: `ROLE_AUTHORIZATION_GUIDE.md` 참고

### Role 종류
- `USER`: 일반 사용자 (회원가입 시 기본값)
- `ADMIN`: 관리자 (SQL로 직접 생성)

### 관리자 계정 생성
상세 내용은 `ADMIN_CREATE_SQL.md` 참고

---

**수정일**: 2025-12-29  
**테스트 완료**: ✅

