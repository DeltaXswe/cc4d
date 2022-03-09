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
    lower_limit DOUBLE,
    upper_limit DOUBLE,
    average DOUBLE,
    auto_adjust BOOLEAN NOT NULL,
    sample_size INTEGER,
    device_id INTEGER NOT NULL REFERENCES device(id),
    PRIMARY KEY(device_id, id),
    UNIQUE(name, device_id)
);

CREATE TABLE detection
(
    creation_time TIMESTAMP,
    value DOUBLE NOT NULL,
    outlier BOOLEAN NOT NULL,
    characteristic_id INTEGER NOT NULL,
    device_id INTEGER NOT NULL,
    PRIMARY KEY(creation_time, device_id, characteristic_id),
    FOREIGN KEY(device_id, characteristic_id) REFERENCES characteristic(device_id, id)
);

CREATE TABLE user
(
    username TEXT PRIMARY KEY,
    hashed_password TEXT NOT NULL,
    administrator BOOLEAN NOT NULL,
    archived BOOLEAN NOT NULL
);

SELECT create_hypertable('detection', 'creation_time', chunk_time_interval => 100000);

CREATE OR REPLACE FUNCTION autofillCharacteristicId() RETURNS TRIGGER AS '
BEGIN
  SELECT INTO NEW.id COALESCE(MAX(id), 0) + 1
  FROM Characteristic
  WHERE machine_id = NEW.machine_id;
  RETURN NEW;
END; ' LANGUAGE plpgsql;

CREATE TRIGGER "characteristicIdAutoFill"
BEFORE INSERT ON characteristic
FOR EACH ROW EXECUTE PROCEDURE autofillCharacteristicId();

-- TODO: CREATE USER ...
