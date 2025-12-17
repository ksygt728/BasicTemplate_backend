-- Role 테스트 데이터
INSERT INTO TB_ROLE (
    ROLE_CD, ROLE_NAME, ROLE_DESC,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 1. 시스템 관리자 권한
('[JUnit]ROLE_ADMIN', '[JUnit]시스템관리자', '[JUnit]시스템 전체 관리 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 2. 일반 사용자 권한
('[JUnit]ROLE_USER', '[JUnit]일반사용자', '[JUnit]기본 사용자 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 3. 매니저 권한
('[JUnit]ROLE_MANAGER', '[JUnit]매니저', '[JUnit]부서 관리 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 4. 개발자 권한
('[JUnit]ROLE_DEVELOPER', '[JUnit]개발자', '[JUnit]개발 업무 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 5. 읽기 전용 권한
('[JUnit]ROLE_VIEWER', '[JUnit]조회자', '[JUnit]읽기 전용 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 6. 외부 사용자 권한
('[JUnit]ROLE_EXTERNAL', '[JUnit]외부사용자', '[JUnit]외부 사용자 제한 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 7. 인사 담당자 권한
('[JUnit]ROLE_HR', '[JUnit]인사담당자', '[JUnit]인사 관리 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 8. 영업 담당자 권한
('[JUnit]ROLE_SALES', '[JUnit]영업담당자', '[JUnit]영업 관리 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D'),

-- 9. 회계 담당자 권한
('[JUnit]ROLE_FINANCE', '[JUnit]회계담당자', '[JUnit]회계 관리 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 10. 비활성 권한
('[JUnit]ROLE_INACTIVE', '[JUnit]비활성권한', '[JUnit]테스트용 비활성 권한', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'N')
;

-- Menu 테스트 데이터
INSERT INTO TB_MENU (
    MENU_CD, MENU_NM, UPPER_MENU_CD, MENU_LV, USE_YN, MENU_URL, ORDER_NUM,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 메인 메뉴들
('[JUnit]MENU001', '[JUnit]시스템관리', NULL, 1, 'Y', '/admin', 1, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]MENU002', '[JUnit]사용자관리', '[JUnit]MENU001', 2, 'Y', '/admin/user', 1, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]MENU003', '[JUnit]권한관리', '[JUnit]MENU001', 2, 'Y', '/admin/role', 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]MENU004', '[JUnit]메뉴관리', '[JUnit]MENU001', 2, 'Y', '/admin/menu', 3, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]MENU005', '[JUnit]대시보드', NULL, 1, 'Y', '/dashboard', 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C')
;

-- RoleMenu 테스트 데이터
INSERT INTO TB_ROLE_MENU (
    ROLE_CD, MENU_CD, MENU_RW, USE_YN,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- ADMIN은 모든 메뉴에 쓰기 권한
('[JUnit]ROLE_ADMIN', '[JUnit]MENU001', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_ADMIN', '[JUnit]MENU002', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_ADMIN', '[JUnit]MENU003', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_ADMIN', '[JUnit]MENU004', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_ADMIN', '[JUnit]MENU005', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- USER는 대시보드만 읽기 권한
('[JUnit]ROLE_USER', '[JUnit]MENU005', 'R', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_USER', '[JUnit]MENU004', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_USER', '[JUnit]MENU003', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- MANAGER는 사용자관리와 대시보드에 쓰기 권한
('[JUnit]ROLE_MANAGER', '[JUnit]MENU002', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]ROLE_MANAGER', '[JUnit]MENU005', 'C', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C')
;

-- User 테스트 데이터 (User-Role 관계 테스트용)
INSERT INTO TB_USER (
    USER_ID, PASSWORD, NAME, PHONE_NUM, EMAIL, ROLE, USER_TYPE, GENDER, DEPT_CODE,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
('[JUnit]testuser001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]테스트사용자1', '010-1111-1111', 'testuser001@test.com', 'ROLE_GUEST', 'CBMS', 'M', '20000000', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]testuser002', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]테스트사용자2', '010-2222-2222', 'testuser002@test.com', 'ROLE_GUEST', 'CBMS', 'F', '20000000', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]testuser003', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]테스트사용자3', '010-3333-3333', 'testuser003@test.com', 'ROLE_GUEST', 'CBMS', 'M', '20000000', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C')
;

-- RoleUser 테스트 데이터
INSERT INTO TB_ROLE_USER (
    ROLE_CD, USER_ID, USE_YN,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- testuser001은 ADMIN 권한
('[JUnit]ROLE_ADMIN', '[JUnit]testuser001', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- testuser002는 USER 권한
('[JUnit]ROLE_USER', '[JUnit]testuser002', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D'),

-- testuser003은 MANAGER 권한
('[JUnit]ROLE_MANAGER', '[JUnit]testuser003', 'Y', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C')
;