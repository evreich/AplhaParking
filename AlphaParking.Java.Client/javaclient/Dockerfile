# build
FROM maven:alpine as build
WORKDIR /main
COPY . .
RUN mvn clean package
COPY . .

# package without maven
FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8585
WORKDIR /realise
COPY --from=build /main/target/javaclient-0.0.1.war target/app.war
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","target/app.war"]
