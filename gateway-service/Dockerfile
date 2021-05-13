FROM maven:3.8-openjdk-11 AS maven
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN mvn clean package -Dmaven.test.skip=true

FROM openjdk:11
COPY --from=maven /usr/src/app/target /usr/src/app/target
WORKDIR /usr/src/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/gateway-service.jar"]
