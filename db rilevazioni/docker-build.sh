docker build -t db-rilevazioni ./
docker run -d --name db-rilevazioni-container -p 5433:5432 db-rilevazioni