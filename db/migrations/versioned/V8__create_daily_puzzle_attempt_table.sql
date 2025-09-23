CREATE TABLE daily_puzzle_attempt {
    day DATE NOT NULL,
    user_id BIGINT NOT NULL,
    puzzle_attempt_id INT UNSIGNED NOT NULL,

    PRIMARY KEY (day, user_id),

    CONSTRAINT daily_puzzle_attempt__daily_puzzle
        FOREIGN KEY (day)
            REFERENCES daily_puzzle(day)
            ON DELETE CASCADE,

    CONSTRAINT daily_puzzle_attempt__users
        FOREIGN KEY (user_id)
            REFERENCES users(user_id)
            ON DELETE CASCADE,

    CONSTRAINT daily_puzzle_attempt__puzzle_attempt
        FOREIGN KEY (puzzle_attempt_id)
            REFERENCES puzzle_attempt(id)
            ON DELETE CASCADE,

};