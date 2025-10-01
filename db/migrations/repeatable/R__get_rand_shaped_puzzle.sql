DROP PROCEDURE IF EXISTS GetRandShapedPuzzle;

CREATE PROCEDURE GetRandShapedPuzzle()
BEGIN
    SELECT
        p.puzzle_id,
        p.puzzle_vals,
        p.solution_vals,
        p.num_clues,
        p.difficulty,
        sp.shape_name
    FROM shaped_puzzle sp
        JOIN puzzle p 
            ON sp.puzzle_id = p.puzzle_id 
    ORDER BY RAND()
    LIMIT 1
    ;
END;