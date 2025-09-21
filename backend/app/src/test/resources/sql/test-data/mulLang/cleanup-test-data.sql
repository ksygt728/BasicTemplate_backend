-- MulLang 테스트 데이터 정리 쿼리
-- [JUnit] 접두사가 포함된 테스트 데이터를 모두 삭제

DELETE FROM TB_MUL_LANG WHERE LANG_CD LIKE '[JUnit]%';
