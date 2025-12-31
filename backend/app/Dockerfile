############################################
# 1단계: Maven 빌드
############################################
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /build

# 의존성 캐시
COPY pom.xml .
RUN mvn dependency:go-offline

# 소스 복사 후 빌드
COPY src ./src
RUN mvn clean package -DskipTests


############################################
# 2단계: 실행
############################################
FROM eclipse-temurin:17-jre

WORKDIR /app

# 빌드된 jar만 복사
COPY --from=builder /build/target/*.jar app.jar

# Spring Boot 기본 포트 (문서용)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]