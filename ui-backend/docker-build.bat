./mvnw package
docker build -t produlytics/ui-backend .
docker run -p 8080:8080 produlytics/ui-backend