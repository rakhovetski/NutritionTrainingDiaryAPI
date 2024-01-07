CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY NOT NULL,
    username VARCHAR(128) NOT NULL UNIQUE,
    hashed_password VARCHAR(256) NOT NULL,
    height DECIMAL(5, 2) NOT NULL,
    weight DECIMAL(5, 2) NOT NULL,
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMPTZ DEFAULT now() NOT NULL
);

CREATE TABLE IF NOT EXISTS body_stat_record
(
    id SERIAL PRIMARY KEY,
    actual_weight DECIMAL(5, 2) NOT NULL,
    record_date TIMESTAMPTZ NOT NULL,
    user_id SERIAL NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS muscle_stat_record
(
    id SERIAL PRIMARY KEY NOT NULL,
    muscle_girth VARCHAR(32) NOT NULL,
    girth_in_cm DECIMAL(5, 2) NOT NULL,
    body_stat_record_id SERIAL NOT NULL REFERENCES body_stat_record(id)
);

CREATE TABLE IF NOT EXISTS exercise_record
(
    id SERIAL PRIMARY KEY NOT NULL,
    exercise_name VARCHAR(128) NOT NULL,
    muscle_exercise VARCHAR(32) NOT NULL,
    record_date TIMESTAMPTZ NOT NULL,
    user_id SERIAL NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS approach
(
    id SERIAL PRIMARY KEY NOT NULL,
    number_of_repetitions INTEGER NOT NULL,
    extra_weight DECIMAL(5, 2) NOT NULL,
    exercise_record_id SERIAL NOT NULL REFERENCES exercise_record(id)
);

CREATE TABLE IF NOT EXISTS food_record
(
    id SERIAL PRIMARY KEY NOT NULL,
    record_date TIMESTAMPTZ NOT NULL,
    user_id SERIAL NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS food
(
    id INTEGER PRIMARY KEY NOT NULL,
    name VARCHAR(64) NOT NULL,
    calories_100_gramm DECIMAL(5, 2) NOT NULL,
    protein DECIMAL(5, 2) NOT NULL,
    carbohydrates DECIMAL(5, 2) NOT NULL,
    fats DECIMAL(5, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS food_record_food
(
    id SERIAL PRIMARY KEY NOT NULL,
    food_id INTEGER NOT NULL REFERENCES food(id),
    food_record_id SERIAL NOT NULL REFERENCES food_record(id),
    amount_100_gramm INTEGER NOT NULL,
    UNIQUE (food_id, food_record_id)
);