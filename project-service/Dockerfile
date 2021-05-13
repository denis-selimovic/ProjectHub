FROM maven:3.8-openjdk-15
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:15
WORKDIR /usr/src/app
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "target/project-service.jar"]
