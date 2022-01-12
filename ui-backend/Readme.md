# PRODULYTICS BACKEND

## Download and install dependencies
`./mvnv dependency:resolve`

## Test
`./mvnw test`

## Build jar
`./mvnw package`

## Run
`./mvnw spring-boot:run`

## Deploy docker
Requirements
- dockerfile
- jar in `./target/`

### Build image and container
Bisognerebbe chiamarli una volta sola, devo ancora scoprire come fare deploy successivi.
1. `docker build -t produlytics/ui-backend .`
2. `docker run -p 8080:8080 produlytics/ui-backend`

## TODO
Vedere board kanban github del progetto.
