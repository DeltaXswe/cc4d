FROM maven:3.8.4-openjdk-17 as persistence

WORKDIR /persistence
COPY persistence/pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline

COPY persistence/src ./src
RUN mvn package

FROM maven:3.8.4-openjdk-17 as ui-backend

WORKDIR /persistence
COPY --from=persistence /persistence/target/persistence.jar /persistence/pom.xml ./
RUN mvn install:install-file -Dfile=persistence.jar -DpomFile=pom.xml

WORKDIR /ui-backend
COPY ui-backend/pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline

COPY ui-backend/src ./src
WORKDIR /ui-backend
RUN mvn package -Dmaven.test.skip

# CMD ["./mvnw", "test"]
CMD ["java", "-jar", "./target/ui-backend.jar"]
