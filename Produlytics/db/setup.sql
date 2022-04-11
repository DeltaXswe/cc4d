CREATE TABLE device
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    deactivated BOOLEAN NOT NULL,
    archived BOOLEAN NOT NULL,
    api_key VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE characteristic
(
    id SERIAL,
    name TEXT NOT NULL,
    archived BOOLEAN NOT NULL,
    lower_limit DOUBLE PRECISION,
    upper_limit DOUBLE PRECISION,
    auto_adjust BOOLEAN NOT NULL,
    sample_size INTEGER,
    device_id INTEGER NOT NULL REFERENCES device(id),
    PRIMARY KEY(device_id, id),
    UNIQUE(name, device_id)
);

CREATE TABLE detection
(
    creation_time TIMESTAMP,
    value DOUBLE PRECISION NOT NULL,
    outlier BOOLEAN NOT NULL,
    characteristic_id INTEGER NOT NULL,
    device_id INTEGER NOT NULL,
    PRIMARY KEY(device_id, characteristic_id, creation_time),
    FOREIGN KEY(device_id, characteristic_id) REFERENCES characteristic(device_id, id)
);

CREATE TABLE account
(
    username TEXT PRIMARY KEY,
    hashed_password TEXT NOT NULL,
    administrator BOOLEAN NOT NULL,
    archived BOOLEAN NOT NULL
);

SELECT create_hypertable('detection', 'creation_time', chunk_time_interval => 100000000000);

-- Autoincrement characteristic's id for those with the same device_id
CREATE OR REPLACE FUNCTION autofillCharacteristicId() RETURNS TRIGGER AS '
BEGIN
  SELECT INTO NEW.id COALESCE(MAX(id), 0) + 1
  FROM characteristic
  WHERE device_id = NEW.device_id;
  RETURN NEW;
END; ' LANGUAGE plpgsql;

CREATE TRIGGER "characteristicIdAutoFill"
BEFORE INSERT ON characteristic
FOR EACH ROW EXECUTE PROCEDURE autofillCharacteristicId();

-- users
CREATE USER ui WITH PASSWORD 'ui';
GRANT SELECT ON ALL TABLES IN SCHEMA PUBLIC TO ui;
GRANT INSERT, UPDATE ON TABLE device, characteristic, account TO ui;
GRANT USAGE, SELECT ON ALL sequences IN SCHEMA PUBLIC to ui;

CREATE USER api WITH PASSWORD 'api';
GRANT SELECT ON ALL TABLES IN SCHEMA PUBLIC TO api;
GRANT INSERT, UPDATE ON TABLE detection TO api;
