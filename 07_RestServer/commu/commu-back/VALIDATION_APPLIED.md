# Spring Boot Validation 적용 완료

commu-back 프로젝트에 Spring Boot Validation을 성공적으로 적용했습니다.

---

## 📋 적용된 파일 목록

### 1. DTO 파일 (검증 규칙 추가)
- `domain/member/dto/MemberDto.java` - 회원 관련 검증
- `domain/board/dto/BoardDto.java` - 게시글 관련 검증 (신규 Request/UpdateRequest 추가)

### 2. Controller 파일 (@Validated 적용)
- `domain/member/controller/MemberController.java`
- `domain/board/controller/BoardController.java`

### 3. 전역 예외 처리 (신규 생성)
- `global/exception/GlobalExceptionHandler.java` - 검증 예외 처리
- `global/exception/ErrorResponse.java` - 에러 응답 DTO

### 4. 문서
- `VALIDATION_GUIDE.md` - 적용 가이드 및 사용법

---

## 🔄 주요 변경사항

### MemberDto 개선
**Request (회원가입)**
```java
// 강화된 검증
@NotBlank(message = "사용자 ID는 필수입니다")
@Size(min = 4, max = 20, message = "사용자 ID는 4자 이상 20자 이하여야 합니다")
@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "사용자 ID는 영문, 숫자, 언더스코어만 사용 가능합니다")
private String userId;

@NotBlank(message = "비밀번호는 필수입니다")
@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하여야 합니다")
@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]+$", 
        message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
private String userPwd;

@Email(message = "올바른 이메일 형식이 아닙니다")
private String email;

@Min(value = 14, message = "나이는 14세 이상이어야 합니다")
@Max(value = 120, message = "나이는 120세 이하여야 합니다")
private Integer age;

@Pattern(regexp = "^01[0-9]-?[0-9]{3,4}-?[0-9]{4}$", message = "올바른 전화번호 형식이 아닙니다")
private String phone;
```

### BoardDto 신규 추가
**Request (게시글 작성)**
```java
@NotBlank(message = "게시글 제목은 필수입니다")
@Size(min = 2, max = 100, message = "게시글 제목은 2자 이상 100자 이하여야 합니다")
private String boardTitle;

@NotBlank(message = "게시글 내용은 필수입니다")
@Size(min = 10, max = 5000, message = "게시글 내용은 10자 이상 5000자 이하여야 합니다")
private String boardContent;

@NotBlank(message = "작성자 ID는 필수입니다")
private String userId;
```

**UpdateRequest (게시글 수정)**
```java
@Size(min = 2, max = 100, message = "게시글 제목은 2자 이상 100자 이하여야 합니다")
private String boardTitle;

@Size(min = 10, max = 5000, message = "게시글 내용은 10자 이상 5000자 이하여야 합니다")
private String boardContent;
```

### Controller 개선

**MemberController**
```java
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Validated  // PathVariable, RequestParam 검증 활성화
public class MemberController {

    @PostMapping
    public ResponseEntity<String> registerMember(
            @Valid @RequestBody MemberDto.Request request) {  // RequestBody 검증
        // ...
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberDto.Response> getMemberById(
            @PathVariable @NotBlank(message = "사용자 ID는 필수입니다") String userId) {
        // ...
    }
}
```

**BoardController**
```java
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Validated
public class BoardController {

    @PostMapping
    public ResponseEntity<Long> createBoard(
            @Valid @RequestBody BoardDto.Request request,  // 기존 @RequestParam에서 변경
            @RequestParam(value = "file", required = false) MultipartFile file) {
        // ...
    }

    @GetMapping
    public ResponseEntity<PageResponse<BoardDto.Response>> getAllBoards(
            @RequestParam(defaultValue = "0") 
            @PositiveOrZero(message = "페이지 번호는 0 이상이어야 합니다") int page,
            @RequestParam(defaultValue = "5") 
            @Positive(message = "페이지 크기는 1 이상이어야 합니다") int size) {
        // ...
    }
}
```

