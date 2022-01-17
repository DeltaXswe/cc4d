FROM timescale/timescaledb:latest-pg12
ENV POSTGRES_USER deltax
ENV POSTGRES_PASSWORD deltax
ENV POSTGRES_DB rilevazioni
COPY db_rilevazioni.sql popolamento_db_rilevazioni.sql /docker-entrypoint-initdb.d/
