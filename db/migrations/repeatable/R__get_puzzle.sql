DROP PROCEDURE IF EXISTS GetPuzzle;

CREATE PROCEDURE GetPuzzle(IN puzzle_id INT)
BEGIN
	WITH attempted_by AS (
		SELECT COUNT(*) AS num_attempted
        FROM puzzle p
			JOIN attempted a
				ON p.puzzle_id = puzzle_id
		WHERE p.puzzle_id = puzzle_id
        GROUP BY p.puzzle_id
    )

    SELECT p.puzzle_id,
		p.puzzle_vals,
        p.solution_vals,
        a.num_attempted AS attempted_by,
        COUNT(*) AS solved_by,
        AVG(s.rating) AS avg_rating
	FROM puzzle p
		JOIN attempted_by a
			ON p.puzzle_id = a.puzzle_id
		JOIN solved s
			ON p.puzzle_id = s.puzzle_id
	WHERE p.puzzle_id = puzzle_id
    GROUP BY p.puzzle_id,
		p.puzzle_vals,
		p.solution_vals
    ;
END;