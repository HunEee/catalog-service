# JRE가 이미 설치되어 있는 이클립스 테무린 배포판 우분투 베이스 이미지
FROM eclipse-temurin:17
# 현재 작업 폴더를 workspace로 변경
WORKDIR workspace

# 프로젝트에서 애플리케이션 JAR 파일의 위치를 지정하는 빌드 인수
ARG JAR_FILE=build/libs/*.jar
# 애플리케이션 JAR파일을 로컬 머신에서 이미지 안으로 복사
COPY ${JAR_FILE} catalog-service.jar
# 컨테이너 실행 시 실행할 명령어
ENTRYPOINT ["java","-jar","catalog-service.jar"]