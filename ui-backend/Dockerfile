FROM openjdk:17-oracle

COPY persistence /persistence
WORKDIR /persistence
RUN sed -i 's/\r$//' ./mvnw
RUN chmod +x ./mvnw
RUN ./mvnw clean install

COPY ui-backend /ui-backend
WORKDIR /ui-backend
RUN sed -i 's/\r$//' ./mvnw
RUN chmod +x ./mvnw
RUN ./mvnw dependency:resolve
RUN ./mvnw package -Dmaven.test.skip

# CMD ["./mvnw", "test"]
CMD ["java", "-jar", "./target/ui-backend.jar"]
