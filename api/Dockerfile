FROM maven:3.8.4-openjdk-17 as persistence

WORKDIR /persistence
COPY persistence/pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline

COPY persistence .
RUN mvn package

FROM maven:3.8.4-openjdk-17 as api

WORKDIR /persistence
COPY --from=persistence /persistence/target/persistence.jar /persistence/pom.xml ./
RUN mvn install:install-file -Dfile=persistence.jar -DpomFile=pom.xml

WORKDIR /api
COPY api/pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline

COPY api .
WORKDIR /api
RUN mvn package -Dmaven.test.skip

# CMD ["./mvnw", "test"]
CMD ["java", "-jar", "./target/api.jar"]
