-- MulLang 테스트 데이터 INSERT 쿼리
-- [JUnit] 접두사를 사용하여 테스트 데이터와 프로덕션 데이터를 구분

-- 다국어 테스트 데이터 (한국어)
INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]TEST001', 'ko', 'msg', '테스트 메시지 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]TEST001', 'en', 'msg', 'Test Message 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]TEST002', 'ko', 'msg', '테스트 메시지 2', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]TEST002', 'en', 'msg', 'Test Message 2', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]ERR001', 'ko', 'err', '오류 메시지 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]ERR001', 'en', 'err', 'Error Message 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]LABEL001', 'ko', 'label', '라벨 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]LABEL001', 'en', 'label', 'Label 1', 'Y', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]CODE001', 'ko', 'code', '코드 1', 'N', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');

INSERT INTO TB_MUL_LANG (LANG_CD, LANG_TYPE, LANG_GUBUN, LANG_NM, USE_YN, REG_DT, REG_USER_ID, UPD_DT, UPD_USER_ID)
VALUES ('[JUnit]CODE001', 'en', 'code', 'Code 1', 'N', NOW(), '[JUnit]testuser', NOW(), '[JUnit]testuser');
