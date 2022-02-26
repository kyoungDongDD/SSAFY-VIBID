FROM openjdk:11-jdk
# WORKDIR /backend
# VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
CMD ["./mvnw", "clean", "package"]

ENTRYPOINT ["java", "-jar","-Duser.timezone=Asia/Seoul", "./app.jar"]