-- =====================================================
-- Member 테이블에 role 컬럼 추가
-- =====================================================

-- 1. role 컬럼 추가 (기본값: USER)
ALTER TABLE member 
ADD COLUMN role VARCHAR(10) NOT NULL DEFAULT 'USER'
AFTER status;

-- 2. 기존 회원들에게 USER role 부여 (혹시 NULL인 경우 대비)
UPDATE member 
SET role = 'USER' 
WHERE role IS NULL OR role = '';

-- 3. 인덱스 추가 (role로 검색할 경우 성능 향상)
CREATE INDEX idx_member_role ON member(role);

-- 4. 확인 쿼리
SELECT user_id, user_name, role, status 
FROM member 
LIMIT 10;

-- =====================================================
-- 관리자 계정 생성 (선택사항)
-- =====================================================

-- 관리자 계정 생성 (비밀번호: admin1234)
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
    '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG',
    '관리자',
    'admin@commu.com',
    'Y',
    'ADMIN',
    NOW(),
    NOW()
);

-- =====================================================
-- 검증 쿼리
-- =====================================================

-- 전체 회원 role 분포 확인
SELECT role, COUNT(*) as count 
FROM member 
WHERE status = 'Y'
GROUP BY role;

-- 관리자 계정 확인
SELECT user_id, user_name, email, role, status 
FROM member 
WHERE role = 'ADMIN' AND status = 'Y';

