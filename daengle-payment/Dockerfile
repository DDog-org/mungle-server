FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/daengle-server-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

# .env 파일을 컨테이너로 복사   #TODO 루트 모듈 루트 경로에 있는 .env 파일을 읽어와야됨
COPY .env .env

ENV TZ=Asia/Seoul

EXPOSE 8080

ENTRYPOINT ["java","-Dfile.encoding=UTF-8", "-jar","/app.jar"]