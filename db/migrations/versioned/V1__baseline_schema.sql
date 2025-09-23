DELIMITER ;;
CREATE PROCEDURE `GetAttemptedPuzzle`(IN in_username VARCHAR(20), in_puzzle_id INT)
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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetAttemptedPuzzles`(IN username VARCHAR(20))
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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetLeaderboard`(IN page_offset INT, IN page_size INT)
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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetNumSolved`(
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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetNumUsers`()
BEGIN
	SELECT COUNT(*) AS num_users
    FROM users
    ;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetPuzzle`(IN puzzle_id INT)
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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetPuzzleLeaders`(IN in_puzzle_id INT)
BEGIN
	SELECT username,
		seconds_to_solve,
        solved_on
	FROM solved
    WHERE hints_used = 0
    ORDER BY seconds_to_solve DESC
    LIMIT 3
    ;
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetPuzzleMetrics`(IN in_puzzle_id INT)
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
END ;;
DELIMITER ;

DELIMITER ;;
CREATE PROCEDURE `GetSolvedPuzzles`(IN username VARCHAR(20))
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
END ;;
DELIMITER ;

