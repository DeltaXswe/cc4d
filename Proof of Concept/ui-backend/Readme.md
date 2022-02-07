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

## ♪ Note ♫
I seguenti package contengono classi che si preoccupano di gestire l'accesso ad una singola *risorsa* (nel senso di risorsa dato da HTTP).
1. adapters
2. configuration
3. web 
4. di simile concetto anche il package db, che però è dipendente dalla banca dati, a differenza dei primi.

Il package business è indipendente dal resto: il resto dipende dalle classi di business.
Questo package può essere considerato il nucleo dell'architettura. Le altre classi saranno il "perimetro".

A differenza del perimetro il package di business si concentra perlopiù sulle azioni eseguibili sull'applicazione.
Queste azioni possono essere categorizzate:
- azioni eseguibili sul dominio dall'esterno (in-ports / casi d'uso)
- azioni eseguibili dal dominio sullo strato di persistenza (out-ports)

I casi d'uso sono implementati tramite i Services. Un Service implementa un solo caso d'uso.

## Sistema gestione errori

Le richieste che non trovano la risorsa (id sbagliati) restituiscono 404, 
con un oggetto nella body che indica le chiavi e la risorsa cercata. 