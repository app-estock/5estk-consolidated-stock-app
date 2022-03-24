FROM openjdk:11
WORKDIR /usr/src/app
ENV SPRING_PROFILES_ACTIVE = Dev
COPY ./build/libs/Stock-0.0.1-SNAPSHOT.jar Stock-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar", "Stock-0.0.1-SNAPSHOT.jar"]