-- SMS 템플릿 테스트 데이터
INSERT INTO TB_SMS_M (SMS_ID, LANG_TYPE, SMS_NAME, TEXT, DESCRIPTION, STS, CREATE_USER, CREATE_DATE, UPDATE_USER, TIMESTAMP) VALUES
('[JUnit]SMS001', 'ko', '[JUnit]회원가입인증', '[JUnit]회원가입 인증번호는 {code}입니다.', '[JUnit]회원가입 시 발송되는 인증 SMS', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]SMS002', 'en', '[JUnit]SignupAuth', '[JUnit]Your verification code is {code}', '[JUnit]SMS for user registration verification', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]SMS003', 'ko', '[JUnit]비밀번호초기화', '[JUnit]비밀번호 초기화 인증번호는 {code}입니다.', '[JUnit]비밀번호 초기화 시 발송되는 SMS', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]SMS004', 'ko', '[JUnit]결제알림', '[JUnit]{amount}원 결제가 완료되었습니다.', '[JUnit]결제 완료 시 발송되는 알림 SMS', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]SMS005', 'ko', '[JUnit]주문확인', '[JUnit]주문번호 {orderNo}의 주문이 확인되었습니다.', '[JUnit]주문 확인 시 발송되는 SMS', 'D', 'testUser', NOW(3), 'testUser', NOW(3)),
('[JUnit]SMS006', 'ko', '[JUnit]배송알림', '[JUnit]택배가 배송 시작되었습니다. 운송장번호: {trackingNo}', '[JUnit]배송 시작 시 발송되는 알림 SMS', 'C', 'testUser', NOW(3), 'testUser', NOW(3));

-- SMS 발송 이력 테스트 데이터  
INSERT INTO TB_SMS_H (LOG_ID, SMS_ID, FROM_PHONE, TO_PHONE, TEXT, SUCCESS, ERROR_MSG, STS, CREATE_USER, CREATE_DATE, UPDATE_USER, TIMESTAMP) VALUES
('11111111-1111-1111-1111-111111111111', '[JUnit]SMS001', '01012345678', '01098765432', '[JUnit]회원가입 인증번호는 123456입니다.', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('11111111-1111-1111-1111-111111111112', '[JUnit]SMS001', '01012345678', '01098765432', '[JUnit]회원가입 인증번호는 654321입니다.', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('22222222-2222-2222-2222-222222222222', '[JUnit]SMS002', '01012345678', '01087654321', '[JUnit]Your verification code is 789012', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('33333333-3333-3333-3333-333333333333', '[JUnit]SMS003', '01012345678', '01076543210', '[JUnit]비밀번호 초기화 인증번호는 345678입니다.', 'N', '[JUnit]SMS 발송 실패', 'C', 'testUser', NOW(3), 'testUser', NOW(3)),
('44444444-4444-4444-4444-444444444444', '[JUnit]SMS004', '01012345678', '01065432109', '[JUnit]30000원 결제가 완료되었습니다.', 'Y', NULL, 'C', 'testUser', NOW(3), 'testUser', NOW(3));