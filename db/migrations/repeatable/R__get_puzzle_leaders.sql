DROP PROCEDURE IF EXISTS GetPuzzleLeaders;

CREATE PROCEDURE GetPuzzleLeaders(IN in_puzzle_id INT)
BEGIN
	SELECT username,
		seconds_to_solve,
        solved_on
	FROM solved
    WHERE hints_used = 0
    ORDER BY seconds_to_solve DESC
    LIMIT 3
    ;
END;