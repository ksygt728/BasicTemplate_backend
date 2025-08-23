# 카카오 OAuth 설정 가이드

## 현재 401 오류 해결을 위한 체크리스트

### 1. 카카오 개발자 콘솔 확인 사항

- URL: https://developers.kakao.com/console/app

#### A. 앱 설정 > 앱 키 확인

```
REST API 키: 48a605518fb3519e52a95dd78d205a1b
(현재 application-dev.yml의 spring.kakao.api-key와 일치하는지 확인)
```

#### B. 제품 설정 > 카카오 로그인 설정

1. **카카오 로그인 활성화 상태 확인**

   - "ON" 상태여야 함

2. **Redirect URI 등록 확인**

   ```
   등록된 URI: http://localhost:8080/api/auth/kakao
   (현재 application-dev.yml의 spring.kakao.redirect-uri와 정확히 일치해야 함)
   ```

3. **Client Secret 설정 확인**
   - "보안" 탭에서 Client Secret 사용 여부 확인
   - 사용 중이라면 현재 설정값과 일치하는지 확인: `D9QHopbDBavL1L9SfX9vipn6TZJrJ1o4`

### 2. 일반적인 401 오류 원인

#### A. REST API 키 불일치

- 카카오 콘솔의 REST API 키와 설정파일의 api-key가 다름
- 해결: 두 값을 일치시키기

#### B. Redirect URI 불일치

- 카카오 콘솔에 등록된 URI와 설정파일의 redirect-uri가 다름
- 대소문자, 포트번호, 경로까지 정확히 일치해야 함
- 해결: 정확한 URI로 수정

#### C. Client Secret 관련 문제

- 콘솔에서 Client Secret을 사용하도록 설정했으나 코드에서 전송하지 않음
- 또는 잘못된 Client Secret 값 사용
- 해결: Client Secret 사용 여부를 일치시키기

#### D. Authorization Code 관련

- 만료된 코드 사용 (보통 10분 유효)
- 이미 사용된 코드 재사용 (일회용)
- 해결: 새로운 인증 코드로 재시도

### 3. 디버깅 단계

#### 단계 1: 설정값 검증

```bash
# 애플리케이션 시작 후 로그에서 확인
- API Key: 설정됨/설정되지 않음
- Redirect URI: http://localhost:8080/api/auth/kakao
- Client Secret 사용 여부: true/false
```

#### 단계 2: 카카오 콘솔과 비교

1. 카카오 개발자 콘솔 로그인
2. 해당 앱 선택
3. 앱 키 탭에서 REST API 키 복사하여 설정파일과 비교
4. 카카오 로그인 탭에서 Redirect URI 확인

#### 단계 3: 테스트 요청 분석

- 로그에서 실제 전송되는 파라미터 확인
- 카카오 응답 메시지에서 구체적인 오류 원인 파악

### 4. 설정 수정이 필요한 경우

#### application-dev.yml 수정

```yaml
spring:
  kakao:
    api-key: [카카오 콘솔의 정확한 REST API 키]
    client-secret-key: [카카오 콘솔의 Client Secret, 사용하지 않으면 비워두기]
    redirect-uri: [카카오 콘솔에 등록한 정확한 Redirect URI]
    access-token-uri: https://kauth.kakao.com/oauth/token
```

### 5. 테스트 방법

1. 애플리케이션 재시작
2. OAuth 인증 플로우 다시 시도
3. 로그에서 상세한 오류 정보 확인
4. 필요시 설정 재조정

## 추가 참고사항

- 카카오 OAuth는 HTTPS에서만 동작하는 것이 권장되지만, localhost는 HTTP도 허용
- Redirect URI는 정확히 일치해야 하며, 쿼리 파라미터나 fragment는 포함하지 않음
- Client Secret 사용은 선택사항이지만, 사용 설정한 경우 반드시 포함해야 함
