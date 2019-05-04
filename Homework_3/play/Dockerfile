FROM openjdk:11-jdk-slim
VOLUME /tmp
ADD target/play-0.0.1-SNAPSHOT.jar play-0.0.1-SNAPSHOT.jar
EXPOSE 8081 9001
ENTRYPOINT ["java","-jar","play-0.0.1-SNAPSHOT.jar"]
