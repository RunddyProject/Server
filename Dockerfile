# ===========================
# 1) Build stage
# ===========================
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

# 캐시 효율을 위해 설정/의존 파일 먼저 복사
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
# 의존성 프리캐시 (소스 없이도 가능하면 시도, 실패해도 무시)
RUN ./gradlew --version

# 나머지 소스 복사 후 빌드
COPY . .
RUN ./gradlew clean bootJar --no-daemon

# 빌드 결과 JAR 하나로 정리(plain JAR 제외)

RUN set -euo pipefail \
 && JAR_FILE="$(find build/libs -maxdepth 1 -type f -name '*.jar' ! -name '*plain*' | head -n 1)" \
 && cp "$JAR_FILE" /app/app.jar

# ===========================
# 2) Runtime stage
# ===========================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# (헬스체크용) curl 설치 + 타임존 설정
RUN apt-get update && apt-get install -y --no-install-recommends curl tzdata \
 && ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo Asia/Seoul > /etc/timezone \
 && rm -rf /var/lib/apt/lists/*

# 앱 복사
COPY --from=build /app/app.jar /app/app.jar

# 컨테이너 기본 환경
ENV TZ=Asia/Seoul \
    JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8080

# 액추에이터 헬스가 열려 있다면 유용
# (management.endpoints.web.exposure.include=health)
HEALTHCHECK --interval=30s --timeout=5s --retries=5 \
  CMD curl -fsS http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
