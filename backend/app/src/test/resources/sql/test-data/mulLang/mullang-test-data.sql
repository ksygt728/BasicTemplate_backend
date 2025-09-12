-- MulLang 테스트 데이터
INSERT INTO TB_MUL_LANG (
    LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 1. 한국어 테스트 메시지
('[JUnit]TEST001', 'ko', 'msg', '[JUnit]테스트 메시지 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 2. 영어 테스트 메시지
('[JUnit]TEST001', 'en', 'msg', '[JUnit]Test Message 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 3. 한국어 두번째 메시지
('[JUnit]TEST002', 'ko', 'msg', '[JUnit]테스트 메시지 2', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 4. 영어 두번째 메시지
('[JUnit]TEST002', 'en', 'msg', '[JUnit]Test Message 2', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 5. 한국어 오류 메시지
('[JUnit]ERR001', 'ko', 'err', '[JUnit]오류 메시지 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 6. 영어 오류 메시지
('[JUnit]ERR001', 'en', 'err', '[JUnit]Error Message 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 7. 한국어 라벨
('[JUnit]LABEL001', 'ko', 'label', '[JUnit]라벨 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 8. 영어 라벨
('[JUnit]LABEL001', 'en', 'label', '[JUnit]Label 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'C'),

-- 9. 한국어 코드 (사용안함)
('[JUnit]CODE001', 'ko', 'code', '[JUnit]코드 1', 'N', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'D'),

-- 10. 영어 코드 (사용안함)
('[JUnit]CODE001', 'en', 'code', '[JUnit]Code 1', 'N', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser', 'D');
