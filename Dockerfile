# 1. JDK가 포함된 기본 이미지 사용
FROM openjdk:17-jdk-slim

# 2. JAR 파일 이름을 변수로 받아오기
ARG JAR_FILE=build/libs/*.jar

# 3. JAR 복사 → 컨테이너 내부로
COPY ${JAR_FILE} app.jar

# 4. 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java","-jar","/app.jar"]