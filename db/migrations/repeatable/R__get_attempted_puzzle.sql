DROP PROCEDURE IF EXISTS GetAttemptedPuzzle;

CREATE PROCEDURE GetAttemptedPuzzle(IN in_username VARCHAR(20), in_puzzle_id INT)
BEGIN
	SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        a.current_state,
        a.username,
        a.seconds_worked_on,
        a.hints_used,
        a.started_on
	FROM attempted a
		JOIN puzzle p
			ON a.puzzle_id = p.puzzle_id
	WHERE a.username = in_username
		AND a.puzzle_id = in_puzzle_id
	;
END;