# API 명세서

## 개요
학생용 정보공유 커뮤니티 백엔드 API

**Base URL**: `http://localhost:8080/api`

**인증 방식**: JWT Bearer Token

---

## 1. User (회원)

### 1.1 회원가입
```http
POST /users/signup
```

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "password123",
  "nickname": "닉네임"
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "email": "user@example.com",
    "nickname": "닉네임",
    "role": "USER",
    "createdAt": "2024-01-01T00:00:00"
  },
  "error": null
}
```

### 1.2 로그인
```http
POST /users/login
```

**Request Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  },
  "error": null
}
```

### 1.3 내 정보 조회
```http
GET /users/me
Authorization: Bearer {token}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "id": 1,
    "email": "user@example.com",
    "nickname": "닉네임",
    "role": "USER",
    "createdAt": "2024-01-01T00:00:00"
  },
  "error": null
}
```

---

## 2. Post (게시글)

### 2.1 게시글 작성
```http
POST /posts
Authorization: Bearer {token}
```

**Request Body**:
```json
{
  "category": "TODAY_LEARNED",
  "title": "제목",
  "content": "내용"
}
```

**카테고리 종류**:
- `TODAY_LEARNED`: 📝 오늘 배운 것
- `STUCK`: ❓ 막힌 것
- `SHORT_TIP`: 💡 짧은 팁
- `SUMMARY_NOTE`: 📌 정리 노트

### 2.2 게시글 목록 조회
```http
GET /posts?page=0&size=10&category=TODAY_LEARNED
```

**Query Parameters**:
- `page` (optional): 페이지 번호 (기본값: 0)
- `size` (optional): 페이지 크기 (기본값: 10)
- `category` (optional): 카테고리 필터

**Response**:
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "userId": 1,
        "userNickname": "닉네임",
        "category": "TODAY_LEARNED",
        "title": "제목",
        "content": "내용",
        "likeCount": 5,
        "createdAt": "2024-01-01T00:00:00",
        "updatedAt": "2024-01-01T00:00:00"
      }
    ],
    "pageable": {...},
    "totalElements": 100,
    "totalPages": 10
  },
  "error": null
}
```

### 2.3 게시글 상세 조회
```http
GET /posts/{postId}
```

### 2.4 게시글 수정
```http
PUT /posts/{postId}
Authorization: Bearer {token}
```

**Request Body**:
```json
{
  "category": "TODAY_LEARNED",
  "title": "수정된 제목",
  "content": "수정된 내용"
}
```

### 2.5 게시글 삭제
```http
DELETE /posts/{postId}
Authorization: Bearer {token}
```

### 2.6 내가 작성한 게시글 목록
```http
GET /posts/my?page=0&size=10
Authorization: Bearer {token}
```

---

## 3. Comment (댓글)

### 3.1 댓글 작성
```http
POST /posts/{postId}/comments
Authorization: Bearer {token}
```

**Request Body**:
```json
{
  "content": "댓글 내용"
}
```

### 3.2 댓글 목록 조회
```http
GET /posts/{postId}/comments
```

**Response**:
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "postId": 1,
      "userId": 1,
      "userNickname": "닉네임",
      "content": "댓글 내용",
      "createdAt": "2024-01-01T00:00:00"
    }
  ],
  "error": null
}
```

### 3.3 댓글 수정
```http
PUT /posts/{postId}/comments/{commentId}
Authorization: Bearer {token}
```

### 3.4 댓글 삭제
```http
DELETE /posts/{postId}/comments/{commentId}
Authorization: Bearer {token}
```

---

## 4. Like (좋아요)

### 4.1 좋아요 토글
```http
POST /posts/{postId}/like
Authorization: Bearer {token}
```

**Response**:
```json
{
  "success": true,
  "data": {
    "liked": true,
    "likeCount": 6
  },
  "error": null
}
```

### 4.2 좋아요 상태 조회
```http
GET /posts/{postId}/like
Authorization: Bearer {token}
```

**Response**:
```json
{
  "success": true,
  "data": true,
  "error": null
}
```

---

## 에러 응답 형식

```json
{
  "success": false,
  "data": null,
  "error": {
    "message": "에러 메시지",
    "status": 400
  }
}
```

**주요 에러 코드**:
- `400`: 잘못된 요청 (유효성 검증 실패)
- `401`: 인증 실패
- `403`: 권한 없음
- `404`: 리소스를 찾을 수 없음
- `500`: 서버 내부 오류

---

## H2 Console

개발 환경에서는 H2 Console에 접근 가능합니다:

**URL**: http://localhost:8080/h2-console

**접속 정보**:
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (비어있음)

