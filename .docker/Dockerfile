# 1. 빌드 환경
FROM amazoncorretto:17-alpine-jdk AS builder
WORKDIR /app

# [최적화 1] 변경이 잦은 소스코드보다, 변경이 적은 '설정 파일'을 먼저 복사
COPY ../gradlew .
COPY ../gradle gradle
COPY ../build.gradle.kts settings.gradle.kts ./

# [최적화 2] 소스코드가 없어도 의존성(라이브러리)만 먼저 다운로드
# 캐시 재사용
RUN ./gradlew dependencies --no-daemon

# 실제 소스코드를 복사, 빌드
COPY ../src src
RUN ./gradlew bootJar -x test --no-daemon

# ---------------------------------------------------

# 2. 실행 환경
# [최적화 3] JDK가 아닌 순수 JRE 이미지를 사용하여 용량을 대폭 축소
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]