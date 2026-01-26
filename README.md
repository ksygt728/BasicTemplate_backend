# 🚀 CBMS - Code Base Management System

> **Java Spring Boot 기반의 솔루션**  
> 마이크로서비스 아키텍처를 지원하는 모듈식 프로젝트 템플릿

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.6-6DB33F?style=flat-square&logo=spring-boot)
![MySQL](https://img.shields.io/badge/MySQL-8.0+-4479A1?style=flat-square&logo=mysql)
![JUnit 5](https://img.shields.io/badge/JUnit-5-25A162?style=flat-square&logo=junit5)
![OAuth 2.0](https://img.shields.io/badge/OAuth-2.0-4285F4?style=flat-square)
![Docker](https://img.shields.io/badge/Docker-Latest-2496ED?style=flat-square&logo=docker)
![Jenkins](https://img.shields.io/badge/Jenkins-Pipeline-D24939?style=flat-square&logo=jenkins)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

</div>

---

## 📋 목차

- [프로젝트 개요](#프로젝트-개요)
- [주요 기능](#주요-기능)
- [기술 스택](#기술-스택)
- [프로젝트 구조](#프로젝트-구조)
- [빠른 시작](#빠른-시작)
- [배포 가이드](#배포-가이드)
- [CI/CD 파이프라인](#-cicd-파이프라인-jenkins)
- [API 문서](#api-문서)
- [개발 환경 설정](#개발-환경-설정)
- [테스트](#-테스트)
- [OAuth 2.0 인증](#-oauth-20-인증)

---

## 🎯 프로젝트 개요

**CBMS(Code Base Management System)**는 모든 엔터프라이즈 애플리케이션에 필수적인 **관리자 기능 및 기반 모듈**을 구현한 Spring Boot 스타터 템플릿입니다.

### 📌 프로젝트 목표

이 프로젝트는 **서비스별 비즈니스 로직만 구현하면 되는 백엔드 템플릿**을 제공합니다.
모든 프로젝트 시작에 있어 필수로 구현해야 하는 기본적인 기능을 제공하는 것이 목적입니다.

```
┌─────────────────────────────────────────────────┐
│  CBMS                                           │
│  ├─ ✅ 관리자 메뉴                                 │
│  ├─ ✅ 인증/인가 (JWT, OAuth 2.0, RBAC)           │
│  ├─ ✅ 기본 데이터 모델 (User, Role, Menu)          │
│  ├─ ✅ CI/CD 파이프라인 (Jenkins)                  │
│  └─ ✅ 테스트 프레임워크 (JUnit 5)                  │
│                                                 │
│  + 새로운 서비스 비즈니스 로직                         │
│  └─ (Entity, Service, Controller 추가)           │
└─────────────────────────────────────────────────┘
```

### 주요 특징

✅ **Java 기술 스택** - Java 21 LTS + Spring Boot 3.3.6  
✅ **다중 환경 지원** - Local, Dev, QA, Prod 환경별 자동 설정  
✅ **자동화된 CI/CD** - Jenkins Pipeline 기반 자동 배포 & Rollback  
✅ **컨테이너화** - Docker & Docker Compose 지원  
✅ **인증/인가** - JWT 토큰 + OAuth 2.0 (Kakao, Google, Naver)  
✅ **관리자 기능** - 사용자, 역할, 메뉴 관리 시스템  
✅ **테스트 지원** - JUnit 5 + MockMvc 통합 테스트  
✅ **스케줄링** - Quartz 기반 작업 스케줄링  
✅ **메시징** - Kafka 통합 이벤트 처리

---

## 💡 주요 기능

| 기능                | 설명                                        | 기술 스택            |
| ------------------- | ------------------------------------------- | -------------------- |
| **인증/인가**       | JWT 토큰 + OAuth 2.0 (Kakao, Google, Naver) | Spring Security, JWT |
| **관리자 메뉴**     | ✅ 아래 상세 참고                           | JPA, Hibernate       |
| **데이터 접근**     | 타입 안전한 SQL 쿼리 빌더                   | jOOQ, JPA/Hibernate  |
| **API 테스트**      | 자동화된 단위 테스트 및 통합 테스트         | JUnit 5, MockMvc     |
| **이메일 발송**     | 비동기 메일 전송 지원                       | Spring Mail          |
| **SMS 발송**        | SMS 통합 서비스                             | Custom SMS Service   |
| **백그라운드 작업** | 주기적 작업 실행                            | Quartz Scheduler     |
| **이벤트 처리**     | 비동기 메시징                               | Kafka                |

### 🔐 관리자 기능 상세 (8개 카테고리)

#### 1️⃣ **사용자 관리 (User Management)**

- 사용자 정보 조회/수정/삭제
- 부서별 사용자 관리
- 사용자 역할 할당 (Role 관리)
- 사용자 활성화/비활성화
- **Entity:** `User.java`

#### 2️⃣ **역할/권한 관리 (Role & Permission)**

- 역할 정의 및 권한 설정
- 역할별 메뉴 권한 관리
- 역할별 사용자 할당
- 권한 계층 구조 관리
- **Entity:** `Role.java`, `RoleUser.java`, `RoleMenu.java`

#### 3️⃣ **메뉴 관리 (Menu Management)**

- 계층형 메뉴 구조 (상위/하위 메뉴)
- 메뉴별 URL 매핑
- 메뉴 활성화/비활성화
- 메뉴 순서 관리
- 역할별 메뉴 권한 할당
- **Entity:** `Menu.java`

#### 4️⃣ **부서 관리 (Department Management)**

- 회사별 부서 관리
- 부서 계층 구조 (상위/하위 부서)
- 부서 활성화/비활성화
- 부서별 사용자 관리
- **Entity:** `Department.java`, `Company.java`

#### 5️⃣ **공통 코드 관리 (Common Code)**

- 그룹별 코드 정의 (ComCodeM)
- 코드 속성 관리 (ComCodeT)
- 코드 상세정보 관리 (ComCodeD)
- 다국어 코드명 지원 (MulLang)
- **Entity:** `ComCodeM.java`, `ComCodeT.java`, `ComCodeD.java`

#### 6️⃣ **로그 관리 (Logging & Audit)**

| 로그 타입                  | 설명                                       | Entity          |
| -------------------------- | ------------------------------------------ | --------------- |
| **API 로그**               | API 호출 기록 (요청/응답 시간, 사용자, IP) | `LogApi.java`   |
| **사용자 행위 로그(보류)** | 페이지 방문, 클릭 등 사용자 행동 기록      | `LogAct.java`   |
| **에러 로그**              | 시스템 에러 및 예외 발생 기록              | `LogError.java` |

#### 7️⃣ **게시판 및 공지사항 (Board & Notice)**

- 게시판 글 작성/수정/삭제
- 게시판 댓글 관리
- 공지사항 관리
- 사용자별 글 관리
- **Entity:** `Bbs.java`, `BbsComment.java`

#### 8️⃣ **시스템 관리 (System Management)**

| 기능                      | 설명                       | Entity                     |
| ------------------------- | -------------------------- | -------------------------- |
| **채번(시퀀스)**          | 자동 번호 생성 (패턴 기반) | `Chaebun.java`             |
| **메일 전송 및 이력**     | 발송한 메일 이력 관리      | `MailH.java`, `MailM.java` |
| **SMS 전송 및 이력**      | 발송한 SMS 이력 관리       | `SmsH.java`, `SmsM.java`   |
| **스케줄 관리**           | Quartz 작업 스케줄 관리    | `ScheH.java`, `ScheM.java` |
| **다국어**                | 다국어 문구 관리           | `MulLang.java`             |
| **외부 인터페이스(보류)** | 외부 API 연동 관리         | `Interface.java`           |
| **웹 서비스(보류)**       | 웹서비스 통합 관리         | `WebSvc.java`              |

---

## 🛠️ 기술 스택

### 백엔드 프레임워크

- **Java 21 LTS** - 최신 Java 장기 지원 버전
- **Spring Boot 3.3.6** - 엔터프라이즈급 프레임워크
- **Spring Web MVC** - RESTful API 구축
- **Spring Data JPA** - ORM 및 데이터 접근 계층
- **Spring Security** - 인증 및 권한 관리 + OAuth 2.0
- **Spring OAuth2 Client** - OAuth 2.0 인증 (Kakao, Google, Naver)

### 테스트 프레임워크

- **JUnit 5** - 최신 Java 테스팅 프레임워크
- **Spring Test** - Spring Boot 통합 테스트
- **MockMvc** - REST API 모의 테스트
- **AssertJ** - Fluent Assertion 라이브러리

### 데이터베이스

- **MySQL 8.0+** - 프로덕션 관계형 데이터베이스
- **jOOQ** - 타입 안전한 SQL 쿼리 빌더
- **JPA/Hibernate** - ORM 프레임워크
- **Flyway/Liquibase** - DB 마이그레이션

### 기타 핵심 라이브러리

| 라이브러리      | 용도                     | 버전                |
| --------------- | ------------------------ | ------------------- |
| Lombok          | 보일러플레이트 코드 제거 | 1.18.30             |
| Kafka           | 이벤트 메시징            | Spring Cloud Stream |
| Quartz          | 작업 스케줄링            | 2.3.x               |
| MySQL Connector | DB 드라이버              | 8.3.0               |
| Spring Mail     | 메일 발송 (비동기)       | Spring Boot         |
| OAuth2          | OAuth 2.0 인증 통합      | Spring Security 6   |

### 캐시 & 세션 관리

- **Redis** - 고속 캐시 및 세션 저장소
- **Lettuce** - Redis 클라이언트 (비동기, 논블로킹)
- **Spring Data Redis** - Redis 통합 라이브러리
- **TTL 설정** - 자동 캐시 만료 시간 관리

### 메시징 & 이벤트

- **Kafka** - 이벤트 스트리밍 플랫폼
- **Spring Kafka** - Kafka 통합 지원
- **Spring Mail** - 메일 발송 (비동기)
- **FreeMarker** - 메일 템플릿 엔진
- **CoolSMS SDK** - SMS 통합 발송

### DevOps & 배포

- **Docker** - 컨테이너화 (다단계 빌드)
- **Docker Compose** - 로컬 개발 환경
- **Jenkins** - CI/CD 파이프라인
- **Git** - 버전 관리(Git flow 전략)
- **Maven** - 빌드 자동화

### 클라우드 인프라

- **AWS EC2** - 애플리케이션 호스팅
- **Docker Hub** - 이미지 레지스트리

---

## 📁 프로젝트 구조

```
backend/
├── app/                              # 메인 애플리케이션
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/basic/app/
│   │   │   │   ├── AppApplication.java           # 메인 애플리케이션 클래스
│   │   │   │   ├── annotation/                   # 커스텀 애노테이션
│   │   │   │   ├── aop/                          # 횡단 관심사 (로깅, 감시)
│   │   │   │   ├── api/                          # 외부 API 통합
│   │   │   │   ├── auth/                         # 인증 관련 (JWT, OAuth)
│   │   │   │   ├── config/                       # Spring 설정 클래스
│   │   │   │   ├── controller/                   # REST API 컨트롤러
│   │   │   │   ├── dto/                          # 데이터 전송 객체
│   │   │   │   ├── entity/                       # JPA 엔티티 (DB 모델)
│   │   │   │   ├── exception/                    # 커스텀 예외 처리
│   │   │   │   ├── jwt/                          # JWT 토큰 처리
│   │   │   │   ├── kafka/                        # Kafka 프로듀서/컨슈머
│   │   │   │   ├── mail/                         # 이메일 발송 서비스
│   │   │   │   ├── quartz/                       # 스케줄 작업
│   │   │   │   ├── repository/                   # 데이터 접근 계층 (jOOQ/JPA)
│   │   │   │   ├── service/                      # 비즈니스 로직 계층
│   │   │   │   ├── sms/                          # SMS 발송 서비스
│   │   │   │   └── util/                         # 유틸리티 클래스
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── application.yml                # 기본 설정
│   │   │       ├── application-local.yml          # 로컬 환경 설정
│   │   │       ├── application-dev.yml            # 개발 환경 설정
│   │   │       ├── application-qa.yml             # QA 환경 설정
│   │   │       ├── application-prod.yml           # 운영 환경 설정
│   │   │       ├── application-test.yml           # 테스트 환경 설정
│   │   │       ├── db/                            # DB 마이그레이션 스크립트
│   │   │       └── docs/                          # API 문서
│   │   │
│   │   └── test/
│   │       ├── java/                              # 단위 테스트
│   │       └── resources/                         # 테스트 설정
│   │
│   ├── target/                                    # 컴파일된 결과 (Git 무시)
│   ├── pom.xml                                    # Maven 설정파일
│   ├── mvnw / mvnw.cmd                            # Maven Wrapper
│   ├── Dockerfile                                 # 컨테이너 이미지 빌드
│   ├── docker-compose.yml                         # 현재 프로젝트 compose파일
│   ├── docker-compose-infra.dev.yml               # 개발 인프라 구성
│   ├── docker-compose-infra.local.yml             # 로컬 인프라 구성
│   ├── docker-compose-infra.qa.yml                # QA 인프라 구성
│   ├── docker-compose-infra.prod.yml              # 운영 인프라 구성
│   ├── Jenkinsfile                                # CI/CD 파이프라인
│   ├── JenkinsCasC.yaml                           # Jenkins Configuration as Code
│   └── README.md                                  # 이 파일
```

---

## 🚀 빠른 시작

### 필수 사항

- **Java 21+** 설치
- **Maven 3.8+** 또는 Maven Wrapper 사용
- **Docker & Docker Compose** (선택사항)
- **MySQL 8.0+** 또는 Docker로 실행

### 1️⃣ 저장소 클론

```bash
git clone https://github.com/ksygt728/BasicTemplate_backend.git
cd BasicTemplate_backend/backend/app
```

### 2️⃣ Infra 설정

```bash
# 개발 환경 Infra 실행 (MySQL, Redis 등)
docker-compose -p cbsk-dev -f /home/ec2-user/infra/docker-compose-infra.dev.yml up -d --build
```

### 3️⃣ 애플리케이션 실행

#### VSCode에서 실행

1. **프로젝트 폴더 열기**
   - VSCode에서 `backend/app` 폴더를 Open

2. **Extension 설치** (필요시)
   - Extension Marketplace에서 "Extension Pack for Java" 설치
   - 또는 "Spring Boot Extension Pack" 설치

3. **애플리케이션 실행**
   - `src/main/java/com/basic/app/AppApplication.java` 파일 열기
   - 상단의 "Run" 또는 "Debug" 버튼 클릭
   - 또는 `F5` 키를 눌러 디버그 모드로 실행

4. **애플리케이션 확인**
   ```
   ✅ 애플리케이션 시작: http://localhost:8080
   ```

---

## 📦 환경별 프로필 설정

### 프로필 종류

| 프로필    | 설명      | 데이터베이스          | 용도             |
| --------- | --------- | --------------------- | ---------------- |
| **local** | 로컬 개발 | localhost:3306        | 개발자 로컬 머신 |
| **dev**   | 개발 서버 | dev.cbmsori.com:3306  | 개발 서버 배포   |
| **qa**    | QA 서버   | qa.cbmsori.com:3307   | 품질 보증 테스트 |
| **prod**  | 운영 서버 | prod.cbmsori.com:3308 | 실제 운영 환경   |
| **test**  | 테스트    | 테스트용 프로파일     | 자동화 테스트    |

### 프로필 활성화

```bash
# Maven 빌드 시
./mvnw clean package -DskipTests

# 애플리케이션 실행 시
java -jar app.jar --spring.profiles.active=dev

# 환경 변수로
export SPRING_PROFILES_ACTIVE=dev
java -jar app.jar
```

---

## 🐳 Docker 배포

### 로컬 개발 환경 (Docker Compose)

```bash
# 전체 스택 실행 (MySQL + Redis + 기타)
docker-compose -f docker-compose-infra.local.yml up -d

# 애플리케이션 빌드 및 실행
docker build -t cbms-back:latest .
docker run -d --name cbms-back \
  -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=local \
  cbms-back:latest
```

### 이미지 빌드 (다단계 빌드)

```bash
# 최적화된 이미지 생성
docker build -t docker.io/seungyeon728/cbms-back-dev:latest .

# 레지스트리에 push
docker login docker.io
docker push docker.io/seungyeon728/cbms-back-dev:latest
```

---

## 🔄 CI/CD 파이프라인 (Jenkins)

### 파이프라인 단계

```
┌─────────────────────────────────────────────────────┐
│ 1️⃣ Init Parameters                                  │
│    (ENV, BRANCH, GIT_TAG, SERVER, PORT 확인)         │
└────────────────────┬────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────┐
│ 2️⃣ Checkout                                         │
│    Git Branch 또는 Tag 선택                           │
│    ✅ Rollback 지원: GIT_TAG 파라미터 활용               │
└────────────────────┬────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────┐
│ 3️⃣ Maven Test                                       │
│    ./mvnw clean test -Dspring.profiles.active=test  │
└────────────────────┬────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────┐
│ 4️⃣ Docker Build                                     │
│    docker build -t image:tag .                      │
└────────────────────┬────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────┐
│ 5️⃣ Docker Push (Remote Only)                        │
│    docker push image:tag                            │
│    docker image prune -a -f (정리)                   │
└────────────────────┬────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────┐
│ 6️⃣ Deploy (Remote Server)                           │
│    SSH로 원격 서버 배포                                 │
│    docker-compose up -d                             │
└────────────────────┬────────────────────────────────┘
                     ↓
┌─────────────────────────────────────────────────────┐
│ 7️⃣ Git Tag (버전 기록)                                │
│    git tag -a back-dev-{date}-{buildNum}            │
│    git push origin {TAG}                            │
└─────────────────────────────────────────────────────┘
```

### Jenkins 빌드 파라미터

| 파라미터        | 기본값          | 설명                            |
| --------------- | --------------- | ------------------------------- |
| `ENV`           | dev             | 배포 환경 (dev, qa, prod)       |
| `BRANCH`        | main            | 빌드할 Git 브랜치               |
| `GIT_TAG`       | HEAD            | 롤백할 Tag (비워두면 HEAD 사용) |
| `DEPLOY_SERVER` | dev.cbmsori.com | 대상 서버 IP/호스트명           |
| `BACK_PORT`     | 8080            | 애플리케이션 포트               |

### Rollback 방법

```bash
# 1. Jenkins 대시보드 → CBMS-DEV-BACK → "Build with Parameters"
# 2. GIT_TAG 파라미터에 원하는 태그 입력
#    예: back-dev-2026-01-23_16-8
# 3. Build 클릭
# ✅ 해당 태그의 코드로 배포 완료
```

---

## 📚 API 문서

### Swagger/OpenAPI (선택사항)

```bash
http://dev.cbmsori.com:8080/swagger-ui/index.html
```

### REST API 예시

#### 1. 로그인 (JWT 토큰 획득)

```bash
POST /api/auth/login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "password123"
}

# 응답
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "..."
}
```

#### 2. 인증된 요청 (Bearer Token)

```bash
GET /api/users/profile
Authorization: Bearer {token}
```

---

## ⚙️ 개발 환경 설정

### IDE 설정 (VSCode)

1. **Extension 설치**
   - Extension Marketplace에서 다음 확장 프로그램 설치:
     - **Extension Pack for Java** (Microsoft)
     - **Spring Boot Extension Pack** (Pivotal)
     - **Lombok Annotations Support for VS Code** (GabrielBB)

2. **Lombok 지원 활성화**
   - Settings (Ctrl + ,) → "lombok" 검색
   - `"java.jdt.ls.vmargs"` 에 다음 추가:
     ```
     -javaagent:<path-to-lombok>/lombok.jar
     ```

3. **Maven 설정 (VSCode)**
   - Command Palette (Ctrl + Shift + P) → "Maven" 검색
   - Maven 명령어 실행 또는 작업 팔렛트에서 Maven 작업 실행 가능
   - 디버그: `F5` 키를 눌러 AppApplication 실행 또는
   - 또는 `Run` → `Start Debugging` 메뉴 사용

### Maven 설정

```bash
# 로컬 저장소 캐시 설정
./mvnw dependency:go-offline

# 특정 프로필로 빌드
./mvnw clean package -DskipTests

# jOOQ 코드 생성 (DB 스키마 변경 시)
./mvnw clean compile -Pdev jooq-codegen:generate
```

### 로깅 설정

```yaml
# application.yml
logging:
  level:
    root: INFO
    com.basic.app: DEBUG
    org.springframework.security: DEBUG
  pattern:
    console: "%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
```

---

## � 로깅

```bash
# 실시간 로그 확인 (Docker)
docker logs -f cbms-back

# 컨테이너 내 로그 파일
docker exec cbms-back tail -f /app/logs/app.log
```

---

## 🧪 테스트

### 테스트 전략

이 프로젝트는 **JUnit 5** 기반의 포괄적인 테스트 지원을 제공합니다:

```
┌─────────────────────────────────────────────┐
│         테스트 피라미드 (권장)                   │
├─────────────────────────────────────────────┤
│                    E2E Tests                │
│            (Selenium, Cypress)              │
├─────────────────────────────────────────────┤
│             Integration Tests               │
│          (MockMvc, TestContainers)          │
├─────────────────────────────────────────────┤
│              Unit Tests (JUnit 5)           │
│        (Service, Repository, Util)          │
└─────────────────────────────────────────────┘
```

### 단위 테스트 실행

```bash
# 모든 테스트 실행
./mvnw test

# 특정 테스트 클래스 실행
./mvnw test -Dtest=UserServiceTest

# 특정 테스트 메서드 실행
./mvnw test -Dtest=UserServiceTest#testFindById

# 테스트 커버리지 리포트 (JaCoCo)
./mvnw test jacoco:report
# 리포트 위치: target/site/jacoco/index.html
```

### MockMvc를 이용한 API 테스트 예시

```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testGetUserProfile() throws Exception {
        mockMvc.perform(get("/api/users/profile")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("admin"));
    }
}
```

### 통합 테스트 (Spring Boot Test)

```bash
# test 프로필로 빌드
./mvnw clean test -Dspring.profiles.active=test
```

---

## 🔐 OAuth 2.0 인증

### 지원 플랫폼

- ✅ **Kakao** - 소셜 로그인
- ✅ **Google** - 소셜 로그인
- ✅ **Naver** - 소셜 로그인

### Kakao OAuth 설정

자세한 설정 가이드는 [KAKAO_OAUTH_SETUP_GUIDE.md](KAKAO_OAUTH_SETUP_GUIDE.md)를 참고하세요.

```yaml
# application-dev.yml
spring:
  security:
    oauth2:
      client:
        registration:
          kakao:
            client-id: ${KAKAO_CLIENT_ID}
            client-secret: ${KAKAO_CLIENT_SECRET}
            redirect-uri: http://localhost:8080/login/oauth2/code/kakao
            authorization-grant-type: authorization_code
            client-name: Kakao
            client-authentication-method: client_secret_basic
        provider:
          kakao:
            authorization-uri: https://kauth.kakao.com/oauth/authorize
            token-uri: https://kauth.kakao.com/oauth/token
            user-info-uri: https://kapi.kakao.com/v2/user/me
            user-name-attribute: id
```

### OAuth 로그인 요청

```bash
# Kakao 로그인 페이지로 리다이렉트
GET http://localhost:8080/oauth2/authorization/kakao

# 콜백 처리 (자동)
GET http://localhost:8080/login/oauth2/code/kakao?code=...&state=...
```

---

## 📞 지원 및 문의

- 📧 Email: ksygt728@gmail.com
- 🔗 GitHub: [ksygt728/BasicTemplate_backend](https://github.com/ksygt728/BasicTemplate_backend)
- 📖 문서: [Notion 가이드](https://notion.so/ksygt728study)

---

## 🎓 학습 자료

- [Spring Boot 공식 문서](https://spring.io/projects/spring-boot)
- [jOOQ 튜토리얼](https://www.jooq.org/doc/latest/)
- [Docker 가이드](https://docs.docker.com/)
- [Jenkins 파이프라인](https://www.jenkins.io/doc/book/pipeline/)

---

<div align="center">

Made with by [ksygt728](https://github.com/ksygt728)

</div>
