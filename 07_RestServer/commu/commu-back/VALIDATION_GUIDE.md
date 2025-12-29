# Spring Boot Validation 적용 가이드

## 개요
commu-back 프로젝트에 Spring Boot Validation을 적용하여 데이터 무결성과 API 안정성을 확보했습니다.

---

## 적용 순서

### 1. 의존성 확인

`build.gradle`에 validation 의존성이 이미 포함되어 있습니다.

```gradle
implementation 'org.springframework.boot:spring-boot-starter-validation'
```

> Spring Boot 3.x부터는 `jakarta.validation` 패키지를 사용합니다.

---

### 2. DTO 검증 어노테이션 추가

#### 2.1 MemberDto 검증 강화

**Request (회원가입)**
- `userId`: 4~20자, 영문/숫자/언더스코어만 허용
- `userPwd`: 8~20자, 영문+숫자+특수문자 조합 필수
- `userName`: 2~20자 필수
- `email`: 이메일 형식 검증
- `age`: 14~120세
- `phone`: 휴대폰 번호 형식 검증
- `address`: 최대 200자

**UpdateRequest (회원정보 수정)**
- 필수값 제외, 형식 검증만 적용

```java
@JsonProperty("user_id")
@NotBlank(message = "사용자 ID는 필수입니다")
@Size(min = 4, max = 20, message = "사용자 ID는 4자 이상 20자 이하여야 합니다")
@Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "사용자 ID는 영문, 숫자, 언더스코어만 사용 가능합니다")
private String userId;
```

#### 2.2 BoardDto 검증 추가

새로운 Request/UpdateRequest DTO를 생성했습니다.

**Request (게시글 작성)**
- `boardTitle`: 2~100자 필수
- `boardContent`: 10~5000자 필수
- `userId`: 작성자 ID 필수
- `tags`: 선택사항

**UpdateRequest (게시글 수정)**
- 모든 필드 선택사항, 형식 검증만 적용

---

### 3. Controller 검증 적용

#### 3.1 `@Validated` 추가

Controller 클래스 레벨에 `@Validated`를 추가하여 PathVariable, RequestParam 검증을 활성화합니다.

```java
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Validated
public class MemberController {
```

#### 3.2 메서드별 검증

**RequestBody 검증**
```java
@PostMapping
public ResponseEntity<String> registerMember(@Valid @RequestBody MemberDto.Request request) {
```

**PathVariable 검증**
```java
@GetMapping("/{userId}")
public ResponseEntity<MemberDto.Response> getMemberById(
        @PathVariable @NotBlank(message = "사용자 ID는 필수입니다") String userId) {
```

**RequestParam 검증**
```java
@GetMapping
public ResponseEntity<PageResponse<BoardDto.Response>> getAllBoards(
        @RequestParam(defaultValue = "0") @PositiveOrZero(message = "페이지 번호는 0 이상이어야 합니다") int page,
        @RequestParam(defaultValue = "5") @Positive(message = "페이지 크기는 1 이상이어야 합니다") int size) {
```

---

### 4. 전역 예외 처리

#### 4.1 GlobalExceptionHandler 생성

`global.exception` 패키지에 전역 예외 처리기를 추가했습니다.

