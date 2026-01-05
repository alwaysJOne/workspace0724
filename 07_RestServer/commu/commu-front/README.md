# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react) uses [Babel](https://babeljs.io/) (or [oxc](https://oxc.rs) when used in [rolldown-vite](https://vite.dev/guide/rolldown)) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## React Compiler

The React Compiler is not enabled on this template because of its impact on dev & build performances. To add it, see [this documentation](https://react.dev/learn/react-compiler/installation).

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript with type-aware lint rules enabled. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) for information on how to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.

---

## Project Structure

이 프로젝트의 상세한 구조는 [`PROJECT_STRUCTURE.md`](./PROJECT_STRUCTURE.md) 파일을 참조하십시오. 여기서는 애플리케이션의 주요 구성 요소와 그 역할에 대한 개요를 제공합니다.

*   **`src/`**: 모든 소스 코드가 위치합니다.
    *   **`api/`**: API 통신 관련 로직을 담당합니다.
    *   **`assets/`**: 이미지, 아이콘 등의 정적 파일을 포함합니다.
    *   **`components/`**: 재사용 가능한 UI 컴포넌트들을 모아 둡니다.
    *   **`hooks/`**: 커스텀 React 훅을 정의하여 로직 재사용성을 높입니다.
    *   **`pages/`**: 각 라우트에 매핑되는 최상위 페이지 컴포넌트들을 포함합니다.
    *   **`store/`**: 전역 상태 관리를 위한 파일들이 위치합니다 (Zustand).
    *   **`styles/`**: 전역 스타일, 테마 설정, 믹스인 등을 정의합니다.
    *   **`utils/`**: 비즈니스 로직과 무관한 유틸리티 함수들을 모아 둡니다.
*   **`public/`**: 웹 서버에서 직접 서빙되는 정적 자원들을 포함합니다.
*   **`PROJECT_STRUCTURE.md`**: 프로젝트의 상세한 폴더 구조, 기술 스택, 코드 컨벤션 등에 대한 문서입니다.
*   **`API_DOCUMENTATION.md`**: API 엔드포인트 및 사용법에 대한 문서입니다.
*   **`package.json`**, **`vite.config.js`** 등: 프로젝트 설정 및 빌드 관련 파일들입니다.

자세한 내용은 [`PROJECT_STRUCTURE.md`](./PROJECT_STRUCTURE.md)를 확인해주세요.
```
