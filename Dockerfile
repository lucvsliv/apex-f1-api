# 1. 어떤 환경에서 빌드할 것인가? (운영체제 + Java 버전)
# Amazon Corretto는 아마존에서 무료로 제공하고 최적화해둔 믿을 수 있는 JDK 17 버전입니다.
FROM amazoncorretto:17-alpine-jdk AS builder

# 2. 컨테이너 내부의 작업 디렉토리를 /app 으로 설정합니다.
WORKDIR /app

# 3. 프로젝트의 모든 파일을 컨테이너 내부의 /app 폴더로 복사합니다.
COPY . .

# 4. Gradle을 이용해 프로젝트를 빌드(조립)합니다.
# -x test는 "지금은 빌드할 때 테스트 코드를 돌리지 마!" 라는 뜻입니다. (테스트는 CI에서 할 거니까요)
RUN ./gradlew bootJar -x test

# ----- 여기까지가 '빌드용(조립용)' 환경이었습니다. -----
# ----- 아래부터는 실제로 '서버에서 실행될' 가벼운 환경을 새로 만듭니다. (Multi-stage build) -----

# 5. 실행 환경을 가볍게 만들기 위해 JRE(실행 전용)만 포함된 이미지를 새로 가져옵니다.
FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

# 6. 아까 위(builder)에서 조립 완성된 파일(.jar)만 쏙 빼와서 현재 환경으로 복사합니다.
# 보통 Spring Boot 빌드 결과물은 build/libs/*.jar 에 생깁니다.
COPY --from=builder /app/build/libs/*-SNAPSHOT.jar app.jar

# 7. 스프링 부트 애플리케이션이 사용할 포트(기본 8080)를 열어둡니다.
EXPOSE 8080

# 8. 컨테이너가 켜질 때 실행할 최종 명령어입니다! (java -jar app.jar)
ENTRYPOINT ["java", "-jar", "app.jar"]