### GlobalExceptionHandler (신규)

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 검증 실패 처리 (RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse response = ErrorResponse.of("입력값 검증에 실패했습니다", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // @Validated 검증 실패 처리 (PathVariable, RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex) {
        // ...
    }
}
```

---

## ✅ 검증 규칙 요약

### 회원 (Member)
| 필드 | 검증 규칙 | 비고 |
|------|-----------|------|
| userId | 필수, 4~20자, 영문/숫자/언더스코어만 | 회원가입 시 필수 |
| userPwd | 필수, 8~20자, 영문+숫자+특수문자 조합 | 회원가입 시 필수 |
| userName | 필수, 2~20자 | 회원가입 시 필수 |
| email | 이메일 형식 | 선택사항 |
| age | 14~120세 | 선택사항 |
| phone | 휴대폰 번호 형식 (010-1234-5678) | 선택사항 |
| address | 최대 200자 | 선택사항 |

### 게시글 (Board)
| 필드 | 검증 규칙 | 비고 |
|------|-----------|------|
| boardTitle | 필수, 2~100자 | 작성 시 필수 |
| boardContent | 필수, 10~5000자 | 작성 시 필수 |
| userId | 필수 | 작성 시 필수 |
| tags | - | 선택사항 |

### 페이징 (Pagination)
| 파라미터 | 검증 규칙 |
|----------|-----------|
| page | 0 이상 |
| size | 1 이상 |

---

## 🧪 테스트 방법

### 1. 회원가입 검증 테스트

**잘못된 요청**
```bash
POST http://localhost:8080/api/member
Content-Type: application/json

{
  "user_id": "ab",
  "user_pwd": "1234",
  "user_name": "홍"
}
```

**예상 응답 (400 Bad Request)**
```json
{
  "success": false,
  "message": "입력값 검증에 실패했습니다",
  "errors": {
    "userId": "사용자 ID는 4자 이상 20자 이하여야 합니다",
    "userPwd": "비밀번호는 8자 이상 20자 이하여야 합니다",
    "userName": "사용자 이름은 2자 이상 20자 이하여야 합니다"
  },
  "timestamp": "2025-12-29T10:30:00"
}
```

**올바른 요청**
```bash
POST http://localhost:8080/api/member
Content-Type: application/json

{
  "user_id": "john_doe",
  "user_pwd": "Password123!",
  "user_name": "홍길동",
  "email": "john@example.com",
  "age": 25,
  "phone": "010-1234-5678"
}
```

### 2. 게시글 작성 검증 테스트

**잘못된 요청**
```bash
POST http://localhost:8080/api/board
Content-Type: application/json

{
  "board_title": "안",
  "board_content": "짧음"
}
```

**예상 응답 (400 Bad Request)**
```json
{
  "success": false,
  "message": "입력값 검증에 실패했습니다",
  "errors": {
    "boardTitle": "게시글 제목은 2자 이상 100자 이하여야 합니다",
    "boardContent": "게시글 내용은 10자 이상 5000자 이하여야 합니다",
    "userId": "작성자 ID는 필수입니다"
  },
  "timestamp": "2025-12-29T10:30:00"
}
```

**올바른 요청**
```bash
POST http://localhost:8080/api/board
Content-Type: application/json

{
  "board_title": "안녕하세요",
  "board_content": "게시글 내용입니다. 최소 10자 이상 작성해야 합니다.",
  "user_id": "john_doe",
  "tags": ["공지", "이벤트"]
}
```

### 3. PathVariable 검증 테스트

**잘못된 요청**
```bash
GET http://localhost:8080/api/board/-1
```

**예상 응답 (400 Bad Request)**
```json
{
  "success": false,
  "message": "요청 파라미터 검증에 실패했습니다",
  "errors": {
    "getBoardById.boardId": "게시글 ID는 양수여야 합니다"
  },
  "timestamp": "2025-12-29T10:30:00"
}
```

---

## 🎯 적용 효과

### 1. 데이터 품질 개선
- 잘못된 데이터가 DB에 저장되는 것을 사전 차단
- 비즈니스 로직 실행 전 입력값 검증 완료

### 2. 코드 가독성 향상
- Controller에서 수동 검증 로직 제거
- DTO 어노테이션만으로 검증 규칙 명확히 표현

### 3. 일관된 에러 응답
- 모든 API에서 동일한 에러 응답 형식 제공
- 프론트엔드 에러 처리 로직 단순화

### 4. 개발 생산성 향상
- 반복적인 검증 코드 작성 불필요
- 선언적 검증으로 유지보수 용이

---

## 📌 주의사항

1. **@Validated 필수**
   - Controller에 `@Validated` 어노테이션 추가 필요
   - PathVariable, RequestParam 검증 시 필수

2. **@Valid vs @Validated**
   - `@Valid`: RequestBody 검증
   - `@Validated`: PathVariable, RequestParam 검증

3. **메시지 한글화**
   - 현재 한국어 메시지 하드코딩
   - 필요 시 `messages.properties`로 국제화 가능

4. **파일 업로드**
   - MultipartFile은 별도 검증 필요
   - 현재는 required=false로 선택사항 처리

---

## 📚 참고 문서

- 상세 가이드: `VALIDATION_GUIDE.md` 참조
- Jakarta Bean Validation: https://jakarta.ee/specifications/bean-validation/
- Spring Validation: https://docs.spring.io/spring-framework/reference/core/validation/

---

**작성일**: 2025-12-29  
**적용 버전**: Spring Boot 3.5.9, Jakarta Validation 3.0

