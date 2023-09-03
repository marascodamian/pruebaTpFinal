FROM openjdk:11-jdk-slim
COPY "backend-0.0.1-SNAPSHOT.jar" "lapicito-backend.jar"
EXPOSE 8080
ENTRYPOINT ["java","-jar","lapicito-backend.jar"]