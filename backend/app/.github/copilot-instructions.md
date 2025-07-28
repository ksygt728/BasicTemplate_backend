## ㅁ

## 🚀 BasicTemplate 백엔드 AI 에이전트 지침

### 🌟 프로젝트 개요

이 프로젝트는 Spring Boot 3.5.3 기반의 **REST API 템플릿 프로젝트**예요. 🏗️ 시스템 인터페이스 관리에 중점을 두고 있으며, **Java 21**과 **계층형 아키텍처 패턴**을 사용하고 있어요.

### 🏛️ 주요 아키텍처 패턴

#### 🌐 REST API 구조

- **컨트롤러 위치**: `src/main/java/com/basic/app/controller` 📁 에 있어요.
- **컨트롤러 명명 규칙**: `{도메인}Controller.java` 형식으로 작성해요. 📝
- **API 메서드**: 표준 HTTP 메서드 (GET, POST, PUT, DELETE)를 사용하고, 일관된 URL 패턴을 유지해요.
- **예시**: `AdminInterfaceController.java`를 참고하면 표준 컨트롤러 구조를 알 수 있어요.

#### 📤 응답 패턴

- 모든 REST 응답은 일관된 형식을 위해 `ApiResponse<T>` 래퍼를 사용해요. 📦
- 성공 시에는 표준 HTTP 상태 코드 \*\*200 (OK)\*\*을 사용해요. ✅
- **예시**:
  ```java
  return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(results));
  ```

#### ✍️ 요청 유효성 검사

- Spring의 `@Validated` 어노테이션과 유효성 검사 그룹을 활용해요.
- 그룹 정의: `com.basic.app.dto.group` 패키지에 있어요.
  - `CreateGroup.class`: 생성(Create) 작업용 ➕
  - `UpdateGroup.class`: 업데이트(Update) 작업용 🔄

#### 🛠️ 서비스 계층 패턴

- **서비스 인터페이스**: `src/main/java/com/basic/app/service/interfaces` 에 정의되어 있어요.
- **구현체**: `src/main/java/com/basic/app/service` 에 위치해요.
- **응답 형식**: 서비스는 유연한 응답 구조를 위해 `Map<String, Object>`를 반환해요. 🗺️

### ⚙️ 프로젝트 설정

#### 🌳 애플리케이션 속성

애플리케이션 속성은 환경별로 설정돼요.

- `application.yml`: 기본 설정 🏠
- `application-dev.yml`: 개발 환경 💻
- `application-qa.yml`: QA 환경 🧪
- `application-prod.yml`: 운영 환경 🚀

#### 🗄️ 데이터베이스 설정

- **MySQL 8**과 **JPA/Hibernate**를 사용해요.
- **물리적 명명 전략**: `PhysicalNamingStrategyStandardImpl`을 사용해요.
- \*\*OSIV (Open Session in View)\*\*는 성능을 위해 비활성화되어 있어요. 🚫

### 📚 문서화 규칙

- **한국어 주석**: 다음 형식을 사용해요.
  ```java
  /**
   * @파일명   : ClassName.java
   * @설명     : 간략한 설명
   * @작성자   : 작성자 이름
   * @작성일   : YYYY.MM.DD
   * @변경이력 :
   * YYYY.MM.DD     작성자       변경 내용
   */
  ```

### 📋 API 요구사항 패턴

- 메서드 주석에 다음 형식으로 요구사항을 문서화해요.
  ```java
  /* [REQ_ADM_XXX] [화면 : 화면명] [기능 : 기능명] */
  ```

### 💻 개발 워크플로

1.  프로젝트는 배포를 위해 **WAR 파일**로 패키징돼요. 📦
2.  **Maven**을 사용하여 빌드해요: `mvn clean package` 🔨
3.  기본적으로 `application.yml`에 개발(development) 프로필이 활성화되어 있어요. 🧑‍💻

### ✅ 따라야 할 공통 패턴

1.  REST 응답 시에는 항상 **`ApiResponse` 래퍼**를 사용해요. 📦
2.  생성/업데이트 작업에는 **유효성 검사 그룹**을 사용해요. 🧐
3.  새 파일 작성 시에는 확립된 **한국어 문서화 형식**을 따르세요. 📝
4.  새로운 비즈니스 로직은 **서비스 인터페이스**를 구현하여 작성해요. 🤝
5.  메서드 주석에 \*\*요구사항 코드 `[REQ_XXX_XXX]`\*\*를 사용해요. 🔖

---
