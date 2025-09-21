-- ComCodeM 테스트 데이터 생성
INSERT INTO TB_COM_CODE_M (GRP_CD, GRP_CD_TYPE, GRP_NM, STS, CREATE_DATE, TIMESTAMP, CREATE_USER, UPDATE_USER) VALUES
('[JUnit]APPROVAL_STATUS', 'WORKFLOW', '승인 상태', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', 'COMMON', '자산 상태', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', 'COMMON', '고객 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', 'BUSINESS', '부서 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DOCUMENT_TYPE', 'DOCUMENT', '문서 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]EMPLOYEE_TYPE', 'COMMON', '사원 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', 'SYSTEM', '오류 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]INVENTORY_STATUS', 'COMMON', '재고 상태', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LEAVE_TYPE', 'COMMON', '휴가 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOCATION_TYPE', 'COMMON', '위치 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', 'SYSTEM', '로그 레벨', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', 'SYSTEM', '메뉴 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]NOTIFICATION_TYPE', 'COMMON', '알림 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', 'COMMON', '주문 상태', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', 'FINANCE', '결제 방법', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_TYPE', 'COMMON', '결제 유형', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRIORITY_LEVEL', 'COMMON', '우선순위 레벨', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRODUCT_CATEGORY', 'COMMON', '상품 카테고리', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PROJECT_STATUS', 'COMMON', '프로젝트 상태', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]RISK_LEVEL', 'COMMON', '위험 수준', 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'), -- D
('[JUnit]SCHEDULER_GROUP', 'SYSTEM', '스케줄러 그룹', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SERVICE_LEVEL', 'COMMON', '서비스 레벨', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SHIPMENT_METHOD', 'COMMON', '배송 방법', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]TASK_PRIORITY', 'COMMON', '업무 우선순위', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]TEST_COM_CODE', 'Common', '테스트 공통코드', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]TEST_COM_CODE22', 'Common', '테스트 공통코드', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', 'SYSTEM', '사용자 상태', 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM');

-- 데이터 생성 확인
-- SELECT COUNT(*) FROM TB_COM_CODE_M WHERE GRP_CD LIKE '[JUnit]%';