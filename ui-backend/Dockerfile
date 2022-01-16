FROM openjdk:17-oracle

COPY persistence /persistence
COPY ui-backend /ui-backend

WORKDIR /persistence
RUN ./mvnw clean install

WORKDIR /ui-backend
RUN ./mvnw dependency:resolve
RUN ./mvnw package -Dmaven.test.skip

# CMD ["./mvnw", "test"]
CMD ["java", "-jar", "./target/ui-backend.jar"]
