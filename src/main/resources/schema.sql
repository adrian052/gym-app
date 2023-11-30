CREATE TABLE user_gym (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(25) NOT NULL,
    last_name VARCHAR(25) NOT NULL,
    username VARCHAR(75) UNIQUE NOT NULL,
    password VARCHAR(75) NOT NULL,
    is_active BOOLEAN NOT NULL
);

CREATE TABLE training_type (
    id SERIAL PRIMARY KEY,
    training_type_name VARCHAR(75) NOT NULL
);

CREATE TABLE trainer (
    id SERIAL PRIMARY KEY,
    training_type_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (training_type_id) REFERENCES training_type(id),
    FOREIGN KEY (user_id) REFERENCES user_gym(id)
);

CREATE TABLE trainee (
    id SERIAL PRIMARY KEY,
    date_of_birth DATE,
    address VARCHAR(255),
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES user_gym(id)
);

CREATE TABLE training (
    id SERIAL PRIMARY KEY,
    trainee_id BIGINT,
    trainer_id BIGINT,
    training_name VARCHAR(75) NOT NULL,
    training_type_id BIGINT,
    training_date DATE NOT NULL,
    training_duration INT NOT NULL,
    FOREIGN KEY (trainee_id) REFERENCES trainee(id),
    FOREIGN KEY (trainer_id) REFERENCES trainer(id),
    FOREIGN KEY (training_type_id) REFERENCES training_type(id)
);