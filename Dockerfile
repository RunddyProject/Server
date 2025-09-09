# --- build stage ---
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

# 캐시 최적화: 설정/의존 먼저
COPY build.gradle settings.gradle gradlew ./
COPY gradle gradle
RUN ./gradlew --version || true

# 소스 복사 후 빌드
COPY . .
RUN ./gradlew clean bootJar --no-daemon
# (디버그용) 빌드 산출물 확인 원하면 아래 주석 해제
# RUN echo "== built JARs ==" && find . -type f -path "*/build/libs/*.jar" -print

# --- runtime stage ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN apt-get update && apt-get install -y --no-install-recommends curl tzdata \
 && ln -snf /usr/share/zoneinfo/Asia/Seoul /etc/localtime && echo Asia/Seoul > /etc/timezone \
 && rm -rf /var/lib/apt/lists/*

# bootJar 산출물(app.jar) 복사
COPY --from=build /app/build/libs/app.jar /app/app.jar

ENV TZ=Asia/Seoul \
    JAVA_OPTS="-Xms256m -Xmx512m"

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=5s --retries=5 \
  CMD curl -fsS http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
