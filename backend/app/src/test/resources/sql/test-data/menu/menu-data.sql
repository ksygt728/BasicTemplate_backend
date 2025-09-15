-- Menu 테스트 데이터
-- 계층형 메뉴 구조로 10개 메뉴 생성 (상위메뉴 2개, 하위메뉴 8개)

-- 상위 메뉴 1: 시스템 관리
INSERT INTO TB_MENU (
    MENU_CD, MENU_NM, UPPER_MENU_CD, MENU_LV, USE_YN, MENU_URL, ORDER_NUM,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
('[JUnit]SYS001', '[JUnit]시스템관리', null, 1, 'Y', null, 1, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 상위 메뉴 2: 사용자 관리
('[JUnit]USR001', '[JUnit]사용자관리', null, 1, 'Y', null, 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 시스템 관리 하위 메뉴
('[JUnit]SYS001_001', '[JUnit]메뉴관리', '[JUnit]SYS001', 2, 'Y', '/admin/menu', 1, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

('[JUnit]SYS001_002', '[JUnit]권한관리', '[JUnit]SYS001', 2, 'Y', '/admin/role', 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

('[JUnit]SYS001_003', '[JUnit]코드관리', '[JUnit]SYS001', 2, 'Y', '/admin/code', 3, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

('[JUnit]SYS001_004', '[JUnit]로그관리', '[JUnit]SYS001', 2, 'N', '/admin/log', 4, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 사용자 관리 하위 메뉴
('[JUnit]USR001_001', '[JUnit]사용자조회', '[JUnit]USR001', 2, 'Y', '/user/search', 1, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

('[JUnit]USR001_002', '[JUnit]부서관리', '[JUnit]USR001', 2, 'Y', '/dept/manage', 2, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

('[JUnit]USR001_003', '[JUnit]회사관리', '[JUnit]USR001', 2, 'Y', '/company/manage', 3, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 비활성 메뉴 (삭제 상태)
('[JUnit]DEL001', '[JUnit]삭제된메뉴', null, 1, 'N', null, 99, NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D')
;
