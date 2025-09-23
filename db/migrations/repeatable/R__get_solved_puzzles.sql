DROP PROCEDURE IF EXISTS GetSolvedPuzzles;

CREATE PROCEDURE GetSolvedPuzzles(IN username VARCHAR(20))
BEGIN
	SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        s.seconds_to_solve,
        s.hints_used,
		s.rating,
        s.started_on,
        s.solved_on
	FROM users u
		JOIN solved s
			ON u.username = s.username
		JOIN puzzle p
			ON s.puzzle_id = p.puzzle_id
	WHERE u.username = username
    ;
END;