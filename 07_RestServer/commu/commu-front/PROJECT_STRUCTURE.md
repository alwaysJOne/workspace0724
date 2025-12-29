# 프로젝트 구조 및 기술 스택 문서

## 📋 목차
1. [기술 스택](#기술-스택)
2. [프로젝트 구조](#프로젝트-구조)
3. [주요 라이브러리 설명](#주요-라이브러리-설명)
4. [폴더 구조 상세](#폴더-구조-상세)
5. [개발 환경 설정](#개발-환경-설정)
6. [코드 패턴 및 컨벤션](#코드-패턴-및-컨벤션)

---

## 기술 스택

### 핵심 프레임워크 및 라이브러리
- **React 19.1.1** - UI 라이브러리
- **Vite 7.1.0** - 빌드 도구 및 개발 서버
- **Styled-Components 6.1.19** - CSS-in-JS 스타일링

### 상태 관리 및 라우팅
- **Zustand 5.0.7** - 경량 상태 관리 라이브러리
  - `persist` 미들웨어를 사용한 LocalStorage 연동
- **React Router DOM 7.8.1** - 클라이언트 사이드 라우팅

### HTTP 통신
- **Axios 1.11.0** - HTTP 클라이언트
  - Request/Response 인터셉터 구현
  - 토큰 자동 첨부
  - 에러 핸들링 표준화

### 에디터 (선택사항)
- **Tiptap 3.10.7** - WYSIWYG 에디터
  - `@tiptap/react` - React 통합
  - `@tiptap/starter-kit` - 기본 확장 기능
  - `@tiptap/extension-code-block-lowlight` - 코드 블록 하이라이팅
  - `@tiptap/extension-image` - 이미지 삽입
  - `@tiptap/extension-link` - 링크 삽입
  - `@tiptap/extension-placeholder` - 플레이스홀더
  - `lowlight 3.3.0` - 코드 구문 강조

### UI 라이브러리
- **React Icons 5.5.0** - 아이콘 라이브러리

### 개발 도구
- **ESLint 9.33.0** - 코드 린팅
  - `eslint-plugin-react` - React 린팅 규칙
  - `eslint-plugin-react-hooks` - React Hooks 린팅
  - `eslint-plugin-react-refresh` - React Refresh 지원
  - `eslint-config-prettier` - Prettier와 통합
  - `eslint-plugin-prettier` - Prettier 린팅 규칙
- **Prettier 3.6.2** - 코드 포매팅

### 환경 변수
- **dotenv 17.2.1** - 환경 변수 관리

---

## 프로젝트 구조

```
front/
├── public/                          # 정적 파일
│   ├── alpha_favicon.ico
│   └── logo.png
│
├── src/
│   ├── api/                         # API 관련
│   │   ├── axios.js                 # Axios 인스턴스 및 인터셉터
│   │   ├── config.js                # API 설정 및 엔드포인트
│   │   ├── services.js              # API 서비스 함수
│   │   └── index.js
│   │
│   ├── components/                  # 재사용 가능한 컴포넌트
│   │   ├── common/                  # 공통 컴포넌트
│   │   │   └── Modal/               # 모달 컴포넌트
│   │   │       ├── Modal.jsx
│   │   │       ├── Alert.jsx
│   │   │       ├── Confirm.jsx
│   │   │       ├── Modal.styled.js
│   │   │       └── index.js
│   │   │
│   │   ├── Layout/                  # 레이아웃 컴포넌트
│   │   │   ├── Layout.jsx
│   │   │   ├── Layout.styled.js
│   │   │   └── index.js
│   │   │
│   │   ├── Header/                  # 헤더 컴포넌트
│   │   │   ├── Header.jsx
│   │   │   ├── Header.styled.js
│   │   │   └── index.js
│   │   │
│   │   ├── Footer/                  # 푸터 컴포넌트
│   │   │   ├── Footer.jsx
│   │   │   ├── Footer.styled.js
│   │   │   └── index.js
│   │   │
│   │   ├── BottomNavigation/        # 하단 네비게이션 (모바일)
│   │   │   ├── BottomNavigation.jsx
│   │   │   ├── BottomNavigation.styled.js
│   │   │   └── index.js
│   │   │
│   │   ├── ProtectedRoute/          # 인증 라우트 보호
│   │   │   ├── ProtectedRoute.jsx
│   │   │   └── index.js
│   │   │
│   │   └── TiptapEditor/            # 에디터 컴포넌트 (선택사항)
│   │       ├── TiptapEditor.jsx
│   │       ├── TiptapEditor.styled.js
│   │       └── index.js
│   │
│   ├── pages/                       # 페이지 컴포넌트
│   │   ├── HomePage/
│   │   │   ├── HomePage.jsx
│   │   │   ├── HomePage.styled.js
│   │   │   └── index.js
│   │   │
│   │   ├── LoginPage/
│   │   │   ├── LoginPage.jsx
│   │   │   ├── LoginPage.styled.js
│   │   │   └── index.js
│   │   │
│   │   └── ...                      # 기타 페이지들
│   │
│   ├── store/                       # 상태 관리 (Zustand)
│   │   └── authStore.js             # 인증 상태 관리
│   │
│   ├── hooks/                       # Custom Hooks
│   │   ├── useModal.js              # 모달 관리 훅
│   │   └── useInput.js              # 입력 관리 훅
│   │
│   ├── styles/                      # 스타일 관련
│   │   ├── GlobalStyle.js           # 전역 스타일
│   │   ├── theme.js                 # 테마 설정
│   │   ├── mixins.js                # 재사용 가능한 스타일 믹스인
│   │   ├── commonPageStyles.js      # 공통 페이지 스타일
│   │   └── MediaQueries.js          # 반응형 미디어 쿼리
│   │
│   ├── utils/                       # 유틸리티 함수
│   │   ├── imageHelper.js
│   │   └── passwordValidator.js
│   │
│   ├── assets/                      # 정적 리소스
│   │   └── icons/
│   │       └── google_login.svg
│   │
│   ├── App.jsx                      # 루트 컴포넌트
│   └── main.jsx                     # 엔트리 포인트
│
├── eslint.config.js                 # ESLint 설정
├── vite.config.js                   # Vite 설정
├── package.json                     # 프로젝트 의존성
└── index.html                       # HTML 템플릿
```

---

## 주요 라이브러리 설명

### 1. **Vite (빌드 도구)**
- **역할**: 빠른 개발 서버와 최적화된 프로덕션 빌드 제공
- **장점**: 
  - HMR(Hot Module Replacement) 지원으로 개발 속도 향상
  - ESM 기반으로 빠른 빌드 시간
  - 플러그인 시스템으로 확장 가능

**설정 예시** (`vite.config.js`):
```javascript
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    host: true,
    open: true
  }
})
```

### 2. **Styled-Components (CSS-in-JS)**
- **역할**: JavaScript 내에서 CSS를 작성하고 컴포넌트 스타일링
- **장점**:
  - 컴포넌트 단위의 스타일 캡슐화
  - 동적 스타일링 (props 기반)
  - 테마 지원 (ThemeProvider)
  - 자동 벤더 프리픽스

**사용 패턴**:
```javascript
// ThemeProvider로 전역 테마 제공
<ThemeProvider theme={theme}>
  <GlobalStyle />
  <App />
</ThemeProvider>
```

### 3. **Zustand (상태 관리)**
- **역할**: 간단하고 확장 가능한 전역 상태 관리
- **장점**:
  - Redux보다 간단한 API
  - 보일러플레이트 코드 최소화
  - TypeScript 지원 우수
  - 미들웨어 지원 (persist 등)

**사용 패턴**:
```javascript
const useAuthStore = create(
  persist(
    (set, get) => ({
      user: null,
      token: null,
      login: (token) => set({ token, isAuthenticated: true }),
      logout: () => set({ user: null, token: null })
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({ token: state.token, user: state.user })
    }
  )
)
```

### 4. **Axios (HTTP 클라이언트)**
- **역할**: HTTP 요청 처리 및 API 통신
- **주요 기능**:
  - Request/Response 인터셉터로 공통 로직 처리
  - 자동 토큰 첨부
  - 에러 핸들링 표준화
  - 응답 데이터 변환

**인터셉터 패턴**:
```javascript
// Request 인터셉터 - 토큰 자동 첨부
api.interceptors.request.use((config) => {
  const token = getAuthStore().token;
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response 인터셉터 - 응답 통일 및 에러 처리
api.interceptors.response.use(
  (response) => response.data.data, // 성공 시 data만 반환
  (error) => {
    // 401 에러 시 자동 로그아웃
    if (error.response?.status === 401) {
      getAuthStore().logout();
      window.location.href = '/login';
    }
    return Promise.reject(standardizeError(error));
  }
);
```

### 5. **React Router DOM (라우팅)**
- **역할**: 클라이언트 사이드 라우팅 및 네비게이션
- **주요 기능**:
  - 동적 라우팅
  - 중첩 라우트
  - Protected Routes (인증 필요 라우트)
  - URL 파라미터 및 쿼리 스트링 처리

**사용 패턴**:
```javascript
<Router>
  <Routes>
    <Route path="/" element={<HomePage />} />
    <Route path="/login" element={<LoginPage />} />
    <Route path="/profile" element={
      <ProtectedRoute>
        <ProfilePage />
      </ProtectedRoute>
    } />
  </Routes>
</Router>
```

### 6. **Tiptap (WYSIWYG 에디터) - 선택사항**
- **역할**: 리치 텍스트 에디터 제공
- **장점**:
  - Headless 아키텍처로 완전한 커스터마이징 가능
  - ProseMirror 기반의 강력한 에디터 엔진
  - 다양한 확장 기능 (이미지, 링크, 코드 블록 등)
  - React 통합 우수

---

## 폴더 구조 상세

### 📁 `src/api/`
**목적**: API 통신 관련 로직 중앙화

- **`axios.js`**: Axios 인스턴스 생성 및 인터셉터 설정
- **`config.js`**: API 기본 URL, 타임아웃, 엔드포인트 상수 정의
- **`services.js`**: API 호출 함수들을 도메인별로 그룹화
- **`index.js`**: API 모듈 내보내기

**패턴**:
```javascript
// services.js 예시
export const authService = {
  login: (credentials) => api.post(API_ENDPOINTS.AUTH.LOGIN, credentials),
  logout: () => api.post(API_ENDPOINTS.AUTH.LOGOUT),
  getUserInfo: () => api.get(API_ENDPOINTS.USER.ME)
};
```

### 📁 `src/components/`
**목적**: 재사용 가능한 UI 컴포넌트

**구조 원칙**:
- 각 컴포넌트는 독립된 폴더에 위치
- `.jsx`, `.styled.js`, `index.js` 파일로 구성
- `index.js`를 통한 깔끔한 import 경로

**폴더 구조**:
```
ComponentName/
├── ComponentName.jsx         # 컴포넌트 로직
├── ComponentName.styled.js   # 스타일 정의
└── index.js                  # export { default } from './ComponentName'
```

**특수 컴포넌트**:
- **`common/`**: 프로젝트 전반에 사용되는 범용 컴포넌트
- **`Layout/`**: 페이지 레이아웃 래퍼
- **`ProtectedRoute/`**: 인증이 필요한 라우트 보호

### 📁 `src/pages/`
**목적**: 라우트에 매핑되는 페이지 컴포넌트

**구조**: `components/`와 동일한 폴더 구조 사용

**명명 규칙**: `[Name]Page/`

### 📁 `src/store/`
**목적**: Zustand를 사용한 전역 상태 관리

**파일 구조**:
- 도메인별로 스토어 파일 분리 (예: `authStore.js`, `userStore.js`)
- persist 미들웨어로 LocalStorage 연동

**주요 기능**:
- 상태 정의
- 액션 함수
- LocalStorage 자동 동기화

### 📁 `src/hooks/`
**목적**: 재사용 가능한 Custom Hooks

**예시**:
- **`useModal.js`**: 모달 상태 관리 (open/close, props 전달)
- **`useInput.js`**: 폼 입력 상태 관리
- **`useAlert.js`**: Alert 모달 전용 훅
- **`useConfirm.js`**: Confirm 모달 전용 훅

**Hook 패턴**:
```javascript
export const useModal = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [modalProps, setModalProps] = useState({});
  
  const openModal = (props) => {
    setModalProps(props);
    setIsOpen(true);
  };
  
  const closeModal = () => setIsOpen(false);
  
  return { isOpen, openModal, closeModal, modalProps };
};
```

### 📁 `src/styles/`
**목적**: 스타일 관련 설정 및 유틸리티

**주요 파일**:

#### `GlobalStyle.js`
- CSS Reset
- 전역 스타일 정의
- 스크롤바 커스터마이징
- 접근성 스타일

#### `theme.js`
- 색상 팔레트
- 폰트 설정 (family, size, weight, lineHeight)
- 간격(spacing) 시스템
- Border Radius
- 그림자(shadows)
- Breakpoints (반응형)
- z-index 레벨
- Transition 속도

**테마 구조**:
```javascript
const theme = {
  colors: { primary, gray, success, warning, danger, ... },
  fonts: { family, size, weight, lineHeight },
  spacing: { 0: '0', 1: '0.25rem', 2: '0.5rem', ... },
  borderRadius: { sm, base, md, lg, xl, full },
  shadows: { sm, base, md, lg, xl, '2xl', inner },
  breakpoints: { xs, sm, md, lg, xl, '2xl' },
  zIndex: { dropdown, sticky, fixed, modal, popover, tooltip },
  transitions: { fast, base, slow }
};
```

#### `mixins.js`
- 재사용 가능한 스타일 패턴
- Flexbox 레이아웃 (flexCenter, flexBetween, flexColumn)
- 텍스트 처리 (truncate, lineClamp)
- 버튼 스타일 (buttonPrimary, buttonSecondary, buttonOutline)
- 입력 필드 스타일 (inputBase)
- 카드 스타일 (card, cardHover)
- 애니메이션 (fadeIn, slideUp)
- 레이아웃 (container, srOnly)
- 스켈레톤 로딩

**믹스인 사용 예시**:
```javascript
const Button = styled.button`
  ${buttonPrimary}
  padding: ${props => props.theme.spacing[4]};
`;

const Card = styled.div`
  ${cardHover}
  ${fadeIn}
`;
```

#### `MediaQueries.js`
- 반응형 미디어 쿼리 유틸리티
- 일관된 breakpoint 사용

#### `commonPageStyles.js`
- 페이지 간 공통 스타일
- 레이아웃 패턴

### 📁 `src/utils/`
**목적**: 비즈니스 로직과 무관한 순수 유틸리티 함수

**예시**:
- `imageHelper.js`: 이미지 처리 함수
- `passwordValidator.js`: 비밀번호 유효성 검사
- `dateFormatter.js`: 날짜 포맷팅
- `textUtils.js`: 텍스트 변환 함수

---

## 개발 환경 설정

### 1. **초기 설정**

```bash
# 프로젝트 생성
npm create vite@latest project-name -- --template react

# 의존성 설치
npm install

# 개발 서버 실행
npm run dev
```

### 2. **필수 라이브러리 설치**

```bash
# 핵심 라이브러리
npm install react-router-dom zustand axios styled-components react-icons

# Zustand persist 미들웨어는 기본 포함됨 (zustand/middleware)

# 환경 변수
npm install dotenv
```

### 3. **개발 도구 설치**

```bash
# ESLint & Prettier
npm install -D eslint prettier eslint-config-prettier eslint-plugin-prettier
npm install -D eslint-plugin-react eslint-plugin-react-hooks eslint-plugin-react-refresh
npm install -D @eslint/js globals
```

### 4. **Tiptap 설치 (선택사항)**

```bash
npm install @tiptap/react @tiptap/starter-kit
npm install @tiptap/extension-image @tiptap/extension-link
npm install @tiptap/extension-placeholder @tiptap/extension-code-block-lowlight
npm install lowlight
```

### 5. **환경 변수 설정**

프로젝트 루트에 `.env` 파일 생성:

```env
# API 설정
VITE_API_URL=http://localhost:8001
VITE_API_TIMEOUT=5000
VITE_API_VERSION=v1
```

### 6. **NPM Scripts**

```json
{
  "scripts": {
    "dev": "vite",
    "build": "vite build",
    "preview": "vite preview",
    "lint": "eslint . --ext .js,.jsx",
    "lint:fix": "eslint . --ext .js,.jsx --fix",
    "format": "prettier --write \"**/*.{js,jsx,json,md}\""
  }
}
```

---

## 코드 패턴 및 컨벤션

### 1. **컴포넌트 구조**

```javascript
// ComponentName.jsx
import { useState, useEffect } from 'react';
import * as S from './ComponentName.styled';

const ComponentName = ({ prop1, prop2 }) => {
  // 1. State 선언
  const [state, setState] = useState(initialValue);
  
  // 2. Hooks
  const customHook = useCustomHook();
  
  // 3. Side Effects
  useEffect(() => {
    // effect logic
  }, [dependencies]);
  
  // 4. Event Handlers
  const handleClick = () => {
    // handler logic
  };
  
  // 5. Render
  return (
    <S.Container>
      <S.Title>{prop1}</S.Title>
      <S.Button onClick={handleClick}>{prop2}</S.Button>
    </S.Container>
  );
};

export default ComponentName;
```

### 2. **스타일 컴포넌트 구조**

```javascript
// ComponentName.styled.js
import styled from 'styled-components';
import { flexCenter, buttonPrimary } from '../../styles/mixins';

export const Container = styled.div`
  ${flexCenter}
  padding: ${props => props.theme.spacing[4]};
  background: ${props => props.theme.colors.white};
`;

export const Title = styled.h2`
  font-size: ${props => props.theme.fonts.size['2xl']};
  font-weight: ${props => props.theme.fonts.weight.bold};
  color: ${props => props.theme.colors.gray[900]};
`;

export const Button = styled.button`
  ${buttonPrimary}
`;
```

### 3. **API 서비스 패턴**

```javascript
// services.js
import api from './axios';
import { API_ENDPOINTS } from './config';

// 도메인별로 그룹화
export const authService = {
  login: (credentials) => 
    api.post(API_ENDPOINTS.AUTH.LOGIN, credentials),
  
  logout: () => 
    api.post(API_ENDPOINTS.AUTH.LOGOUT),
  
  getUserInfo: () => 
    api.get(API_ENDPOINTS.USER.ME)
};

export const userService = {
  updateProfile: (data) => 
    api.put(API_ENDPOINTS.USER.ME, data),
  
  changePassword: (passwords) => 
    api.put(API_ENDPOINTS.USER.UPDATE_PASSWORD, passwords)
};
```

### 4. **Store 패턴 (Zustand)**

```javascript
// authStore.js
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

const useAuthStore = create(
  persist(
    (set, get) => ({
      // State
      user: null,
      token: null,
      isAuthenticated: false,
      
      // Actions
      login: (token) => set({ token, isAuthenticated: true }),
      logout: () => set({ user: null, token: null, isAuthenticated: false }),
      setUser: (user) => set({ user }),
      
      // Computed
      checkAuth: () => !!get().token
    }),
    {
      name: 'auth-storage',
      partialize: (state) => ({
        token: state.token,
        user: state.user,
        isAuthenticated: state.isAuthenticated
      })
    }
  )
);

export default useAuthStore;
```

### 5. **Custom Hook 패턴**

```javascript
// useCustomHook.js
import { useState, useEffect, useCallback } from 'react';

export const useCustomHook = (initialValue) => {
  const [state, setState] = useState(initialValue);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  
  const fetchData = useCallback(async () => {
    setLoading(true);
    setError(null);
    
    try {
      const data = await apiCall();
      setState(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, []);
  
  useEffect(() => {
    fetchData();
  }, [fetchData]);
  
  return { state, loading, error, refetch: fetchData };
};
```

### 6. **Protected Route 패턴**

```javascript
// ProtectedRoute.jsx
import { Navigate } from 'react-router-dom';
import useAuthStore from '../../store/authStore';

const ProtectedRoute = ({ children }) => {
  const { isAuthenticated } = useAuthStore();
  
  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }
  
  return children;
};

export default ProtectedRoute;
```

### 7. **에러 처리 패턴**

```javascript
// Component에서 에러 처리
const handleSubmit = async (data) => {
  try {
    await authService.login(data);
    navigate('/dashboard');
  } catch (error) {
    // Axios 인터셉터에서 표준화된 에러 메시지
    const errorMessage = error.response?.data?.message || '오류가 발생했습니다.';
    showAlert(errorMessage, { variant: 'danger' });
  }
};
```

### 8. **폴더별 index.js 패턴**

```javascript
// components/ComponentName/index.js
export { default } from './ComponentName';

// pages/HomePage/index.js
export { default } from './HomePage';

// api/index.js
export { default as api } from './axios';
export * from './services';
export * from './config';
```

---

## 프로젝트 시작 체크리스트

### 초기 설정
- [ ] Vite로 React 프로젝트 생성
- [ ] 필수 라이브러리 설치 (react-router-dom, zustand, axios, styled-components)
- [ ] 폴더 구조 생성 (api, components, pages, store, hooks, styles, utils)
- [ ] 환경 변수 파일 (.env) 생성

### 스타일 설정
- [ ] `theme.js` 작성 (colors, fonts, spacing 등)
- [ ] `GlobalStyle.js` 작성
- [ ] `mixins.js` 작성 (공통 스타일 패턴)
- [ ] `main.jsx`에 ThemeProvider 설정

### API 설정
- [ ] `axios.js` - Axios 인스턴스 생성
- [ ] Request/Response 인터셉터 설정
- [ ] `config.js` - API 엔드포인트 정의
- [ ] `services.js` - API 서비스 함수 작성

### 라우팅 설정
- [ ] `App.jsx`에 React Router 설정
- [ ] 기본 라우트 정의
- [ ] ProtectedRoute 컴포넌트 생성

### 상태 관리
- [ ] `authStore.js` - 인증 상태 관리 스토어 생성
- [ ] Zustand persist 미들웨어 설정
- [ ] Axios 인터셉터와 authStore 연동

### 공통 컴포넌트
- [ ] Layout 컴포넌트
- [ ] Header 컴포넌트
- [ ] Footer 컴포넌트
- [ ] Modal 컴포넌트 (Alert, Confirm)
- [ ] ProtectedRoute 컴포넌트

### 개발 도구
- [ ] ESLint 설정
- [ ] Prettier 설정
- [ ] Git ignore 설정

---

## 참고사항

### 장점
- **모듈화**: 각 기능이 독립적인 모듈로 구성되어 유지보수 용이
- **확장성**: 새로운 페이지나 기능 추가 시 기존 패턴 재사용 가능
- **타입 안정성**: 일관된 API 응답 처리 및 에러 핸들링
- **개발 속도**: 재사용 가능한 컴포넌트와 훅으로 빠른 개발 가능
- **성능**: Vite의 빠른 빌드와 HMR로 개발 경험 향상

### 주의사항
- 환경 변수는 반드시 `VITE_` 접두사 사용
- LocalStorage에 저장되는 민감한 정보 주의 (토큰 등)
- Axios 인터셉터에서 순환 참조 방지 (authStore)
- 전역 상태는 필요한 경우에만 사용 (과도한 전역 상태 지양)

### 확장 가능한 부분
- **다국어 지원**: i18next 라이브러리 추가
- **테스트**: Vitest, React Testing Library 추가
- **애니메이션**: Framer Motion 추가
- **폼 관리**: React Hook Form 추가
- **데이터 패칭**: React Query (TanStack Query) 추가
- **모니터링**: Sentry 추가

---

**문서 작성일**: 2025년 12월  
**프로젝트 버전**: 0.0.0  
**작성자**: 개발 문서화 시스템

