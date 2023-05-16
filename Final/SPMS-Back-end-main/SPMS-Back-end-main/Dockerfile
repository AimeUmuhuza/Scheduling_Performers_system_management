FROM  FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine

WORKDIR /usr/src/app

COPY target/DukaChallenge-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9092

ENTRYPOINT ["java","-jar","app.jar"]