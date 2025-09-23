DROP PROCEDURE IF EXISTS GetAttemptedPuzzles;

CREATE PROCEDURE GetAttemptedPuzzles(IN username VARCHAR(20))
BEGIN
	SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        a.seconds_worked_on,
        a.hints_used,
        a.started_on
	FROM users u
		JOIN attempted a
			ON u.user_id = a.user_id
		JOIN puzzle p
			ON a.puzzle_id = p.puzzle_id
	WHERE u.username = username
    ;
END;