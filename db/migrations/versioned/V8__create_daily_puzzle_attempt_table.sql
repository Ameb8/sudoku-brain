CREATE TABLE daily_puzzle_attempt {
    day DATE NOT NULL,
    puzzle_attempt_id INT UNSIGNED NOT NULL,

    PRIMARY KEY (day, puzzle_attempt_id),

    CONSTRAINT daily_puzzle_attempt__daily_puzzle
        FOREIGN KEY (day)
            REFERENCES daily_puzzle(day)
            ON DELETE CASCADE,

    CONSTRAINT daily_puzzle_attempt__puzzle_attempt
        FOREIGN KEY (puzzle_attempt_id)
            REFERENCES puzzle_attempt(id)
            ON DELETE CASCADE,

};