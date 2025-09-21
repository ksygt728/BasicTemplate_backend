-- BBS 테스트 데이터
INSERT INTO TB_BBS (
    BBS_ID, BBS_TYPE, TITLE, CONTENT, WRITOR, WRITE_DATE,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 1. 정상 조회용 게시글
('[JUnit]BBS001', '[JUnit]공지', '[JUnit]공지사항 제목1', '[JUnit]공지사항 내용입니다.', '[JUnit]admin001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 2. 수정용 게시글  
('[JUnit]BBS002', '[JUnit]일반', '[JUnit]일반게시글 제목2', '[JUnit]일반게시글 내용입니다.', '[JUnit]user001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 3. 삭제 대상 게시글
('[JUnit]BBS003', '[JUnit]Q&A', '[JUnit]질문게시글 제목3', '[JUnit]질문게시글 내용입니다.', '[JUnit]mgr001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 4. 이미 삭제된 게시글 (sts = 'D')
('[JUnit]BBS004', '[JUnit]공지', '[JUnit]삭제된게시글', '[JUnit]삭제된 내용입니다.', '[JUnit]admin001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D'),

-- 5. 검색 테스트용 추가 데이터
('[JUnit]BBS005', '[JUnit]이벤트', '[JUnit]이벤트 제목5', '[JUnit]이벤트 내용입니다.', '[JUnit]dev001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]BBS006', '[JUnit]자유', '[JUnit]자유게시글 제목6', '[JUnit]자유게시글 내용입니다.', '[JUnit]design001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]BBS007', '[JUnit]공지', '[JUnit]중요공지 제목7', '[JUnit]중요공지 내용입니다.', '[JUnit]admin001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C');

-- BBS Comment 테스트 데이터
INSERT INTO TB_BBS_COMMENT (
    COMMENT_ID, BBS_ID, CONTENT, WRITOR, WRITE_DATE,
    CREATE_DATE, CREATE_USER, TIMESTAMP, UPDATE_USER, STS
) VALUES 
-- 1. BBS001에 대한 댓글
('[JUnit]COMMENT001', '[JUnit]BBS001', '[JUnit]첫번째 댓글 내용입니다.', '[JUnit]user001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),
('[JUnit]COMMENT002', '[JUnit]BBS001', '[JUnit]두번째 댓글 내용입니다.', '[JUnit]mgr001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 2. BBS002에 대한 댓글
('[JUnit]COMMENT003', '[JUnit]BBS002', '[JUnit]BBS002의 댓글입니다.', '[JUnit]dev001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 3. 삭제 대상 댓글
('[JUnit]COMMENT004', '[JUnit]BBS003', '[JUnit]삭제할 댓글입니다.', '[JUnit]design001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'C'),

-- 4. 이미 삭제된 댓글 (sts = 'D')
('[JUnit]COMMENT005', '[JUnit]BBS001', '[JUnit]삭제된 댓글입니다.', '[JUnit]admin001', NOW(), NOW(), 'SYSTEM', NOW(), 'SYSTEM', 'D');