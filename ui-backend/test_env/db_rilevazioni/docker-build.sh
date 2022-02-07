docker build -t ti-db-rilevazioni ./
docker run -d --name ti-db-rilevazioni-container -p 65433:5432 ti-db-rilevazioni