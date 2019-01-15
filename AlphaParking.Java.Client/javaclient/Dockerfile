# build
FROM maven:alpine as build
VOLUME /tmp
EXPOSE 8585
WORKDIR /main
COPY . .
RUN mvn clean package
ENTRYPOINT ["mvn", "spring-boot:run"]
