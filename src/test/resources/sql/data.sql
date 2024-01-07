INSERT INTO body_stat_record(id, actual_weight, record_date, user_id)
VALUES (1, 67.1, now(), 1);

INSERT INTO body_stat_record(id, actual_weight, record_date, user_id)
VALUES (2, 73.1, now(), 1);

INSERT INTO body_stat_record(id, actual_weight, record_date, user_id)
VALUES (3, 83.1, now(), 1);

INSERT INTO muscle_stat_record(id, muscle_girth, girth_in_cm, body_stat_record_id)
VALUES (1, 'TRAPEZE', 20, 1);

INSERT INTO muscle_stat_record(id, muscle_girth, girth_in_cm, body_stat_record_id)
VALUES (2, 'BICEPS', 43, 2);

INSERT INTO muscle_stat_record(id, muscle_girth, girth_in_cm, body_stat_record_id)
VALUES (3, 'TRICEPS', 43, 2);
