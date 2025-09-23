CREATE TABLE puzzle_attempt (
    id INT UNSIGNED AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    puzzle_id INT UNSIGNED NOT NULL,
    current_state CHAR(81) NOT NULL,
    seconds_worked_on INT UNSIGNED NOT NULL,
    hints_used INT UNSIGNED NOT NULL,
    started_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    solved_on DATETIME,

    PRIMARY KEY (id),

    CONSTRAINT puzzle_attempt__users
        FOREIGN KEY (user_id) 
            REFERENCES users(user_id) 
            ON DELETE CASCADE,

    CONSTRAINT puzzle_attempt__puzzle
        FOREIGN KEY (puzzle_id)
            REFERENCES puzzle(puzzle_id)
            ON DELETE CASCADE,

    INDEX users_puzzle (user_id, puzzle_id),
    INDEX puzzle_users (puzzle_id, user_id)
);