-- Company 테스트 데이터
INSERT INTO TB_COMPANY(COMPANY_CODE, COMPANY_NAME, CREATE_USER, CREATE_DATE, UPDATE_USER, TIMESTAMP, STS) VALUES 
('[JUnit]C100' , 'CBMS회사', 'ADMIN', now() , 'ADMIN' , now(), 'C')
;

INSERT INTO TB_DEPARTMENT(DEPT_CODE, DEPT_NM, COMPANY_CODE, UPPER_DEPT_CODE, DEPT_LV, USE_YN, STS, CREATE_USER, CREATE_DATE, UPDATE_USER, TIMESTAMP ) VALUES
('[JUnit]21323243' , 'TEST회사' , '[JUnit]C100' , '20000000', 1, 'Y', 'C', 'ADMIN' , now(), 'ADMIN' , now())
;

-- User 테스트 데이터 10개
INSERT INTO TB_USER (
    USER_ID, PASSWORD, NAME, PHONE_NUM, EMAIL, ROLE, USER_TYPE, GENDER, DEPT_CODE,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 1. 관리자
('[JUnit]admin001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]김관리', '010-1111-1111', 'admin001@company.com', 'ROLE_GUEST', 'CBMS', 'M', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 2. 일반 사용자
('[JUnit]user001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]이직원', '010-2222-2222', 'user001@company.com', 'ROLE_GUEST', 'CBMS', 'F', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 3. 매니저
('[JUnit]mgr001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]박매니저', '010-3333-3333', 'mgr001@company.com', 'ROLE_GUEST', 'CBMS', 'M', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 4. 개발자
('[JUnit]dev001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]최개발', '010-4444-4444', 'dev001@company.com', 'ROLE_GUEST', 'CBMS', 'M', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 5. 디자이너
('[JUnit]design001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]정디자인', '010-5555-5555', 'design001@company.com', 'ROLE_GUEST', 'CBMS', 'F', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 6. 외부 사용자
('[JUnit]external001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]김외부', '010-6666-6666', 'external001@external.com', 'ROLE_GUEST', 'EXTERNAL', 'M', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 7. 영업 담당자
('[JUnit]sales001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]송영업', '010-7777-7777', 'sales001@company.com', 'ROLE_GUEST', 'ROLE_GUEST', 'F', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D'),

-- 8. 인사 담당자
('[JUnit]hr001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]문인사', '010-8888-8888', 'hr001@company.com', 'ROLE_GUEST', 'CBMS', 'F', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 9. 회계 담당자
('[JUnit]finance001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]안회계', '010-9999-9999', 'finance001@company.com', 'ROLE_GUEST', 'CBMS', 'M', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 10. 비활성 사용자
('[JUnit]inactive001', '$2a$10$ID/OC2DkXIFl4eZFybBSwubZ52NomhzKHbDDHXt0.me8LkHGvmkT2', '[JUnit]유비활성', '010-0000-0000', 'inactive001@company.com', 'ROLE_GUEST', 'CBMS', 'M', '[JUnit]21323243', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'N')
;