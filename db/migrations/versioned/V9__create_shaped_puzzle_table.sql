CREATE TABLE shaped_puzzle (
    puzzle_id INT UNSIGNED NOT NULL,
    shape_name VARCHAR(45),

    PRIMARY KEY (puzzle_id),

    CONSTRAINT shaped_puzzle__puzzle
        FOREIGN KEY (puzzle_id)
            REFERENCES puzzle(puzzle_id)
            ON DELETE CASCADE
);

