INSERT INTO venues (name, address, capacity)
VALUES ('서울 콘서트홀', '서울시 종로구 세종대로 175', 91),
       ('부산 아트센터', '부산시 해운대구 해운대로 123', 76),
       ('대구 오페라하우스', '대구시 중구 중앙대로 100', 106),
       ('인천 문화예술회관', '인천시 남동구 예술로 50', 58);

INSERT INTO seats (venue_id, "row", number)
SELECT 1,
       CHR(65 + ((n - 1) / 13)),
       ((n - 1) % 13) + 1
FROM generate_series(1, 91) AS s(n);

INSERT INTO seats (venue_id, "row", number)
SELECT 2,
       CHR(65 + ((n - 1) / 19)),
       ((n - 1) % 19) + 1
FROM generate_series(1, 76) AS s(n);

INSERT INTO seats (venue_id, "row", number)
SELECT 3,
       CHR(65 + ((n - 1) / 11)),
       ((n - 1) % 11) + 1
FROM generate_series(1, 106) AS s(n);

INSERT INTO seats (venue_id, "row", number)
SELECT 4,
       CHR(65 + ((n - 1) / 10)),
       ((n - 1) % 10) + 1
FROM generate_series(1, 58) AS s(n);
