# Commu API Documentation

**Base URL:** `http://localhost:8888`  
**Version:** v1  
**작성일:** 2025-12-26

---

## 목차

1. [인증 API](#1-인증-api)
2. [회원 API](#2-회원-api)
3. [게시판 API](#3-게시판-api)
4. [공통 응답 형식](#4-공통-응답-형식)
5. [에러 코드](#5-에러-코드)

---

## 1. 인증 API

### 1.1. 로그인

사용자 인증 및 JWT 토큰 발급

**Endpoint:** `POST /api/v1/auth/login`  
**인증 필요:** ❌

#### 요청

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "user_id": "user01",
  "user_pwd": "password123"
}
```

**Validation:**
- `user_id`: 필수, 공백 불가
- `user_pwd`: 필수, 공백 불가

#### 응답

**성공 (200 OK):**
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyMDEi...",
    "user_id": "user01",
    "user_name": "홍길동",
    "role": "USER"
  },
  "error": null
}
```

**실패 (401 Unauthorized):**
```json
{
  "status": 401,
  "message": "아이디 또는 비밀번호가 일치하지 않습니다.",
  "path": "/api/v1/auth/login",
  "timestamp": "2025-12-26T00:15:30.123"
}
```

---

## 2. 회원 API

### 2.1. 회원가입

새로운 회원 등록

**Endpoint:** `POST /api/v1/members`  
**인증 필요:** ❌

#### 요청

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "user_id": "user01",
  "user_pwd": "password123",
  "user_name": "홍길동",
  "email": "user01@example.com",
  "gender": "M",
  "age": 25,
  "phone": "010-1234-5678",
  "address": "서울시 강남구"
}
```

**Validation:**
- `user_id`: 필수, 4-30자, 영문자와 숫자만 가능
- `user_pwd`: 필수, 8-100자
- `user_name`: 필수, 2-15자
- `email`: 선택, 이메일 형식, 최대 255자
- `gender`: 선택, M 또는 F
- `age`: 선택, 0-150
- `phone`: 선택, 010-1234-5678 형식
- `address`: 선택, 최대 100자

#### 응답

**성공 (200 OK):**
```json
{
  "success": true,
  "data": "user01",
  "error": null
}
```

**실패 - 중복 회원 (409 Conflict):**
```json
{
  "status": 409,
  "message": "이미 존재하는 회원입니다.",
  "path": "/api/v1/members",
  "timestamp": "2025-12-26T00:15:30.123"
}
```

**실패 - Validation 오류 (400 Bad Request):**
```json
{
  "status": 400,
  "message": "userId: 사용자 ID는 4자 이상 30자 이하여야 합니다, userPwd: 비밀번호는 8자 이상 100자 이하여야 합니다",
  "path": "/api/v1/members",
  "timestamp": "2025-12-26T00:15:30.123"
}
```

---

### 2.2. 회원 목록 조회

전체 회원 목록 조회 (활성 회원만)

**Endpoint:** `GET /api/v1/members`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Query Parameters:** 없음

#### 응답

**성공 (200 OK):**
```json
[
  {
    "user_id": "user01",
    "user_name": "홍길동",
    "email": "user01@example.com",
    "gender": "M",
    "age": 25,
    "phone": "010-1234-5678",
    "address": "서울시 강남구",
    "create_date": "2025-12-26T10:00:00",
    "modify_date": "2025-12-26T10:00:00"
  },
  {
    "user_id": "user02",
    "user_name": "김철수",
    "email": "user02@example.com",
    "gender": "M",
    "age": 30,
    "phone": "010-9876-5432",
    "address": "서울시 서초구",
    "create_date": "2025-12-26T11:00:00",
    "modify_date": null
  }
]
```

---

### 2.3. 회원 검색

키워드로 회원 검색 (이름 기준)

**Endpoint:** `GET /api/v1/members?keyword={keyword}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Query Parameters:**
- `keyword` (required): 검색할 키워드

**예시:**
```
GET /api/v1/members?keyword=홍길
```

#### 응답

**성공 (200 OK):**
```json
[
  {
    "user_id": "user01",
    "user_name": "홍길동",
    "email": "user01@example.com",
    "gender": "M",
    "age": 25,
    "phone": "010-1234-5678",
    "address": "서울시 강남구",
    "create_date": "2025-12-26T10:00:00",
    "modify_date": "2025-12-26T10:00:00"
  }
]
```

---

### 2.4. 회원 단건 조회

특정 회원의 상세 정보 조회

**Endpoint:** `GET /api/v1/members/{userId}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Path Parameters:**
- `userId`: 조회할 회원 ID

**예시:**
```
GET /api/v1/members/user01
```

#### 응답

**성공 (200 OK):**
```json
{
  "user_id": "user01",
  "user_name": "홍길동",
  "email": "user01@example.com",
  "gender": "M",
  "age": 25,
  "phone": "010-1234-5678",
  "address": "서울시 강남구",
  "create_date": "2025-12-26T10:00:00",
  "modify_date": "2025-12-26T10:00:00"
}
```

**실패 - 회원 없음 (404 Not Found):**
```json
{
  "status": 404,
  "message": "회원 ID: user99",
  "path": "/api/v1/members/user99",
  "timestamp": "2025-12-26T00:15:30.123"
}
```

---

### 2.5. 회원 정보 수정

특정 회원의 정보 수정

**Endpoint:** `PUT /api/v1/members/{userId}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
Content-Type: application/json
```

**Path Parameters:**
- `userId`: 수정할 회원 ID

**Body:**
```json
{
  "user_name": "홍길동수정",
  "email": "newemail@example.com",
  "gender": "M",
  "age": 26,
  "phone": "010-9999-8888",
  "address": "서울시 송파구"
}
```

**Validation:**
- `user_name`: 선택, 2-15자
- `email`: 선택, 이메일 형식, 최대 255자
- `gender`: 선택, M 또는 F
- `age`: 선택, 0-150
- `phone`: 선택, 010-1234-5678 형식
- `address`: 선택, 최대 100자

#### 응답

**성공 (200 OK):**
```json
{
  "user_id": "user01",
  "user_name": "홍길동수정",
  "email": "newemail@example.com",
  "gender": "M",
  "age": 26,
  "phone": "010-9999-8888",
  "address": "서울시 송파구",
  "create_date": "2025-12-26T10:00:00",
  "modify_date": "2025-12-26T12:00:00"
}
```

---

### 2.6. 회원 삭제

특정 회원 삭제 (논리 삭제 - status를 N으로 변경)

**Endpoint:** `DELETE /api/v1/members/{userId}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Path Parameters:**
- `userId`: 삭제할 회원 ID

**예시:**
```
DELETE /api/v1/members/user01
```

#### 응답

**성공 (200 OK):**
```json
"ok"
```

---

## 3. 게시판 API

### 3.1. 게시글 작성

새로운 게시글 작성 (파일 업로드 및 태그 포함)

**Endpoint:** `POST /api/board`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
Content-Type: multipart/form-data
```

**Body (FormData):**
- `board_title` (required): 게시글 제목
- `board_content` (required): 게시글 내용
- `user_id` (required): 작성자 ID
- `file` (optional): 첨부 파일
- `tags` (optional): 태그 리스트 (배열)

**예시:**
```
POST /api/board
Content-Type: multipart/form-data

board_title=제목입니다
board_content=내용입니다
user_id=user01
file=image.jpg
tags=태그1
tags=태그2
```

#### 응답

**성공 (200 OK):**
```json
1
```
(생성된 게시글 ID)

---

### 3.2. 게시글 목록 조회 (페이징)

게시글 목록을 페이지네이션으로 조회

**Endpoint:** `GET /api/board`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Query Parameters:**
- `page` (optional, default: 0): 페이지 번호 (0부터 시작)
- `size` (optional, default: 5): 페이지당 항목 수
- `sort` (optional, default: "createDate,desc"): 정렬 기준 (필드명,방향)

**예시:**
```
GET /api/board?page=0&size=10&sort=createDate,desc
```

#### 응답

**성공 (200 OK):**
```json
{
  "content": [
    {
      "board_id": 1,
      "board_title": "첫 번째 게시글",
      "board_content": "게시글 내용입니다",
      "origin_name": "image.jpg",
      "change_name": "uuid_image.jpg",
      "count": 0,
      "user_id": "user01",
      "user_name": "홍길동",
      "create_date": "2025-12-26T10:00:00",
      "tags": ["태그1", "태그2"]
    }
  ],
  "page_number": 0,
  "page_size": 10,
  "total_elements": 1,
  "total_pages": 1,
  "first": true,
  "last": true
}
```

---

### 3.3. 게시글 단건 조회

특정 게시글의 상세 정보 조회

**Endpoint:** `GET /api/board/{boardId}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Path Parameters:**
- `boardId`: 조회할 게시글 ID

**예시:**
```
GET /api/board/1
```

#### 응답

**성공 (200 OK):**
```json
{
  "board_id": 1,
  "board_title": "첫 번째 게시글",
  "board_content": "게시글 내용입니다",
  "origin_name": "image.jpg",
  "change_name": "uuid_image.jpg",
  "count": 5,
  "user_id": "user01",
  "user_name": "홍길동",
  "create_date": "2025-12-26T10:00:00",
  "tags": ["태그1", "태그2"]
}
```

---

### 3.4. 게시글 수정

특정 게시글 수정 (부분 수정 가능)

**Endpoint:** `PATCH /api/board/{boardId}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
Content-Type: multipart/form-data
```

**Path Parameters:**
- `boardId`: 수정할 게시글 ID

**Body (FormData):**
- `board_title` (optional): 수정할 제목
- `board_content` (optional): 수정할 내용
- `file` (optional): 수정할 파일
- `tags` (optional): 수정할 태그 리스트

**예시:**
```
PATCH /api/board/1
Content-Type: multipart/form-data

board_title=수정된 제목
```

#### 응답

**성공 (200 OK):**
```json
{
  "board_id": 1,
  "board_title": "수정된 제목",
  "board_content": "게시글 내용입니다",
  "origin_name": "image.jpg",
  "change_name": "uuid_image.jpg",
  "count": 5,
  "user_id": "user01",
  "user_name": "홍길동",
  "create_date": "2025-12-26T10:00:00",
  "tags": ["태그1", "태그2"]
}
```

---

### 3.5. 게시글 삭제

특정 게시글 삭제 (논리 삭제)

**Endpoint:** `DELETE /api/board/{boardId}`  
**인증 필요:** ✅

#### 요청

**Headers:**
```
Authorization: Bearer {JWT_TOKEN}
```

**Path Parameters:**
- `boardId`: 삭제할 게시글 ID

**예시:**
```
DELETE /api/board/1
```

#### 응답

**성공 (204 No Content):**
```
(응답 본문 없음)
```

---

## 4. 공통 응답 형식

### 4.1. 성공 응답 (ApiResponse)

```json
{
  "success": true,
  "data": { /* 실제 데이터 */ },
  "error": null
}
```

### 4.2. 에러 응답 (ErrorResponse)

```json
{
  "status": 400,
  "message": "에러 메시지",
  "path": "/api/v1/members",
  "timestamp": "2025-12-26T00:15:30.123"
}
```

---

## 5. 에러 코드

| HTTP 상태 | 에러 코드 | 메시지 | 설명 |
|----------|---------|-------|------|
| 400 | INVALID_INPUT_VALUE | 입력값이 올바르지 않습니다. | Validation 실패 |
| 400 | MISSING_REQUEST_PARAMETER | 필수 파라미터가 누락되었습니다. | 필수 파라미터 누락 |
| 401 | UNAUTHORIZED | 인증이 필요합니다. | JWT 토큰 누락 또는 만료 |
| 401 | INVALID_CREDENTIALS | 아이디 또는 비밀번호가 일치하지 않습니다. | 로그인 실패 |
| 401 | INVALID_TOKEN | 유효하지 않은 토큰입니다. | JWT 토큰 검증 실패 |
| 401 | TOKEN_EXPIRED | 만료된 토큰입니다. | JWT 토큰 만료 |
| 403 | FORBIDDEN | 접근 권한이 없습니다. | 권한 부족 |
| 404 | MEMBER_NOT_FOUND | 존재하지 않는 회원입니다. | 회원 조회 실패 |
| 404 | BOARD_NOT_FOUND | 존재하지 않는 게시글입니다. | 게시글 조회 실패 |
| 409 | MEMBER_ALREADY_EXISTS | 이미 존재하는 회원입니다. | 중복 회원 가입 시도 |
| 500 | INTERNAL_SERVER_ERROR | 서버 오류가 발생했습니다. | 예상치 못한 서버 오류 |

---

## 6. 인증 방법

### JWT 토큰 사용

1. `/api/v1/auth/login` 엔드포인트로 로그인하여 JWT 토큰 획득
2. 이후 모든 인증이 필요한 API 호출 시 Header에 포함:

```
Authorization: Bearer {JWT_TOKEN}
```

### 토큰 유효 기간

- **24시간** (86400초)
- 만료 시 재로그인 필요

---

## 7. 예시 시나리오

### 7.1. 회원가입 → 로그인 → 회원정보 조회

```bash
# 1. 회원가입
curl -X POST http://localhost:8888/api/v1/members \
  -H "Content-Type: application/json" \
  -d '{
    "user_id": "testuser",
    "user_pwd": "password123",
    "user_name": "테스트유저",
    "email": "test@example.com"
  }'

# 2. 로그인
curl -X POST http://localhost:8888/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "user_id": "testuser",
    "user_pwd": "password123"
  }'

# 응답에서 token 값 복사

# 3. 회원정보 조회
curl -X GET http://localhost:8888/api/v1/members/testuser \
  -H "Authorization: Bearer {복사한_토큰}"
```

---

## 8. 참고 사항

### 8.1. API 버전 관리
- 현재 버전: v1
- Base Path: `/api/v1`

### 8.2. 데이터베이스
- MySQL 사용
- 시간대: Asia/Seoul
- 포트: 3306

### 8.3. 서버 설정
- 포트: 8888
- 인코딩: UTF-8
- 최대 파일 업로드 크기: 10MB
- 최대 요청 크기: 50MB

### 8.4. 보안
- JWT 토큰 기반 인증
- 비밀번호 BCrypt 암호화
- CSRF 비활성화 (Stateless API)

---

**문서 버전:** 1.0  
**최종 수정일:** 2025-12-26  
**작성자:** Commu Development Team

