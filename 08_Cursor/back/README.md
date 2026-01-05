# 학생용 정보공유 커뮤니티 (백엔드)

학습 과정을 기록하고 경험을 공유하는 커뮤니티 플랫폼의 백엔드 API 서버입니다.

## 기술 스택

- **Framework**: Spring Boot 3.5.9
- **Language**: Java 17
- **Database**: H2 (개발), MySQL (운영 예정)
- **Security**: Spring Security + JWT
- **ORM**: Spring Data JPA
- **Build Tool**: Gradle

## 주요 기능

### 1. 사용자 (User)
- 회원가입 / 로그인 (JWT 인증)
- 내 정보 조회
- 닉네임 중복 검사

### 2. 게시글 (Post)
- CRUD (작성, 조회, 수정, 삭제)
- 카테고리 필터링 (오늘 배운 것, 막힌 것, 짧은 팁, 정리 노트)
- 페이지네이션 지원
- 작성자 본인만 수정/삭제 가능

### 3. 댓글 (Comment)
- 게시글별 댓글 작성/조회
- 댓글 수정/삭제 (작성자만)

### 4. 좋아요 (Like)
- 게시글 좋아요/취소 토글
- 좋아요 수 캐싱 (likeCount)

## 프로젝트 구조

```
src/main/java/com/kh/back
├── domain/
│   ├── user/          # 사용자 도메인
│   │   ├── entity/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── controller/
│   │   └── dto/
│   ├── post/          # 게시글 도메인
│   ├── comment/       # 댓글 도메인
│   └── like/          # 좋아요 도메인
└── global/
    ├── common/        # 공통 응답/엔티티
    ├── config/        # 설정 (JPA, Security)
    ├── security/      # JWT 인증
    └── exception/     # 예외 처리
```

## 시작하기

### 1. 프로젝트 클론

```bash
git clone <repository-url>
cd back
```

### 2. 빌드 및 실행

```bash
# Windows
gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

### 3. H2 Console 접속

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (비어있음)

## API 문서

자세한 API 명세는 [API.md](docs/API.md)를 참고하세요.

### 주요 엔드포인트

| 도메인 | 메서드 | 경로 | 설명 |
|--------|--------|------|------|
| User | POST | `/api/users/signup` | 회원가입 |
| User | POST | `/api/users/login` | 로그인 |
| User | GET | `/api/users/me` | 내 정보 조회 |
| Post | POST | `/api/posts` | 게시글 작성 |
| Post | GET | `/api/posts` | 게시글 목록 |
| Post | GET | `/api/posts/{id}` | 게시글 상세 |
| Comment | POST | `/api/posts/{postId}/comments` | 댓글 작성 |
| Comment | GET | `/api/posts/{postId}/comments` | 댓글 목록 |
| Like | POST | `/api/posts/{postId}/like` | 좋아요 토글 |

## 환경 설정

`application.yaml` 주요 설정:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

jwt:
  secret: (변경 필요)
  expiration: 86400000  # 24시간
```

## 보안

- 비밀번호는 BCrypt로 암호화
- JWT 토큰 기반 인증
- 게시글/댓글 작성자만 수정/삭제 가능
- CSRF 비활성화 (Stateless API)

## 개발 규칙

- **패키지 구조**: 도메인 기반 (기능 단위)
- **계층 구조**: Controller → Service → Repository
- **트랜잭션**: 조회는 `@Transactional(readOnly = true)` 적용
- **검증**: Jakarta Validation 사용 (`@Valid`)
- **응답 포맷**: 표준 `ApiResponse` 사용

## 향후 계획

- [ ] MySQL 연동 및 운영 환경 설정
- [ ] 마이페이지 통계 API (작성한 글 수, 받은 좋아요 합계)
- [ ] 게시글 검색 기능
- [ ] 알림 기능
- [ ] 파일 업로드 (이미지)
- [ ] API 문서 자동화 (Swagger/Spring REST Docs)

## 라이선스

MIT License