**처리하는 예외**
1. `MethodArgumentNotValidException`: @Valid 검증 실패 (RequestBody)
2. `ConstraintViolationException`: @Validated 검증 실패 (PathVariable, RequestParam)
3. `Exception`: 기타 예외

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .data(errors)
                .error("입력값 검증에 실패했습니다")
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
```

#### 4.2 ErrorResponse DTO 생성

표준화된 에러 응답 형식을 제공합니다.

```java
{
  "success": false,
  "message": "입력값 검증에 실패했습니다",
  "errors": {
    "userId": "사용자 ID는 4자 이상 20자 이하여야 합니다",
    "userPwd": "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다"
  },
  "timestamp": "2025-12-29T10:30:00"
}
```

---

## 주요 검증 어노테이션

### 기본 검증
- `@NotNull`: null 불가
- `@NotBlank`: null, 빈 문자열, 공백만 있는 문자열 불가
- `@NotEmpty`: null, 빈 컬렉션/배열 불가

### 문자열 검증
- `@Size(min, max)`: 길이 제한
- `@Pattern(regexp)`: 정규표현식 패턴 매칭
- `@Email`: 이메일 형식

### 숫자 검증
- `@Min(value)`: 최솟값
- `@Max(value)`: 최댓값
- `@Positive`: 양수만
- `@PositiveOrZero`: 0 또는 양수
- `@Negative`: 음수만
- `@NegativeOrZero`: 0 또는 음수

### 날짜/시간 검증
- `@Past`: 과거 날짜만
- `@Future`: 미래 날짜만
- `@PastOrPresent`: 과거 또는 현재
- `@FutureOrPresent`: 미래 또는 현재

---

## 테스트 예시

### 1. 회원가입 검증 실패

**요청**
```json
POST /api/member
{
  "user_id": "ab",
  "user_pwd": "1234",
  "user_name": "홍길동"
}
```

**응답 (400 Bad Request)**
```json
{
  "success": false,
  "data": {
    "userId": "사용자 ID는 4자 이상 20자 이하여야 합니다",
    "userPwd": "비밀번호는 8자 이상 20자 이하여야 합니다"
  },
  "error": "입력값 검증에 실패했습니다"
}
```

### 2. 게시글 작성 검증 실패

**요청**
```json
POST /api/board
{
  "board_title": "안",
  "board_content": "짧음"
}
```

**응답 (400 Bad Request)**
```json
{
  "success": false,
  "data": {
    "boardTitle": "게시글 제목은 2자 이상 100자 이하여야 합니다",
    "boardContent": "게시글 내용은 10자 이상 5000자 이하여야 합니다",
    "userId": "작성자 ID는 필수입니다"
  },
  "error": "입력값 검증에 실패했습니다"
}
```

### 3. PathVariable 검증 실패

**요청**
```
GET /api/board/-1
```

**응답 (400 Bad Request)**
```json
{
  "success": false,
  "data": {
    "getBoardById.boardId": "게시글 ID는 양수여야 합니다"
  },
  "error": "요청 파라미터 검증에 실패했습니다"
}
```

---

## 적용 효과

### 1. 데이터 무결성 보장
- 잘못된 형식의 데이터가 DB에 저장되는 것을 사전에 차단
- 비즈니스 로직 실행 전 입력값 검증 완료

### 2. 개발 생산성 향상
- Controller에서 수동 검증 로직 제거
- 선언적 검증으로 코드 가독성 향상

### 3. 일관된 에러 응답
- 전역 예외 처리로 모든 API에서 동일한 에러 형식 제공
- 프론트엔드에서 에러 처리 로직 단순화

### 4. API 문서화 개선
- DTO의 validation 어노테이션이 API 명세 역할 수행
- 각 필드의 제약사항이 명확히 표현됨

---

## 주의사항

### 1. @Valid vs @Validated
- `@Valid`: 표준 JSR-303, RequestBody 검증
- `@Validated`: Spring 확장, PathVariable/RequestParam 검증 가능
- Controller에 `@Validated` 추가 시 메서드 파라미터 검증 활성화

### 2. 검증 순서
1. Controller 진입 전: PathVariable, RequestParam 검증
2. Controller 진입 시: @Valid RequestBody 검증
3. 검증 실패 시 즉시 예외 발생, 비즈니스 로직 미실행

### 3. 메시지 국제화
- 현재는 한국어 메시지 하드코딩
- 필요시 `messages.properties`로 분리 가능

### 4. 그룹 검증
- 동일 DTO를 여러 API에서 다른 검증 규칙으로 사용 시
- `groups` 속성으로 검증 그룹 분리 가능

---

## 추가 개선 가능 항목

1. **커스텀 Validator 작성**
   - 비즈니스 로직 기반 복잡한 검증 (예: 중복 ID 체크)
   
2. **메시지 국제화**
   - `messages.properties` 활용한 다국어 지원

3. **검증 그룹화**
   - 등록/수정 시 다른 검증 규칙 적용

4. **필드 레벨 검증 강화**
   - 파일 업로드 크기/형식 검증
   - 복합 조건 검증 (예: 시작일 < 종료일)

---

## 참고 자료

- [Jakarta Bean Validation 공식 문서](https://jakarta.ee/specifications/bean-validation/3.0/)
- [Spring Validation 가이드](https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html)
- [Hibernate Validator 문서](https://hibernate.org/validator/)

