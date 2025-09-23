DROP PROCEDURE IF EXISTS GetPuzzleMetrics;

CREATE PROCEDURE GetPuzzleMetrics(IN in_puzzle_id INT)
BEGIN
	WITH attempted_by AS (
		SELECT p.puzzle_id,
		CAST(COUNT(*) AS UNSIGNED)					AS num_attempted,
		CAST(SUM(a.seconds_worked_on) AS UNSIGNED) 	AS total_seconds,
        CAST(SUM(a.hints_used) AS UNSIGNED) 		AS total_hints_used
        FROM puzzle p
			JOIN attempted a
				ON p.puzzle_id = a.puzzle_id
		WHERE p.puzzle_id = in_puzzle_id
        GROUP BY p.puzzle_id
    ),
	solved_by AS (
		SELECT puzzle_id,
			CAST(COUNT(*) AS UNSIGNED) 				AS num_solved,
			AVG(rating) 							AS avg_rating,
            CAST(COUNT(rating) AS UNSIGNED) 		AS num_rated,
			CAST(SUM(seconds_worked_on) AS UNSIGNED) AS time_worked_on,
            AVG(seconds_worked_on)					AS avg_solve_time,
            AVG(hints_used)							AS avg_hints_used,
            CAST(SUM(hints_used) AS UNSIGNED)		AS total_hints_used
		FROM solved
        WHERE puzzle_id = in_puzzle_id
        GROUP BY puzzle_id
    ),
    record AS (
		SELECT s.puzzle_id,
			u.username,
			s.seconds_worked_on,
			s.solved_on
		FROM solved s
			JOIN users u
				ON s.user_id = u.user_id
        WHERE s.hints_used = 0 AND s.puzzle_id = in_puzzle_id
        ORDER BY s.seconds_worked_on
        LIMIT 1
    )

    SELECT COALESCE(s.num_solved, 0)										AS num_solved,
		(COALESCE(a.num_attempted, 0) + COALESCE(s.num_solved, 0))			AS num_attempted,
        COALESCE(s.avg_rating, -1)											AS avg_rating,
        COALESCE(s.num_rated, 0)											AS num_rated,
        COALESCE(s.avg_solve_time, -1)										AS avg_solve_time,
		(COALESCE(s.time_worked_on, 0) + COALESCE(a.total_seconds, 0))		AS total_seconds,
		COALESCE(s.avg_hints_used, -1)										AS avg_hints_used,
		(COALESCE(s.total_hints_used, 0) + COALESCE(a.total_hints_used, 0))	AS total_hints_used,
        r.seconds_worked_on											AS record,
		r.username		 													AS record_holder,
        r.solved_on															AS record_date

	FROM puzzle p
		LEFT JOIN attempted_by a
			ON p.puzzle_id = a.puzzle_id
		LEFT JOIN record r
			ON p.puzzle_id = r.puzzle_id
		LEFT JOIN solved_by s
			ON p.puzzle_id = s.puzzle_id
    WHERE in_puzzle_id = p.puzzle_id
    ;
END;