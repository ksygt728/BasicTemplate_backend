-- ComCodeT 테스트 데이터 생성 (총 108개)
INSERT INTO TB_COM_CODE_T (GRP_CD, ATTR_CD, ATTR_NM, ORDER_NUM, STS, CREATE_DATE, TIMESTAMP, CREATE_USER, UPDATE_USER) VALUES
-- APPROVAL_STATUS 관련 속성들
('[JUnit]APPROVAL_STATUS', '[JUnit]STATUS', '승인상태', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]PRIORITY', '우선순위', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]LEVEL', '승인레벨', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]APPROVAL_STATUS', '[JUnit]TYPE', '승인유형', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ASSET_STATUS 관련 속성들
('[JUnit]ASSET_STATUS', '[JUnit]CONDITION', '자산상태', 1, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]LOCATION', '보관위치', 2, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]CATEGORY', '자산분류', 3, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ASSET_STATUS', '[JUnit]VALUE', '자산가치', 4, 'D', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- CUSTOMER_TYPE 관련 속성들
('[JUnit]CUSTOMER_TYPE', '[JUnit]GRADE', '고객등급', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]REGION', '지역구분', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]BUSINESS', '업종구분', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]CUSTOMER_TYPE', '[JUnit]SIZE', '규모구분', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- DEPT_TYPE 관련 속성들
('[JUnit]DEPT_TYPE', '[JUnit]LEVEL', '부서레벨', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]FUNCTION', '부서기능', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]REGION', '지역구분', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DEPT_TYPE', '[JUnit]SIZE', '부서규모', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- DOCUMENT_TYPE 관련 속성들
('[JUnit]DOCUMENT_TYPE', '[JUnit]FORMAT', '문서형식', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DOCUMENT_TYPE', '[JUnit]SECURITY', '보안등급', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DOCUMENT_TYPE', '[JUnit]RETENTION', '보존기간', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]DOCUMENT_TYPE', '[JUnit]ACCESS', '접근권한', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- EMPLOYEE_TYPE 관련 속성들
('[JUnit]EMPLOYEE_TYPE', '[JUnit]POSITION', '직급구분', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]EMPLOYEE_TYPE', '[JUnit]EMPLOYMENT', '고용형태', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]EMPLOYEE_TYPE', '[JUnit]WORK_TYPE', '근무형태', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]EMPLOYEE_TYPE', '[JUnit]SKILL', '스킬레벨', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ERROR_TYPE 관련 속성들
('[JUnit]ERROR_TYPE', '[JUnit]SEVERITY', '심각도', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]CATEGORY', '오류분류', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]SOURCE', '발생원인', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ERROR_TYPE', '[JUnit]RESOLUTION', '해결방법', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- INVENTORY_STATUS 관련 속성들
('[JUnit]INVENTORY_STATUS', '[JUnit]QUANTITY', '재고수량', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]INVENTORY_STATUS', '[JUnit]LOCATION', '보관장소', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]INVENTORY_STATUS', '[JUnit]CONDITION', '상품상태', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]INVENTORY_STATUS', '[JUnit]EXPIRY', '유효기간', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- LEAVE_TYPE 관련 속성들
('[JUnit]LEAVE_TYPE', '[JUnit]DURATION', '휴가기간', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LEAVE_TYPE', '[JUnit]REASON', '휴가사유', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LEAVE_TYPE', '[JUnit]PAYMENT', '급여지급', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LEAVE_TYPE', '[JUnit]APPROVAL', '승인절차', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- LOCATION_TYPE 관련 속성들
('[JUnit]LOCATION_TYPE', '[JUnit]BUILDING', '건물구분', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOCATION_TYPE', '[JUnit]FLOOR', '층구분', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOCATION_TYPE', '[JUnit]AREA', '구역구분', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOCATION_TYPE', '[JUnit]ACCESS', '접근권한', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- LOG_LEVEL 관련 속성들
('[JUnit]LOG_LEVEL', '[JUnit]SEVERITY', '심각도', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', '[JUnit]MODULE', '모듈구분', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', '[JUnit]RETENTION', '보관기간', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]LOG_LEVEL', '[JUnit]ALERT', '알림설정', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- MENU_TYPE 관련 속성들
('[JUnit]MENU_TYPE', '[JUnit]CATEGORY', '메뉴분류', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', '[JUnit]LEVEL', '메뉴레벨', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', '[JUnit]PERMISSION', '권한등급', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]MENU_TYPE', '[JUnit]DISPLAY', '표시방식', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- NOTIFICATION_TYPE 관련 속성들
('[JUnit]NOTIFICATION_TYPE', '[JUnit]PRIORITY', '우선순위', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]NOTIFICATION_TYPE', '[JUnit]CHANNEL', '발송채널', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]NOTIFICATION_TYPE', '[JUnit]TIMING', '발송시점', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]NOTIFICATION_TYPE', '[JUnit]TEMPLATE', '템플릿', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- ORDER_STATUS 관련 속성들
('[JUnit]ORDER_STATUS', '[JUnit]PROCESS', '처리단계', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', '[JUnit]PAYMENT', '결제상태', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', '[JUnit]DELIVERY', '배송상태', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]ORDER_STATUS', '[JUnit]RETURN', '반품상태', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PAYMENT_METHOD 관련 속성들
('[JUnit]PAYMENT_METHOD', '[JUnit]TYPE', '결제유형', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]PROVIDER', '결제사업자', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]SECURITY', '보안등급', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_METHOD', '[JUnit]FEE', '수수료', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PAYMENT_TYPE 관련 속성들
('[JUnit]PAYMENT_TYPE', '[JUnit]SCHEDULE', '결제일정', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_TYPE', '[JUnit]CURRENCY', '통화구분', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_TYPE', '[JUnit]LIMIT', '한도설정', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PAYMENT_TYPE', '[JUnit]VALIDATION', '검증방식', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PRIORITY_LEVEL 관련 속성들
('[JUnit]PRIORITY_LEVEL', '[JUnit]URGENCY', '긴급도', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRIORITY_LEVEL', '[JUnit]IMPORTANCE', '중요도', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRIORITY_LEVEL', '[JUnit]RESPONSE_TIME', '응답시간', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRIORITY_LEVEL', '[JUnit]ESCALATION', '에스컬레이션', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PRODUCT_CATEGORY 관련 속성들
('[JUnit]PRODUCT_CATEGORY', '[JUnit]BRAND', '브랜드', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRODUCT_CATEGORY', '[JUnit]PRICE_RANGE', '가격대', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRODUCT_CATEGORY', '[JUnit]TARGET', '타겟고객', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PRODUCT_CATEGORY', '[JUnit]SEASON', '시즌구분', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- PROJECT_STATUS 관련 속성들
('[JUnit]PROJECT_STATUS', '[JUnit]PHASE', '프로젝트단계', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PROJECT_STATUS', '[JUnit]PROGRESS', '진행률', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PROJECT_STATUS', '[JUnit]RISK', '위험도', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]PROJECT_STATUS', '[JUnit]BUDGET', '예산상태', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- RISK_LEVEL 관련 속성들
('[JUnit]RISK_LEVEL', '[JUnit]PROBABILITY', '발생가능성', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]RISK_LEVEL', '[JUnit]IMPACT', '영향도', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]RISK_LEVEL', '[JUnit]MITIGATION', '완화방안', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]RISK_LEVEL', '[JUnit]MONITORING', '모니터링', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- SCHEDULER_GROUP 관련 속성들
('[JUnit]SCHEDULER_GROUP', '[JUnit]FREQUENCY', '실행빈도', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SCHEDULER_GROUP', '[JUnit]PRIORITY', '우선순위', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SCHEDULER_GROUP', '[JUnit]RESOURCE', '리소스사용량', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SCHEDULER_GROUP', '[JUnit]TIMEOUT', '타임아웃', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- SERVICE_LEVEL 관련 속성들
('[JUnit]SERVICE_LEVEL', '[JUnit]RESPONSE_TIME', '응답시간', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SERVICE_LEVEL', '[JUnit]AVAILABILITY', '가용성', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SERVICE_LEVEL', '[JUnit]THROUGHPUT', '처리량', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SERVICE_LEVEL', '[JUnit]QUALITY', '서비스품질', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- SHIPMENT_METHOD 관련 속성들
('[JUnit]SHIPMENT_METHOD', '[JUnit]SPEED', '배송속도', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SHIPMENT_METHOD', '[JUnit]COST', '배송비용', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SHIPMENT_METHOD', '[JUnit]TRACKING', '추적가능여부', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]SHIPMENT_METHOD', '[JUnit]INSURANCE', '보험적용', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- TASK_PRIORITY 관련 속성들
('[JUnit]TASK_PRIORITY', '[JUnit]DEADLINE', '마감일', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]TASK_PRIORITY', '[JUnit]COMPLEXITY', '복잡도', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]TASK_PRIORITY', '[JUnit]DEPENDENCY', '의존성', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]TASK_PRIORITY', '[JUnit]RESOURCE', '필요리소스', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),

-- USER_STATUS 관련 속성들
('[JUnit]USER_STATUS', '[JUnit]ACTIVE', '활성상태', 1, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]ROLE', '사용자역할', 2, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]PERMISSION', '권한등급', 3, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM'),
('[JUnit]USER_STATUS', '[JUnit]SECURITY', '보안등급', 4, 'C', NOW(), NOW(), 'SYSTEM', 'SYSTEM');

-- 데이터 생성 확인
-- SELECT COUNT(*) as '생성된 ComCodeT 수' FROM TB_COM_CODE_T WHERE GRP_CD LIKE '[JUnit]%';