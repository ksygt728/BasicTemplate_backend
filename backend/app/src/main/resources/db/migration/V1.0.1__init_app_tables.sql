-- =========================================================
-- Flyway Migration Script
-- Version  : V1.0.1
-- Description : 애플리케이션 기본 테이블 초기화
-- Target DB   : CBMS (MySQL 8.x, InnoDB)
-- Created     : 2026-07-01
-- Author      : 김승연
--
-- [참고사항]
--   - 모든 테이블은 BaseEntity 공통 컬럼(STS, CREATE_USER, CREATE_DATE,
--     UPDATE_USER, TIMESTAMP)을 포함합니다.
--   - `TIMESTAMP` 는 MySQL 예약어이므로 백틱(`)으로 이스케이프합니다.
--   - `TEXT` 컬럼에는 DEFAULT 값을 지정할 수 없습니다(MySQL 제약).
--   - FK 참조 순서를 고려하여 의존 테이블이 먼저 생성됩니다.
--   - ddl-auto: validate 옵션으로 엔티티 매핑과 일치 여부를 검증할 수 있습니다.
-- =========================================================


-- =========================================================
-- 1. TB_COMPANY - 회사 테이블 (Company.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_COMPANY
(
    COMPANY_CODE VARCHAR(45)  NOT NULL                COMMENT '회사코드',
    COMPANY_NAME VARCHAR(100) NOT NULL                COMMENT '회사명',
    -- BaseEntity 공통 컬럼
    STS          VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER  VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE  TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER  VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP`  TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (COMPANY_CODE)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '회사 테이블';


-- =========================================================
-- 2. TB_ROLE - 권한 테이블 (Role.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_ROLE
(
    ROLE_CD     VARCHAR(45)   NOT NULL                COMMENT '권한코드',
    ROLE_NAME   VARCHAR(100)  NOT NULL                COMMENT '권한명',
    ROLE_DESC   VARCHAR(1024)                         COMMENT '권한설명',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)    NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)   NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3)  NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)   NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3)  NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (ROLE_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '권한 테이블';


-- =========================================================
-- 3. TB_MENU - 메뉴 테이블 (Menu.java)
--    UPPER_MENU_CD: 자기 참조 FK (nullable)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_MENU
(
    MENU_CD       VARCHAR(45)  NOT NULL                COMMENT '메뉴코드',
    MENU_NM       VARCHAR(100) NOT NULL                COMMENT '메뉴명',
    UPPER_MENU_CD VARCHAR(45)                          COMMENT '상위메뉴코드 (자기참조 FK)',
    MENU_LV       INT          NOT NULL                COMMENT '메뉴레벨',
    USE_YN        VARCHAR(1)   NOT NULL DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    MENU_URL      VARCHAR(200)                         COMMENT '메뉴 URL',
    ORDER_NUM     INT          NOT NULL                COMMENT '정렬순서',
    -- BaseEntity 공통 컬럼
    STS           VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER   VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE   TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER   VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP`   TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (MENU_CD),
    CONSTRAINT FK_MENU_UPPER FOREIGN KEY (UPPER_MENU_CD) REFERENCES TB_MENU (MENU_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '메뉴 테이블';


-- =========================================================
-- 4. TB_COM_CODE_M - 공통코드 마스터 테이블 (ComCodeM.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_COM_CODE_M
(
    GRP_CD      VARCHAR(45)  NOT NULL                COMMENT '그룹코드',
    GRP_CD_TYPE VARCHAR(45)  NOT NULL                COMMENT '그룹코드유형',
    GRP_NM      VARCHAR(100) NOT NULL                COMMENT '그룹코드명',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (GRP_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '공통코드 마스터 테이블';


-- =========================================================
-- 5. TB_COM_CODE_T - 공통코드 속성 테이블 (ComCodeT.java)
--    복합키: (GRP_CD, ATTR_CD)
--    ComCodeTId: grpCd + attrCd
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_COM_CODE_T
(
    GRP_CD      VARCHAR(45)  NOT NULL                COMMENT '그룹코드',
    ATTR_CD     VARCHAR(45)  NOT NULL                COMMENT '속성코드',
    ATTR_NM     VARCHAR(100) NOT NULL                COMMENT '속성명',
    ORDER_NUM   INT          NOT NULL                COMMENT '정렬순서',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (GRP_CD, ATTR_CD),
    CONSTRAINT FK_COM_CODE_T_M FOREIGN KEY (GRP_CD) REFERENCES TB_COM_CODE_M (GRP_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '공통코드 속성 테이블';


-- =========================================================
-- 6. TB_COM_CODE_T_PIVOT - 공통코드 속성 피벗 테이블 (ComCodeTPivot.java)
--    복합키: (GRP_CD, ATTR_CD) - ComCodeTId 재사용
--    TB_COM_CODE_T 와 동일한 키 구조, 외래키 없음(독립 피벗)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_COM_CODE_T_PIVOT
(
    GRP_CD      VARCHAR(45)  NOT NULL                COMMENT '그룹코드',
    ATTR_CD     VARCHAR(45)  NOT NULL                COMMENT '속성코드',
    ATTR_NM     VARCHAR(100) NOT NULL                COMMENT '속성명',
    ORDER_NUM   INT          NOT NULL                COMMENT '정렬순서',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (GRP_CD, ATTR_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '공통코드 속성 피벗 테이블';


-- =========================================================
-- 7. TB_COM_CODE_D - 공통코드 상세 테이블 (ComCodeD.java)
--    복합키: (GRP_CD, ATTR_CD, DTL_CD)
--    ComCodeDId: ComCodeTId(grpCd, attrCd) + dtlCd
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_COM_CODE_D
(
    GRP_CD      VARCHAR(45)  NOT NULL                COMMENT '그룹코드',
    ATTR_CD     VARCHAR(45)  NOT NULL                COMMENT '속성코드',
    DTL_CD      VARCHAR(45)  NOT NULL                COMMENT '상세코드',
    DTL_NM      VARCHAR(100)                         COMMENT '상세코드명',
    USE_YN      VARCHAR(1)            DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    ORDER_NUM   INT          NOT NULL                COMMENT '정렬순서',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (GRP_CD, ATTR_CD, DTL_CD),
    CONSTRAINT FK_COM_CODE_D_T FOREIGN KEY (GRP_CD, ATTR_CD) REFERENCES TB_COM_CODE_T (GRP_CD, ATTR_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '공통코드 상세 테이블';


-- =========================================================
-- 8. TB_COM_CODE_D_PIVOT - 공통코드 상세 피벗 테이블 (ComCodeDPivot.java)
--    복합키: (GRP_CD, DTL_CD)
--    ComCodeDPivotId: grpCd + dtlCd
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_COM_CODE_D_PIVOT
(
    GRP_CD      VARCHAR(45)  NOT NULL                COMMENT '그룹코드',
    DTL_CD      VARCHAR(45)  NOT NULL                COMMENT '상세코드',
    ATTR_01     VARCHAR(200)                         COMMENT '속성값01',
    ATTR_02     VARCHAR(200)                         COMMENT '속성값02',
    ATTR_03     VARCHAR(200)                         COMMENT '속성값03',
    ATTR_04     VARCHAR(200)                         COMMENT '속성값04',
    ATTR_05     VARCHAR(200)                         COMMENT '속성값05',
    ATTR_06     VARCHAR(200)                         COMMENT '속성값06',
    ATTR_07     VARCHAR(200)                         COMMENT '속성값07',
    ATTR_08     VARCHAR(200)                         COMMENT '속성값08',
    ATTR_09     VARCHAR(200)                         COMMENT '속성값09',
    ATTR_10     VARCHAR(200)                         COMMENT '속성값10',
    USE_YN      VARCHAR(1)            DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    ORDER_NUM   INT          NOT NULL                COMMENT '정렬순서',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (GRP_CD, DTL_CD),
    CONSTRAINT FK_COM_CODE_D_PIVOT_M FOREIGN KEY (GRP_CD) REFERENCES TB_COM_CODE_M (GRP_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '공통코드 상세 피벗 테이블';


-- =========================================================
-- 9. TB_DEPARTMENT - 부서 테이블 (Department.java)
--    COMPANY_CODE → TB_COMPANY
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_DEPARTMENT
(
    DEPT_CODE       VARCHAR(45)  NOT NULL                COMMENT '부서코드',
    DEPT_NM         VARCHAR(100) NOT NULL                COMMENT '부서명',
    UPPER_DEPT_CODE VARCHAR(45)  NOT NULL                COMMENT '상위부서코드',
    DEPT_LV         INT          NOT NULL                COMMENT '부서레벨',
    COMPANY_CODE    VARCHAR(45)  NOT NULL                COMMENT '회사코드 (FK → TB_COMPANY)',
    USE_YN          VARCHAR(1)            DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    -- BaseEntity 공통 컬럼
    STS             VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER     VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE     TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER     VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP`     TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (DEPT_CODE),
    CONSTRAINT FK_DEPT_COMPANY FOREIGN KEY (COMPANY_CODE) REFERENCES TB_COMPANY (COMPANY_CODE)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '부서 테이블';


-- =========================================================
-- 10. TB_USER - 사용자 테이블 (User.java)
--    DEPT_CODE → TB_DEPARTMENT (nullable)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_USER
(
    USER_ID   VARCHAR(45)  NOT NULL                COMMENT '사용자아이디',
    PASSWORD  VARCHAR(200) NOT NULL                COMMENT '비밀번호',
    NAME      VARCHAR(100) NOT NULL                COMMENT '이름',
    PHONE_NUM VARCHAR(45)  NOT NULL                COMMENT '전화번호',
    EMAIL     VARCHAR(45)  NOT NULL                COMMENT '이메일',
    ROLE      VARCHAR(45)  NOT NULL                COMMENT '역할',
    USER_TYPE VARCHAR(45)  NOT NULL                COMMENT '사용자타입',
    GENDER    VARCHAR(1)   NOT NULL                COMMENT '성별 (M/F)',
    DEPT_CODE VARCHAR(45)                          COMMENT '부서코드 (FK → TB_DEPARTMENT, nullable)',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (USER_ID),
    CONSTRAINT FK_USER_DEPT FOREIGN KEY (DEPT_CODE) REFERENCES TB_DEPARTMENT (DEPT_CODE)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '사용자 테이블';


-- =========================================================
-- 11. TB_BBS - 게시판 테이블 (Bbs.java)
--    WRITOR → TB_USER (updatable=false)
--    BBS_ID: UUID (@PrePersist 자동 생성)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_BBS
(
    BBS_ID     VARCHAR(36)   NOT NULL                                COMMENT '게시판아이디 (UUID)',
    BBS_TYPE   VARCHAR(45)   NOT NULL                                COMMENT '게시판타입',
    TITLE      VARCHAR(100)  NOT NULL                                COMMENT '제목',
    CONTENT    VARCHAR(2048) NOT NULL                                COMMENT '내용',
    WRITOR     VARCHAR(45)   NOT NULL                                COMMENT '작성자 (FK → TB_USER, updatable=false)',
    WRITE_DATE TIMESTAMP(3)  NOT NULL DEFAULT CURRENT_TIMESTAMP(3)  COMMENT '작성일',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'                   COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                                COMMENT '수정일시',
    PRIMARY KEY (BBS_ID),
    CONSTRAINT FK_BBS_WRITOR FOREIGN KEY (WRITOR) REFERENCES TB_USER (USER_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '게시판 테이블';


-- =========================================================
-- 12. TB_BBS_COMMENT - 게시판 댓글 테이블 (BbsComment.java)
--    BBS_ID → TB_BBS
--    WRITOR → TB_USER (updatable=false)
--    COMMENT_ID: UUID (@PrePersist 자동 생성)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_BBS_COMMENT
(
    COMMENT_ID VARCHAR(36)   NOT NULL  COMMENT '댓글아이디 (UUID)',
    BBS_ID     VARCHAR(36)   NOT NULL  COMMENT '게시판아이디 (FK → TB_BBS)',
    CONTENT    VARCHAR(2048) NOT NULL  COMMENT '내용',
    WRITOR     VARCHAR(45)   NOT NULL  COMMENT '작성자 (FK → TB_USER, updatable=false)',
    WRITE_DATE TIMESTAMP(3)  NOT NULL  COMMENT '작성일',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (COMMENT_ID),
    CONSTRAINT FK_BBS_COMMENT_BBS    FOREIGN KEY (BBS_ID) REFERENCES TB_BBS (BBS_ID),
    CONSTRAINT FK_BBS_COMMENT_WRITOR FOREIGN KEY (WRITOR) REFERENCES TB_USER (USER_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '게시판 댓글 테이블';


-- =========================================================
-- 13. TB_NOTICE - 공지사항 테이블 (Notice.java)
--    WRITOR → TB_USER
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_NOTICE
(
    NOT_ID     VARCHAR(45)   NOT NULL  COMMENT '공지아이디',
    NOT_TYPE   VARCHAR(45)   NOT NULL  COMMENT '공지타입 (공지/매뉴얼)',
    TITLE      VARCHAR(100)  NOT NULL  COMMENT '제목',
    CONTENT    VARCHAR(2048) NOT NULL  COMMENT '내용',
    WRITOR     VARCHAR(45)   NOT NULL  COMMENT '작성자 (FK → TB_USER)',
    WRITE_DATE TIMESTAMP(3)  NOT NULL  COMMENT '작성일',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (NOT_ID),
    CONSTRAINT FK_NOTICE_WRITOR FOREIGN KEY (WRITOR) REFERENCES TB_USER (USER_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '공지사항 테이블';


-- =========================================================
-- 14. TB_ROLE_MENU - 권한-메뉴 매핑 테이블 (RoleMenu.java)
--    복합키: (ROLE_CD, MENU_CD)
--    RoleMenuId: roleCd + menuCd
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_ROLE_MENU
(
    ROLE_CD     VARCHAR(45)  NOT NULL                COMMENT '권한코드 (FK → TB_ROLE)',
    MENU_CD     VARCHAR(45)  NOT NULL                COMMENT '메뉴코드 (FK → TB_MENU)',
    MENU_RW     VARCHAR(45)                          COMMENT '메뉴 접근 수준 (C,R,U,D 조합)',
    USE_YN      VARCHAR(1)   NOT NULL DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (ROLE_CD, MENU_CD),
    CONSTRAINT FK_ROLE_MENU_ROLE FOREIGN KEY (ROLE_CD) REFERENCES TB_ROLE (ROLE_CD),
    CONSTRAINT FK_ROLE_MENU_MENU FOREIGN KEY (MENU_CD) REFERENCES TB_MENU (MENU_CD)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '권한-메뉴 매핑 테이블';


-- =========================================================
-- 15. TB_ROLE_USER - 권한-사용자 매핑 테이블 (RoleUser.java)
--    복합키: (ROLE_CD, USER_ID)
--    RoleUserId: roleCd + userId
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_ROLE_USER
(
    ROLE_CD     VARCHAR(45)  NOT NULL                COMMENT '권한코드 (FK → TB_ROLE)',
    USER_ID     VARCHAR(45)  NOT NULL                COMMENT '사용자아이디 (FK → TB_USER)',
    USE_YN      VARCHAR(1)   NOT NULL DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (ROLE_CD, USER_ID),
    CONSTRAINT FK_ROLE_USER_ROLE FOREIGN KEY (ROLE_CD) REFERENCES TB_ROLE (ROLE_CD),
    CONSTRAINT FK_ROLE_USER_USER FOREIGN KEY (USER_ID) REFERENCES TB_USER (USER_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '권한-사용자 매핑 테이블';


-- =========================================================
-- 16. TB_CHAEBUN - 채번(시퀀스) 테이블 (Chaebun.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_CHAEBUN
(
    SEQ_ID        VARCHAR(45)  NOT NULL              COMMENT '채번아이디',
    SEQ_NAME      VARCHAR(100) NOT NULL              COMMENT '채번명',
    PATTERN       VARCHAR(45)  NOT NULL              COMMENT '채번패턴',
    PREFIX        VARCHAR(45)  NOT NULL              COMMENT '채번고유번호',
    CURRENT_VALUE INT          NOT NULL              COMMENT '현재 채번값',
    STEP          INT          NOT NULL DEFAULT 1    COMMENT '증가량',
    LENGTH        INT          NOT NULL DEFAULT 4    COMMENT '채번길이',
    DATEFORMAT    VARCHAR(45)                        COMMENT '날짜포맷',
    -- BaseEntity 공통 컬럼
    STS           VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER   VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE   TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER   VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP`   TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (SEQ_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '채번(시퀀스) 테이블';


-- =========================================================
-- 17. TB_IF - 인터페이스 테이블 (Interface.java)
--    `TEXT`: MySQL 예약어이므로 백틱 이스케이프
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_IF
(
    IF_ID       VARCHAR(45)  NOT NULL                COMMENT '인터페이스아이디',
    IF_NAME     VARCHAR(100) NOT NULL                COMMENT '인터페이스명',
    `TEXT`      VARCHAR(100) NOT NULL                COMMENT 'WSDL 텍스트',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (IF_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '인터페이스 테이블';


-- =========================================================
-- 18. TB_LOG_ACT - 사용자 행위 로그 테이블 (LogAct.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_LOG_ACT
(
    LOG_ID             VARCHAR(45)  NOT NULL                COMMENT '로그아이디',
    USER_ID            VARCHAR(45)                          COMMENT '사용자아이디',
    IP_ADDR            VARCHAR(45)                          COMMENT '아이피주소',
    PAGE_URL           VARCHAR(200)                         COMMENT '페이지URL',
    ACTION_TYPE        VARCHAR(45)                          COMMENT '액션타입',
    ACTION_TYPE_DETAIL VARCHAR(45)                          COMMENT '액션 내용',
    -- BaseEntity 공통 컬럼
    STS                VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER        VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE        TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER        VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP`        TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (LOG_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '사용자 행위 로그 테이블';


-- =========================================================
-- 19. TB_LOG_API - API 호출 로그 테이블 (LogApi.java)
--    LOG_ID: UUID (@PrePersist 자동 생성)
--    인덱스 4개: @Table(indexes = { ... }) 에 정의된 그대로
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_LOG_API
(
    LOG_ID        VARCHAR(36)  NOT NULL                COMMENT '로그아이디 (UUID)',
    USER_ID       VARCHAR(45)                          COMMENT '사용자아이디',
    START_DATE    TIMESTAMP(3)                         COMMENT '시작시간',
    END_DATE      TIMESTAMP(3)                         COMMENT '종료시간',
    IP_ADDR       VARCHAR(45)                          COMMENT '아이피주소',
    USER_AGENT    VARCHAR(200)                         COMMENT '브라우저정보',
    REQUEST_URI   VARCHAR(200)                         COMMENT '요청 URI',
    HTTP_METHOD   VARCHAR(45)                          COMMENT 'HTTP 메소드',
    REQUEST_BODY  TEXT                                 COMMENT '요청내용',
    RESPONSE_BODY TEXT                                 COMMENT '응답내용',
    STATUS_CODE   VARCHAR(45)                          COMMENT '상태코드',
    EXEC_TIME     BIGINT                               COMMENT '실행시간(ms)',
    -- BaseEntity 공통 컬럼
    STS           VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER   VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE   TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER   VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP`   TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (LOG_ID),
    -- @Index 어노테이션에 정의된 인덱스
    INDEX idx_access_log_end_date      (END_DATE),
    INDEX idx_access_log_user_end_date (USER_ID, END_DATE),
    INDEX idx_access_log_uri_end_date  (REQUEST_URI, END_DATE),
    INDEX idx_access_log_ip_end_date   (IP_ADDR, END_DATE)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = 'API 호출 로그 테이블';


-- =========================================================
-- 20. TB_LOG_ERROR - 에러 로그 테이블 (LogError.java)
--    ERR_ID: UUID (@PrePersist 자동 생성)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_LOG_ERROR
(
    ERR_ID      VARCHAR(36)  NOT NULL                COMMENT '에러로그아이디 (UUID)',
    USER_ID     VARCHAR(45)                          COMMENT '사용자아이디',
    IP_ADDR     VARCHAR(45)                          COMMENT '아이피주소',
    USER_AGENT  VARCHAR(200)                         COMMENT '브라우저정보',
    REQUEST_URI VARCHAR(200)                         COMMENT '요청 URI',
    HTTP_METHOD VARCHAR(45)                          COMMENT 'HTTP 메소드',
    ERR_MSG     TEXT                                 COMMENT '에러내용',
    ERR_STACK   TEXT                                 COMMENT '에러 스택트레이스',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (ERR_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '에러 로그 테이블';


-- =========================================================
-- 21. TB_MAIL_M - 메일 템플릿 테이블 (MailM.java)
--    CONTENT: columnDefinition="TEXT"
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_MAIL_M
(
    MAIL_ID     VARCHAR(45)   NOT NULL                COMMENT '메일아이디',
    LANG_TYPE   VARCHAR(45)   NOT NULL                COMMENT '언어타입',
    MAIL_NAME   VARCHAR(100)  NOT NULL                COMMENT '메일명',
    TITLE       VARCHAR(100)  NOT NULL                COMMENT '제목',
    CONTENT     TEXT          NOT NULL                COMMENT '내용',
    DESCRIPTION VARCHAR(2048)                         COMMENT '설명',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)    NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)   NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3)  NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)   NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3)  NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (MAIL_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '메일 템플릿 테이블';


-- =========================================================
-- 22. TB_MAIL_H - 메일 발송 이력 테이블 (MailH.java)
--    LOG_ID: UUID (@PrePersist 자동 생성), length=45
--    TO_ADDR, CONTENT, ERROR_MSG: columnDefinition="TEXT"
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_MAIL_H
(
    LOG_ID    VARCHAR(45)  NOT NULL  COMMENT '이력아이디 (UUID, length=45)',
    MAIL_ID   VARCHAR(45)            COMMENT '메일아이디',
    FROM_ADDR VARCHAR(45)            COMMENT '발신자',
    TO_ADDR   TEXT                   COMMENT '수신자 (TEXT)',
    TITLE     VARCHAR(100)           COMMENT '제목',
    CONTENT   TEXT                   COMMENT '내용 (TEXT)',
    SUCCESS   VARCHAR(1)             COMMENT '성공여부',
    ERROR_MSG TEXT                   COMMENT '실패사유 (TEXT)',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (LOG_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '메일 발송 이력 테이블';


-- =========================================================
-- 23. TB_MUL_LANG - 다국어 테이블 (MulLang.java)
--    복합키: (LANG_CD, LANG_TYPE, LANG_GUBUN)
--    MulLangId: langCd + langType + langGubun
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_MUL_LANG
(
    LANG_CD    VARCHAR(45)   NOT NULL                COMMENT '언어코드',
    LANG_TYPE  VARCHAR(45)   NOT NULL                COMMENT '언어유형',
    LANG_GUBUN VARCHAR(45)   NOT NULL                COMMENT '언어구분 (code, msg, label 등)',
    LANG_NM    VARCHAR(2048) NOT NULL                COMMENT '언어명',
    USE_YN     VARCHAR(1)    NOT NULL DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    -- BaseEntity 공통 컬럼
    STS        VARCHAR(1)    NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (LANG_CD, LANG_TYPE, LANG_GUBUN)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '다국어 테이블';


-- =========================================================
-- 24. TB_SCHE_M - 스케줄 마스터 테이블 (ScheM.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_SCHE_M
(
    SCHE_ID        VARCHAR(45)   NOT NULL                COMMENT '스케줄아이디',
    SCHE_NAME      VARCHAR(100)  NOT NULL                COMMENT '스케줄명',
    DESCRIPTION    VARCHAR(2048)                         COMMENT '설명',
    SCHE_GROUP     VARCHAR(45)   NOT NULL                COMMENT '스케줄러그룹명',
    CLASS_NAME     VARCHAR(100)  NOT NULL                COMMENT '클래스명',
    METHOD_NAME    VARCHAR(100)  NOT NULL                COMMENT '메소드명',
    TRIGGER_NAME   VARCHAR(100)  NOT NULL                COMMENT '트리거명',
    CRON_EXP       VARCHAR(45)   NOT NULL                COMMENT 'CRON식',
    LAST_EXEC_TIME TIMESTAMP(3)                          COMMENT '마지막실행시간',
    NEXT_EXEC_TIME TIMESTAMP(3)                          COMMENT '다음실행시간',
    USE_YN         VARCHAR(1)    NOT NULL DEFAULT 'N'    COMMENT '사용여부 (Y/N)',
    -- BaseEntity 공통 컬럼
    STS            VARCHAR(1)    NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER    VARCHAR(45)   NOT NULL                COMMENT '생성자',
    CREATE_DATE    TIMESTAMP(3)  NOT NULL                COMMENT '생성일시',
    UPDATE_USER    VARCHAR(45)   NOT NULL                COMMENT '수정자',
    `TIMESTAMP`    TIMESTAMP(3)  NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (SCHE_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '스케줄 마스터 테이블';


-- =========================================================
-- 25. TB_SCHE_H - 스케줄 실행 이력 테이블 (ScheH.java)
--    LOG_ID: UUID (@PrePersist 자동 생성)
--    EXEC_TIME: long → BIGINT (length=2048 은 JPA에서 무시됨)
--    ERROR_MSG: columnDefinition="TEXT"
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_SCHE_H
(
    LOG_ID     VARCHAR(36)  NOT NULL  COMMENT '이력아이디 (UUID)',
    SCHE_ID    VARCHAR(45)            COMMENT '스케줄아이디',
    SCHE_GROUP VARCHAR(45)            COMMENT '스케줄러그룹명',
    START_TIME TIMESTAMP(3)           COMMENT '시작시간',
    END_TIME   TIMESTAMP(3)           COMMENT '종료시간',
    EXEC_TIME  BIGINT                 COMMENT '실행시간(ms)',
    SUCCESS    VARCHAR(1)             COMMENT '성공여부',
    ERROR_MSG  TEXT                   COMMENT '실패사유 (TEXT)',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (LOG_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '스케줄 실행 이력 테이블';


-- =========================================================
-- 26. TB_SMS_M - SMS 템플릿 테이블 (SmsM.java)
--    `TEXT`: MySQL 예약어이므로 백틱 이스케이프
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_SMS_M
(
    SMS_ID      VARCHAR(45)   NOT NULL                COMMENT 'SMS아이디',
    LANG_TYPE   VARCHAR(45)   NOT NULL                COMMENT '언어타입',
    SMS_NAME    VARCHAR(100)  NOT NULL                COMMENT '템플릿명',
    `TEXT`      VARCHAR(200)  NOT NULL                COMMENT 'SMS내용',
    DESCRIPTION VARCHAR(2048)                         COMMENT '설명',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)    NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)   NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3)  NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)   NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3)  NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (SMS_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = 'SMS 템플릿 테이블';


-- =========================================================
-- 27. TB_SMS_H - SMS 발송 이력 테이블 (SmsH.java)
--    LOG_ID: UUID (@PrePersist 자동 생성), length=45
--    `TEXT`: MySQL 예약어이므로 백틱 이스케이프
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_SMS_H
(
    LOG_ID     VARCHAR(45)   NOT NULL  COMMENT '이력아이디 (UUID, length=45)',
    SMS_ID     VARCHAR(45)             COMMENT 'SMS아이디',
    FROM_PHONE VARCHAR(45)             COMMENT '발신자',
    TO_PHONE   VARCHAR(45)             COMMENT '수신자',
    `TEXT`     VARCHAR(200)            COMMENT 'SMS내용',
    SUCCESS    VARCHAR(1)              COMMENT '성공여부',
    ERROR_MSG  VARCHAR(2048)           COMMENT '실패사유',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'  COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL              COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL              COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL              COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL              COMMENT '수정일시',
    PRIMARY KEY (LOG_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = 'SMS 발송 이력 테이블';


-- =========================================================
-- 28. TB_WEB_SVC - 웹서비스 테이블 (WebSvc.java)
-- =========================================================
CREATE TABLE IF NOT EXISTS TB_WEB_SVC
(
    SVC_ID      VARCHAR(45)  NOT NULL                COMMENT '웹서비스아이디',
    SVC_NAME    VARCHAR(100) NOT NULL                COMMENT '웹서비스명',
    -- BaseEntity 공통 컬럼
    STS         VARCHAR(1)   NOT NULL DEFAULT 'C'    COMMENT '시스템상태 (C: 정상, D: 삭제)',
    CREATE_USER VARCHAR(45)  NOT NULL                COMMENT '생성자',
    CREATE_DATE TIMESTAMP(3) NOT NULL                COMMENT '생성일시',
    UPDATE_USER VARCHAR(45)  NOT NULL                COMMENT '수정자',
    `TIMESTAMP` TIMESTAMP(3) NOT NULL                COMMENT '수정일시',
    PRIMARY KEY (SVC_ID)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COMMENT = '웹서비스 테이블';
