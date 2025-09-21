-- Chaebun 테스트 데이터
INSERT INTO TB_CHAEBUN (
    SEQ_ID, SEQ_NAME, PATTERN, PREFIX, CURRENT_VALUE, STEP, LENGTH, DATEFORMAT,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 1. 정상 테스트용 채번
('[JUnit]SEQ001', '[JUnit]회원번호채번', '{PREFIX}_{DATEFORMAT}_{VALUE}', '[JUnit]MEMBER', 0, 1, 4, 'yyyyMMdd', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 2. 수정 테스트용 채번  
('[JUnit]SEQ002', '[JUnit]주문번호채번', '{PREFIX}_{DATEFORMAT}_{VALUE}', '[JUnit]ORDER', 5, 1, 6, 'yyyyMMdd', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 3. 삭제 대상 채번
('[JUnit]SEQ003', '[JUnit]상품번호채번', '{PREFIX}_{VALUE}', '[JUnit]PRODUCT', 10, 1, 5, '', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 4. 이미 삭제된 채번 (sts = 'D')
('[JUnit]SEQ004', '[JUnit]삭제된채번', '{PREFIX}_{VALUE}', '[JUnit]DELETED', 0, 1, 4, '', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D'),

-- 5. 검색 테스트용 추가 데이터
('[JUnit]SEQ005', '[JUnit]게시판번호채번', '{PREFIX}_{DATEFORMAT}_{VALUE}', '[JUnit]BOARD', 100, 1, 8, 'yyyyMMdd', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]SEQ006', '[JUnit]파일번호채번', '{PREFIX}_{VALUE}', '[JUnit]FILE', 0, 1, 10, '', NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C');