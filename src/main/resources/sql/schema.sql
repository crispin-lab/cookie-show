DROP TABLE IF EXISTS venues;
DROP TABLE IF EXISTS venue_row_ids;

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
