FROM maven:3.8-openjdk-15 AS maven
COPY . /usr/src/app
WORKDIR /usr/src/app
EXPOSE 8081
RUN mvn clean package -Dmaven.test.skip=true
ENTRYPOINT ["mvn", "spring-boot:run"]
