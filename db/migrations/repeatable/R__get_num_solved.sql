DROP PROCEDURE IF EXISTS GetNumSolved;

CREATE PROCEDURE GetNumSolved(
	IN puzzle_id INT,
    OUT numSolved INT
)
BEGIN
	SELECT COUNT(*)
		INTO numSolved
	FROM puzzle p
		JOIN solved s
			ON p.puzzle_id = s.puzzle_id
	WHERE p.puzzle_id = puzzle_id
	GROUP BY p.puzzle_id;
END;