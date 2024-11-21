FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/checkkuleogi-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENV TZ Asia/Seoul

EXPOSE 8080

ENTRYPOINT ["java","-Dfile.encoding=UTF-8", "-jar","/app.jar"]