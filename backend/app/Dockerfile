############################################
# 1단계: Maven Wrapper 빌드 (JDK 21)
############################################
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

# Maven Wrapper 관련 파일 먼저 복사 (캐시 최적화)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# mvnw 실행 권한
RUN chmod +x mvnw

# 의존성 캐시
RUN ./mvnw -B dependency:go-offline

# 소스 복사 후 테스트 + 빌드
COPY src ./src
RUN ./mvnw clean package -DskipTests


############################################
# 2단계: 실행 (JRE 21)
############################################
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]