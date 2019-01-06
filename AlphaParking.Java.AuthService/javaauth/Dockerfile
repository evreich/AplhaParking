# build
FROM maven:alpine as build
WORKDIR /main
COPY . .
RUN mvn clean package

# package without maven
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8181
WORKDIR /realise
COPY --from=build /main/target/javaauth-0.0.1.jar target/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/app.jar"]
