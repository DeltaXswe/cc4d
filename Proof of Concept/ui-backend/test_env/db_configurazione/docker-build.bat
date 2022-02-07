docker build -t ti-db-configurazione ./
docker run -d --name ti-db-configurazione-container -p 65432:5432 ti-db-configurazione