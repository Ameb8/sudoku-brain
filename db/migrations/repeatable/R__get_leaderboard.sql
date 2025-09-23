DROP PROCEDURE IF EXISTS GetLeaderboard;

CREATE PROCEDURE GetLeaderboard(IN page_offset INT, IN page_size INT)
BEGIN
	SELECT u.username,
		COALESCE(COUNT(s.puzzle_id), 0)			AS puzzles_solved,
        COALESCE(AVG(s.seconds_worked_on), 0) 	AS avg_solve_time,
        COALESCE(AVG(s.hints_used), 0)			AS avg_hints_used
	FROM users u
		LEFT JOIN solved s
			ON u.user_id = s.user_id
	GROUP BY u.username
    ORDER BY puzzles_solved DESC
    LIMIT page_size
    OFFSET page_offset
    ;
END;