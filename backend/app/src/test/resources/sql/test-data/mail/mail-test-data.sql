-- 메일 템플릿 테스트 데이터
INSERT INTO TB_MAIL_M (MAIL_ID, LANG_TYPE, MAIL_NAME, TITLE, CONTENT, DESCRIPTION, STS, CREATE_USER, CREATE_DATE, UPDATE_USER, TIMESTAMP) VALUES
('[JUnit]MAIL001', 'ko', '[JUnit]회원가입메일', '[JUnit]회원가입을 축하합니다', '[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>', '[JUnit]회원가입 완료 시 발송되는 메일', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]MAIL002', 'en', '[JUnit]SignupMail', '[JUnit]Welcome! Registration Complete', '[JUnit]<h1>Welcome!</h1><p>Thank you for signing up.</p>', '[JUnit]Welcome email for new users', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]MAIL003', 'ko', '[JUnit]비밀번호초기화메일', '[JUnit]비밀번호가 초기화되었습니다', '[JUnit]<h1>비밀번호 초기화</h1><p>비밀번호가 초기화되었습니다. 새로운 비밀번호로 로그인해주세요.</p>', '[JUnit]비밀번호 초기화 시 발송되는 메일', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]MAIL004', 'ko', '[JUnit]승인완료메일', '[JUnit]승인이 완료되었습니다', '[JUnit]<h1>승인 완료</h1><p>요청하신 내용이 승인되었습니다.</p>', '[JUnit]승인 완료 시 발송되는 메일', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]MAIL005', 'ko', '[JUnit]공지사항메일', '[JUnit]중요 공지사항입니다', '[JUnit]<h1>공지사항</h1><p>중요한 공지사항이 있습니다. 확인해주세요.</p>', '[JUnit]공지사항 발송용 메일', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]MAIL006', 'ko', '[JUnit]이벤트메일', '[JUnit]특별 이벤트에 참여하세요', '[JUnit]<h1>특별 이벤트</h1><p>특별한 이벤트에 참여해보세요!</p>', '[JUnit]이벤트 안내 메일', 'C', 'testUser', NOW(3), 'testUser', NOW(3));

-- 메일 발송 이력 테스트 데이터
INSERT INTO TB_MAIL_H (LOG_ID, MAIL_ID, FROM_ADDR, TO_ADDR, TITLE, CONTENT, SUCCESS, ERROR_MSG, STS, CREATE_USER, CREATE_DATE, UPDATE_USER, TIMESTAMP) VALUES
('11111111-1111-1111-1111-111111111111', '[JUnit]MAIL001', 'admin@test.com', 'user1@test.com', '[JUnit]회원가입을 축하합니다', '[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('11111111-1111-1111-1111-111111111112', '[JUnit]MAIL001', 'admin@test.com', 'user1@test.com', '[JUnit]회원가입을 축하합니다', '[JUnit]<h1>회원가입 축하</h1><p>안녕하세요. 회원가입을 축하드립니다.</p>', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('22222222-2222-2222-2222-222222222222', '[JUnit]MAIL002', 'admin@test.com', 'user2@test.com', '[JUnit]Welcome! Registration Complete', '[JUnit]<h1>Welcome!</h1><p>Thank you for signing up.</p>', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('33333333-3333-3333-3333-333333333333', '[JUnit]MAIL003', 'admin@test.com', 'user3@test.com', '[JUnit]비밀번호가 초기화되었습니다', '[JUnit]<h1>비밀번호 초기화</h1><p>비밀번호가 초기화되었습니다.</p>', 'N', '[JUnit]SMTP 연결 실패', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('44444444-4444-4444-4444-444444444444', '[JUnit]MAIL004', 'admin@test.com', 'user4@test.com', '[JUnit]승인이 완료되었습니다', '[JUnit]<h1>승인 완료</h1><p>요청하신 내용이 승인되었습니다.</p>', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('55555555-5555-5555-5555-555555555555', '[JUnit]MAIL005', 'admin@test.com', 'user5@test.com', '[JUnit]중요 공지사항입니다', '[JUnit]<h1>공지사항</h1><p>중요한 공지사항이 있습니다.</p>', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3));