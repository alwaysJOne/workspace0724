// API 기본 설정
export const API_BASE_URL = 'http://localhost:8888';
export const API_VERSION = 'v1';
export const API_TIMEOUT = 10000;

// API 엔드포인트
export const API_ENDPOINTS = {
  // 인증 API
  AUTH: {
    LOGIN: `/api/${API_VERSION}/auth/login`,
  },
  
  // 회원 API
  MEMBERS: {
    BASE: `/api/${API_VERSION}/members`,
    DETAIL: (userId) => `/api/${API_VERSION}/members/${userId}`,
    SEARCH: (keyword) => `/api/${API_VERSION}/members?keyword=${keyword}`,
  },
  
  // 게시판 API
  BOARD: {
    BASE: '/api/board',
    DETAIL: (boardId) => `/api/board/${boardId}`,
    LIST: (page = 0, size = 10, sort = 'createDate,desc') => 
      `/api/board?page=${page}&size=${size}&sort=${sort}`,
  },
};

export default {
  API_BASE_URL,
  API_VERSION,
  API_TIMEOUT,
  API_ENDPOINTS,
};

