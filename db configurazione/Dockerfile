FROM postgres
ENV POSTGRES_USER deltax
ENV POSTGRES_PASSWORD deltax
ENV POSTGRES_DB configurazione
COPY db_configurazione.sql popolamento_db_configurazione.sql /docker-entrypoint-initdb.d/
RUN postgres initdb
