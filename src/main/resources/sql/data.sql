INSERT INTO venues (name, address, capacity)
VALUES ('서울 콘서트홀', '서울시 종로구 세종대로 175', 91),
       ('부산 아트센터', '부산시 해운대구 해운대로 123', 76),
       ('대구 오페라하우스', '대구시 중구 중앙대로 100', 106),
       ('인천 문화예술회관', '인천시 남동구 예술로 50', 58);

INSERT INTO rows (label)
VALUES ('A'),
       ('B'),
       ('C'),
       ('D'),
       ('E'),
       ('F'),
       ('G'),
       ('H'),
       ('I'),
       ('J');

INSERT INTO seats (number)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10),
       (11),
       (12),
       (13),
       (14),
       (15),
       (16),
       (17),
       (18),
       (19),
       (20);

INSERT INTO venue_row_ids (venue_id, row_id)
SELECT 1, id
FROM rows WHERE label IN ('A', 'B', 'C', 'D', 'E');

INSERT INTO venue_row_ids (venue_id, row_id)
SELECT 2, id
FROM rows WHERE label IN ('A', 'B', 'C', 'D');

INSERT INTO venue_row_ids (venue_id, row_id)
SELECT 3, id
FROM rows WHERE label IN ('A', 'B', 'C', 'D', 'E', 'F');

INSERT INTO venue_row_ids (venue_id, row_id)
SELECT 4, id
FROM rows WHERE label IN ('A', 'B', 'C');

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 1, id
FROM seats
WHERE number BETWEEN 1 AND 20;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 2, id
FROM seats
WHERE number BETWEEN 1 AND 20;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 3, id
FROM seats
WHERE number BETWEEN 1 AND 18;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 4, id
FROM seats
WHERE number BETWEEN 1 AND 18;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 5, id
FROM seats
WHERE number BETWEEN 1 AND 15;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 6, id
FROM seats
WHERE number BETWEEN 1 AND 15;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 7, id
FROM seats
WHERE number BETWEEN 1 AND 12;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 8, id
FROM seats
WHERE number BETWEEN 1 AND 12;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 9, id
FROM seats
WHERE number BETWEEN 1 AND 10;

INSERT INTO row_seat_ids (row_id, seat_id)
SELECT 10, id
FROM seats
WHERE number BETWEEN 1 AND 10;
