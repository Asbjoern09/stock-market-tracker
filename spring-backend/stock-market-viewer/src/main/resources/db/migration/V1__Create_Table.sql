-- DROP SCHEMA IF EXISTS cryptoDB CASCADE;
-- CREATE SCHEMA cryptoDB;

CREATE TABLE trade_value (
      id SERIAL PRIMARY KEY,
      high_value DOUBLE PRECISION,
      mid_value DOUBLE PRECISION,
      low_value DOUBLE PRECISION,
      open_value DOUBLE PRECISION,
      close_value DOUBLE PRECISION
);

CREATE TABLE volume (
     id SERIAL PRIMARY KEY,
     volume DOUBLE PRECISION,
     volume_notional DOUBLE PRECISION,
     trades_done DOUBLE PRECISION
);

CREATE TABLE exchange_info (
    id SERIAL PRIMARY KEY,
    trade_values_id INT REFERENCES trade_value(id),
    volume_id INT REFERENCES volume(id),
    ticker varchar,
    trade_time TIMESTAMP
);
