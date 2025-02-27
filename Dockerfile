# 1. OpenJDK 17을 기반 이미지로 사용
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일 복사
COPY build/libs/apiserver-0.0.1-SNAPSHOT.jar app.jar

# 4. 컨테이너에서 실행할 명령어 지정
CMD ["java", "-jar", "app.jar"]

EXPOSE 8080
