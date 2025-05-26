DROP TABLE IF EXISTS venues;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS performances;

CREATE TABLE venues
(
  id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  address  VARCHAR(255) NOT NULL,
  capacity INT          NOT NULL
);

CREATE TABLE seats
(
  id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  venue_id BIGINT NOT NULL,
  "row"    VARCHAR(255),
  number   INT
);

CREATE TABLE performances
(
  id                     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title                  VARCHAR(255),
  description            VARCHAR(255),
  venue_id               BIGINT                      NOT NULL,
  start_time             TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  end_time               TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  reservation_start_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  reservation_end_time   TIMESTAMP(6) WITH TIME ZONE NOT NULL
);
