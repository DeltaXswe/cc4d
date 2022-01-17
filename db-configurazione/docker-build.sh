docker build -t db-configurazione ./
docker run -d --name db-configurazione-container -p 5432:5432 db-configurazione