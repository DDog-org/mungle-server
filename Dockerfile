FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/daengle-server-0.0.1-SNAPSHOT.jar

# 작업 디렉토리를 /app으로 설정
WORKDIR /app

# JAR 파일 복사
COPY ${JAR_FILE} app.jar

# .env 파일 복사
COPY .env /app/.env

ENV TZ=Asia/Seoul

EXPOSE 8080

ENTRYPOINT ["java","-Dfile.encoding=UTF-8","-jar","/app.jar"]
