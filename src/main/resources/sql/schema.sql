DROP TABLE IF EXISTS venues;
DROP TABLE IF EXISTS venue_row_ids;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS rows;
DROP TABLE IF EXISTS row_seat_ids;
DROP TABLE IF EXISTS performances;

CREATE TABLE venues
(
  id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name     VARCHAR(255) NOT NULL,
  address  VARCHAR(255) NOT NULL,
  capacity INT          NOT NULL
);

CREATE TABLE venue_row_ids
(
  venue_id BIGINT NOT NULL,
  row_id   BIGINT NOT NULL,
  FOREIGN KEY (venue_id) REFERENCES venues (id)
);

CREATE TABLE seats
(
  id     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  number INT NOT NULL
);

CREATE TABLE rows
(
  id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  label VARCHAR(255)
);

CREATE TABLE row_seat_ids
(
  row_id  BIGINT NOT NULL,
  seat_id BIGINT NOT NULL,
  FOREIGN KEY (row_id) REFERENCES rows (id)
);

CREATE TABLE performances
(
  id                     BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  title                  VARCHAR(255),
  description            VARCHAR(255),
  venue_id               BIGINT,
  start_time             TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  end_time               TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  reservation_start_time TIMESTAMP(6) WITH TIME ZONE NOT NULL,
  reservation_end_time   TIMESTAMP(6) WITH TIME ZONE NOT NULL
)
