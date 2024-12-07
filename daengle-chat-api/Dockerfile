FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/daengle-chat-api-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

# .env 파일을 컨테이너로 복사
#COPY .env .env

ENV TZ=Asia/Seoul

# 프로파일 기본값 설정
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080

ENTRYPOINT ["java","-Dfile.encoding=UTF-8", "-jar","/app.jar"]