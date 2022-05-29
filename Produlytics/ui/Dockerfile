FROM maven:3.8.4-openjdk-17 as maven
FROM openjdk:17 as java
FROM node:16 as node

# Front-end
FROM node as front-end

WORKDIR /front-end
COPY ui/front-end/package.json ui/front-end/package-lock.json .
RUN npm ci

COPY ui/front-end/src src
COPY ui/front-end/*.json ui/front-end/*.js .
# RUN npm install
RUN npm run build --prod

# Persistence
FROM maven as persistence

WORKDIR /persistence
COPY persistence/pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline

COPY persistence/src ./src
RUN mvn package

# Back-end
FROM maven as back-end

WORKDIR /persistence
COPY --from=persistence /persistence/target/persistence.jar /persistence/pom.xml ./
RUN mvn install:install-file -Dfile=persistence.jar -DpomFile=pom.xml

WORKDIR /back-end
COPY ui/back-end/pom.xml .
RUN mvn dependency:resolve-plugins dependency:go-offline

COPY ui/back-end/src ./src
RUN mvn package -Dmaven.test.skip

# Assemblaggio
FROM java as runner

WORKDIR /app
COPY --from=front-end /front-end/dist/produlytics-frontend ./static
COPY --from=back-end back-end/target/back-end-ui.jar .
CMD ["java", "-jar", "back-end-ui.jar"]
