# 관리자 계정 생성 SQL

## 🔐 관리자 계정 직접 생성

아래 SQL을 실행하여 관리자 계정을 생성할 수 있습니다.

---

## 1. BCrypt 암호화된 비밀번호 사용

### SQL

```sql
INSERT INTO member (
    user_id, 
    user_pwd, 
    user_name, 
    email, 
    status, 
    role, 
    create_date, 
    modify_date
) VALUES (
    'admin',
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',  -- "admin1234"를 BCrypt로 암호화한 값
    '관리자',
    'admin@commu.com',
    'Y',
    'ADMIN',
    NOW(),
    NOW()
);
```

**계정 정보**:
- **ID**: `admin`
- **비밀번호**: `admin1234`
- **역할**: `ADMIN`

---

## 2. 사용자 정의 비밀번호로 생성

다른 비밀번호를 사용하고 싶다면:

### 방법 1: 임시 USER 계정 생성 후 업데이트

```sql
-- 1단계: 회원가입 API로 임시 계정 생성 (POST /api/members)
-- {
--   "user_id": "admin",
--   "user_pwd": "원하는비밀번호",
--   "user_name": "관리자",
--   "email": "admin@commu.com"
-- }

-- 2단계: role을 ADMIN으로 변경
UPDATE member 
SET role = 'ADMIN' 
WHERE user_id = 'admin';
```

### 방법 2: BCrypt 암호화 도구 사용

온라인 BCrypt 생성기 사용:
- https://bcrypt-generator.com/
- Rounds: 10 (권장)
- 생성된 해시를 SQL에 삽입

```sql
INSERT INTO member (
    user_id, 
    user_pwd, 
    user_name, 
    email, 
    status, 
    role, 
    create_date, 
    modify_date
) VALUES (
    'admin',
    '{BCrypt로 암호화한 비밀번호}',  -- 온라인 도구에서 생성한 해시
    '관리자',
    'admin@commu.com',
    'Y',
    'ADMIN',
    NOW(),
    NOW()
);
```

---

## 3. 기존 회원을 관리자로 승격

```sql
-- 특정 회원을 관리자로 변경
UPDATE member 
SET role = 'ADMIN' 
WHERE user_id = '변경할_회원_ID';
```

---

## 4. 관리자 계정 확인

```sql
-- 관리자 계정 조회
SELECT user_id, user_name, email, role, status 
FROM member 
WHERE role = 'ADMIN' AND status = 'Y';
```

---

## 5. 테스트 시나리오

### 관리자 로그인

```http
POST http://localhost:8888/api/auth/login
Content-Type: application/json

{
  "user_id": "admin",
  "user_pwd": "admin1234"
}
```

**예상 응답**:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user_id": "admin",
  "user_name": "관리자",
  "role": "ADMIN"
}
```

### 관리자 전용 API 테스트

```http
GET http://localhost:8888/api/members
Authorization: Bearer {admin_token}
```

✅ **성공**: 전체 회원 목록 반환

---

## 📋 권한 비교

| API | USER | ADMIN |
|-----|------|-------|
| 회원가입 | ✅ | ✅ |
| 로그인 | ✅ | ✅ |
| **회원 목록 조회** | ❌ | ✅ |
| **회원 검색** | ❌ | ✅ |
| 본인 정보 조회 | ✅ | ✅ |
| 본인 정보 수정 | ✅ | ✅ |
| **회원 삭제** | ❌ | ✅ |
| 게시글 작성/조회 | ✅ | ✅ |

---

## ⚠️ 주의사항

1. **프로덕션 환경**에서는 절대 기본 관리자 계정을 사용하지 마세요
2. 관리자 비밀번호는 **강력한 암호**로 설정하세요
3. 관리자 계정 생성 후 **즉시 비밀번호를 변경**하세요
4. 관리자 계정의 **로그를 모니터링**하세요

---

## 📖 관련 문서

- **JWT 통합 가이드**: `JWT_LOGIN_GUIDE.md` ⭐
- **API 엔드포인트**: `API_ENDPOINT_FIX.md`

---

**작성일**: 2025-12-29  
**적용 버전**: commu-back v1.0

