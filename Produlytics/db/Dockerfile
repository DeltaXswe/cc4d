FROM timescale/timescaledb:latest-pg12

# TODO: Queste andrebbero spostate da qualche parte (un file di configurazione forse?)
ENV POSTGRES_USER deltax
ENV POSTGRES_PASSWORD deltax

ENV POSTGRES_DB detections
COPY setup.sql /docker-entrypoint-initdb.d/
