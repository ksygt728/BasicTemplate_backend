-- ComCodeD 테스트 데이터 생성 (각 ComCodeT당 3-5개씩, 총 약 400개)
INSERT INTO TB_COM_CODE_D (GRP_CD, ATTR_CD, DTL_CD, DTL_NM, USE_YN, ORDER_NUM, STS, CREATE_DATE, TIMESTAMP, CREATE_USER, UPDATE_USER) VALUES

-- APPROVAL_STATUS > STATUS 상세코드들
('[JUnit]APPROVAL_STATUS', '[JUnit]STATUS', '[JUnit]PENDING', '대기중', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]STATUS', '[JUnit]APPROVED', '승인완료', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]STATUS', '[JUnit]REJECTED', '승인거부', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]STATUS', '[JUnit]CANCELLED', '승인취소', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- APPROVAL_STATUS > PRIORITY 상세코드들
('[JUnit]APPROVAL_STATUS', '[JUnit]PRIORITY', '[JUnit]HIGH', '높음', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]PRIORITY', '[JUnit]MEDIUM', '보통', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]PRIORITY', '[JUnit]LOW', '낮음', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- APPROVAL_STATUS > LEVEL 상세코드들
('[JUnit]APPROVAL_STATUS', '[JUnit]LEVEL', '[JUnit]LEVEL1', '1차승인', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]LEVEL', '[JUnit]LEVEL2', '2차승인', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]LEVEL', '[JUnit]LEVEL3', '3차승인', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]LEVEL', '[JUnit]FINAL', '최종승인', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- APPROVAL_STATUS > TYPE 상세코드들
('[JUnit]APPROVAL_STATUS', '[JUnit]TYPE', '[JUnit]AUTOMATIC', '자동승인', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]TYPE', '[JUnit]MANUAL', '수동승인', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]TYPE', '[JUnit]CONDITIONAL', '조건승인', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ASSET_STATUS > CONDITION 상세코드들
('[JUnit]ASSET_STATUS', '[JUnit]CONDITION', '[JUnit]NEW', '신품', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CONDITION', '[JUnit]GOOD', '양호', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CONDITION', '[JUnit]FAIR', '보통', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CONDITION', '[JUnit]POOR', '불량', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CONDITION', '[JUnit]DAMAGED', '손상', 'Y', 5, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ASSET_STATUS > LOCATION 상세코드들
('[JUnit]ASSET_STATUS', '[JUnit]LOCATION', '[JUnit]WAREHOUSE', '창고', 'Y', 1, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]LOCATION', '[JUnit]OFFICE', '사무실', 'Y', 2, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]LOCATION', '[JUnit]FACTORY', '공장', 'Y', 3, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]LOCATION', '[JUnit]EXTERNAL', '외부', 'Y', 4, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ASSET_STATUS > CATEGORY 상세코드들
('[JUnit]ASSET_STATUS', '[JUnit]CATEGORY', '[JUnit]EQUIPMENT', '장비', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CATEGORY', '[JUnit]FURNITURE', '가구', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CATEGORY', '[JUnit]VEHICLE', '차량', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CATEGORY', '[JUnit]IT', 'IT자산', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ASSET_STATUS > VALUE 상세코드들
('[JUnit]ASSET_STATUS', '[JUnit]VALUE', '[JUnit]HIGH_VALUE', '고가', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]VALUE', '[JUnit]MEDIUM_VALUE', '중가', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]VALUE', '[JUnit]LOW_VALUE', '저가', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- CUSTOMER_TYPE > GRADE 상세코드들
('[JUnit]CUSTOMER_TYPE', '[JUnit]GRADE', '[JUnit]VIP', 'VIP고객', 'Y', 1, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]GRADE', '[JUnit]GOLD', '골드고객', 'Y', 2, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]GRADE', '[JUnit]SILVER', '실버고객', 'Y', 3, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]GRADE', '[JUnit]BRONZE', '브론즈고객', 'Y', 4, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]GRADE', '[JUnit]GENERAL', '일반고객', 'Y', 5, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- CUSTOMER_TYPE > REGION 상세코드들
('[JUnit]CUSTOMER_TYPE', '[JUnit]REGION', '[JUnit]SEOUL', '서울', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]REGION', '[JUnit]BUSAN', '부산', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]REGION', '[JUnit]INCHEON', '인천', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]REGION', '[JUnit]OVERSEAS', '해외', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- CUSTOMER_TYPE > BUSINESS 상세코드들
('[JUnit]CUSTOMER_TYPE', '[JUnit]BUSINESS', '[JUnit]MANUFACTURING', '제조업', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]BUSINESS', '[JUnit]SERVICE', '서비스업', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]BUSINESS', '[JUnit]RETAIL', '소매업', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]BUSINESS', '[JUnit]IT', 'IT업', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- CUSTOMER_TYPE > SIZE 상세코드들
('[JUnit]CUSTOMER_TYPE', '[JUnit]SIZE', '[JUnit]LARGE', '대기업', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]SIZE', '[JUnit]MEDIUM', '중견기업', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]SIZE', '[JUnit]SMALL', '중소기업', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]SIZE', '[JUnit]STARTUP', '스타트업', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- DEPT_TYPE > LEVEL 상세코드들
('[JUnit]DEPT_TYPE', '[JUnit]LEVEL', '[JUnit]HEADQUARTERS', '본사', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]LEVEL', '[JUnit]DIVISION', '사업부', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]LEVEL', '[JUnit]DEPARTMENT', '부서', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]LEVEL', '[JUnit]TEAM', '팀', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- DEPT_TYPE > FUNCTION 상세코드들
('[JUnit]DEPT_TYPE', '[JUnit]FUNCTION', '[JUnit]SALES', '영업', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]FUNCTION', '[JUnit]MARKETING', '마케팅', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]FUNCTION', '[JUnit]DEVELOPMENT', '개발', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]FUNCTION', '[JUnit]SUPPORT', '지원', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ERROR_TYPE > SEVERITY 상세코드들
('[JUnit]ERROR_TYPE', '[JUnit]SEVERITY', '[JUnit]CRITICAL', '치명적', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]SEVERITY', '[JUnit]HIGH', '높음', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]SEVERITY', '[JUnit]MEDIUM', '보통', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]SEVERITY', '[JUnit]LOW', '낮음', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ERROR_TYPE > CATEGORY 상세코드들
('[JUnit]ERROR_TYPE', '[JUnit]CATEGORY', '[JUnit]SYSTEM', '시스템오류', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]CATEGORY', '[JUnit]NETWORK', '네트워크오류', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]CATEGORY', '[JUnit]DATABASE', '데이터베이스오류', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]CATEGORY', '[JUnit]APPLICATION', '애플리케이션오류', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PAYMENT_METHOD > TYPE 상세코드들
('[JUnit]PAYMENT_METHOD', '[JUnit]TYPE', '[JUnit]CREDIT_CARD', '신용카드', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]TYPE', '[JUnit]DEBIT_CARD', '체크카드', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]TYPE', '[JUnit]BANK_TRANSFER', '계좌이체', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]TYPE', '[JUnit]MOBILE_PAY', '모바일결제', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]TYPE', '[JUnit]CASH', '현금', 'Y', 5, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PAYMENT_METHOD > PROVIDER 상세코드들
('[JUnit]PAYMENT_METHOD', '[JUnit]PROVIDER', '[JUnit]KAKAO_PAY', '카카오페이', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]PROVIDER', '[JUnit]NAVER_PAY', '네이버페이', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]PROVIDER', '[JUnit]TOSS', '토스', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]PROVIDER', '[JUnit]PAYPAL', '페이팔', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- USER_STATUS > ACTIVE 상세코드들
('[JUnit]USER_STATUS', '[JUnit]ACTIVE', '[JUnit]ACTIVE', '활성', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ACTIVE', '[JUnit]INACTIVE', '비활성', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ACTIVE', '[JUnit]SUSPENDED', '정지', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ACTIVE', '[JUnit]LOCKED', '잠금', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- USER_STATUS > ROLE 상세코드들
('[JUnit]USER_STATUS', '[JUnit]ROLE', '[JUnit]ADMIN', '관리자', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ROLE', '[JUnit]MANAGER', '매니저', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ROLE', '[JUnit]USER', '사용자', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ROLE', '[JUnit]GUEST', '게스트', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- USER_STATUS > PERMISSION 상세코드들
('[JUnit]USER_STATUS', '[JUnit]PERMISSION', '[JUnit]FULL', '전체권한', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]PERMISSION', '[JUnit]READ_WRITE', '읽기쓰기', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]PERMISSION', '[JUnit]READ_ONLY', '읽기전용', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]PERMISSION', '[JUnit]NO_ACCESS', '접근불가', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- USER_STATUS > SECURITY 상세코드들
('[JUnit]USER_STATUS', '[JUnit]SECURITY', '[JUnit]HIGH', '높음', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]SECURITY', '[JUnit]MEDIUM', '보통', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]SECURITY', '[JUnit]LOW', '낮음', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- LOG_LEVEL > SEVERITY 상세코드들
('[JUnit]LOG_LEVEL', '[JUnit]SEVERITY', '[JUnit]ERROR', '에러', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', '[JUnit]SEVERITY', '[JUnit]WARN', '경고', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', '[JUnit]SEVERITY', '[JUnit]INFO', '정보', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', '[JUnit]SEVERITY', '[JUnit]DEBUG', '디버그', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- MENU_TYPE > CATEGORY 상세코드들
('[JUnit]MENU_TYPE', '[JUnit]CATEGORY', '[JUnit]SYSTEM', '시스템관리', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', '[JUnit]CATEGORY', '[JUnit]BUSINESS', '업무관리', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', '[JUnit]CATEGORY', '[JUnit]REPORT', '리포트', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', '[JUnit]CATEGORY', '[JUnit]SETTING', '설정', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ORDER_STATUS > PROCESS 상세코드들
('[JUnit]ORDER_STATUS', '[JUnit]PROCESS', '[JUnit]PENDING', '대기중', 'Y', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', '[JUnit]PROCESS', '[JUnit]PROCESSING', '처리중', 'Y', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', '[JUnit]PROCESS', '[JUnit]COMPLETED', '완료', 'Y', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', '[JUnit]PROCESS', '[JUnit]CANCELLED', '취소', 'Y', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM');

-- 데이터 생성 확인
-- SELECT COUNT(*) as '생성된 ComCodeD 수' FROM TB_COM_CODE_D WHERE GRP_CD LIKE '[JUnit]%';