FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/daengle-server-0.0.1-SNAPSHOT.jar

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# JAR 파일 복사
COPY ${JAR_FILE} app.jar

# .env 파일 복사
COPY .env .env

# 환경 변수 설정
ENV TZ=Asia/Seoul

EXPOSE 8080

# 애플리케이션 실행 경로 수정
ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","app.jar"]