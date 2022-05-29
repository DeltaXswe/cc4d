FROM timescale/timescaledb:latest-pg14

# TODO: Queste andrebbero spostate da qualche parte (un file di configurazione forse?)
ENV POSTGRES_USER deltax
ENV POSTGRES_PASSWORD deltax

ENV POSTGRES_DB produlytics
COPY setup.sql /docker-entrypoint-initdb.d/
