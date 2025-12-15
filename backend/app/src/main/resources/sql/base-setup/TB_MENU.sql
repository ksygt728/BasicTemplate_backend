============================================================
-- 1. 최상위 메뉴 (1레벨)
============================================================
INSERT INTO TB_MENU (
    MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER,
    MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD
) VALUES (
    'ADM10000', NOW(3), 'SYSTEM', 'C', NOW(3), 'SYSTEM',
    1, '관리자', '/main/admin', 11, 'Y', 'MENU00000'
)
;

============================================================
-- 2. 2레벨 메뉴 (관리자 하위)
============================================================
INSERT INTO TB_MENU VALUES
('ADM20001', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',2,'조직 관리','/main/admin/org',1,'Y','ADM10000'),
('ADM20002', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',2,'기준 정보','/main/admin/base',2,'Y','ADM10000'),
('ADM20003', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',2,'권한 관리','/main/admin/auth',3,'Y','ADM10000'),
('ADM20004', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',2,'시스템 관리','/main/admin/system',4,'Y','ADM10000');

============================================================
-- 3레벨 : 조직 관리 (ADM20001)
============================================================
INSERT INTO TB_MENU VALUES
('ADM30101', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'사용자 관리','/main/admin/org/user',1,'Y','ADM20001'),
('ADM30102', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'부서 관리','/main/admin/org/dept',2,'Y','ADM20001');

============================================================
-- 3레벨 : 기준 정보 (ADM20002)
============================================================
INSERT INTO TB_MENU VALUES
('ADM30201', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'코드 관리','/main/admin/base/code',1,'Y','ADM20002'),
('ADM30202', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'인터페이스 관리','/main/admin/base/interface',2,'Y','ADM20002'),
('ADM30203', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'웹서비스 관리','/main/admin/base/webservice',3,'Y','ADM20002'),
('ADM30204', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'다국어 관리','/main/admin/base/lang',4,'Y','ADM20002');

============================================================
-- 3레벨 : 권한 관리 (ADM20003)
============================================================
INSERT INTO TB_MENU VALUES
('ADM30301', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'메뉴 관리','/main/admin/auth/menu',1,'Y','ADM20003'),
('ADM30302', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'권한 관리(Role)','/main/admin/auth/role',2,'Y','ADM20003'),
('ADM30303', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'사용자별 권한','/main/admin/auth/user-role',3,'Y','ADM20003');

============================================================
-- 3레벨 : 시스템 관리 (ADM20004)
============================================================
INSERT INTO TB_MENU VALUES
('ADM30401', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'채번관리','/main/admin/system/seq',1,'Y','ADM20004'),
('ADM30402', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'메일 관리','/main/admin/system/mail',2,'Y','ADM20004'),
('ADM30403', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'사용자 접속 로그','/main/admin/system/login-log',3,'Y','ADM20004'),
('ADM30404', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'Error 관리','/main/admin/system/error',4,'Y','ADM20004'),
('ADM30405', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'SMS 발송 로그','/main/admin/system/sms-log',5,'Y','ADM20004'),
('ADM30406', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'스케쥴러 관리','/main/admin/system/scheduler',6,'Y','ADM20004'),
('ADM30407', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'메뉴얼 관리','/main/admin/system/manual',7,'Y','ADM20004'),
('ADM30408', NOW(3),'SYSTEM','C',NOW(3),'SYSTEM',3,'결재 관리','/main/admin/system/approval',8,'Y','ADM20004');

============================================================
-- 가이드 (GUD10000)
============================================================
-- lv1
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD10000', '2025-11-20 14:24:29.790', 'SYSTEM', 'C', '2025-11-20 14:24:29.790', 'SYSTEM', 12, '가이드', '/main/guide', 1, 'Y', 'MENU00000');
-- lv 2
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD20000', '2025-11-20 14:24:29.808', 'SYSTEM', 'C', '2025-11-20 14:24:29.808', 'SYSTEM', 2, '개발가이드', '/main/guide/develop', 1, 'Y', 'GUD10000');
-- lv 3
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD30000', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 3, 'ALERT demo ver', '/main/guide/develop/alert-demo', 1, 'Y', 'GUD20000');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD30001', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 3, '기본 컴포넌트', '/main/guide/develop/demo-component', 2, 'Y', 'GUD20000');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD30002', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 3, '고급테이블', '/main/guide/develop/advanced-demo', 3, 'Y', 'GUD20000');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD30003', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 3, '페이지 렌더링 가이드', '/main/guide/develop/page-rendering-guide', 4, 'Y', 'GUD20000');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD30004', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 3, '공통코드 사용방법', '/main/guide/develop/test-common-code', 5, 'Y', 'GUD20000');
-- lv4
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD40000', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 4, 'CSR', '/main/guide/develop/page-rendering-guide/CSR', 1, 'Y', 'GUD30003');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD40001', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 4, 'SSR', '/main/guide/develop/page-rendering-guide/SSR', 2, 'Y', 'GUD30003');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD40002', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 4, 'SSG', '/main/guide/develop/page-rendering-guide/SSG', 3, 'Y', 'GUD30003');
INSERT INTO TB_MENU (MENU_CD, CREATE_DATE, CREATE_USER, STS, TIMESTAMP, UPDATE_USER, MENU_LV, MENU_NM, MENU_URL, ORDER_NUM, USE_YN, UPPER_MENU_CD) VALUES
('GUD40003', '2025-11-20 14:24:29.829', 'SYSTEM', 'C', '2025-11-20 14:24:29.829', 'SYSTEM', 4, 'ISR', '/main/guide/develop/page-rendering-guide/ISR', 4, 'Y', 'GUD30003');
