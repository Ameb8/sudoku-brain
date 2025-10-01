package com.example.SodokuBrainBackend.Puzzle;

import com.example.SodokuBrainBackend.Puzzle.DTO.PuzzleDTO;
import com.example.SodokuBrainBackend.Puzzle.DTO.PuzzleMetricsDTO;
import com.example.SodokuBrainBackend.Puzzle.DTO.SolvedPuzzleDTO;
import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuzzleRepository  extends JpaRepository<Puzzle, Long> {
    /**
     * Calls stored procedure to get all solved puzzle
     *
     * @param username Unique username for user
     * @return List of array containing Puzzle's info
     */
    @Procedure(name = "GetSolvedPuzzles")
    List<Object[]> GetSolvedPuzzles(@Param("username") String username);

    @Procedure(name = "GetAttemptedPuzzles")
    List<Object[]> GetAttemptedPuzzles(@Param("username") String username);

    @Procedure(name = "GetPuzzleMetrics")
    List<Object[]> GetPuzzleMetrics(@Param("puzzleId") Long puzzleId);

    @Query(value = "SELECT * FROM puzzle ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Puzzle findRandomPuzzle();

    @Query(value = "SELECT * FROM puzzle WHERE difficulty = :difficulty ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Puzzle findRandomPuzzleByDifficulty(@Param("difficulty") String difficulty);

    @Procedure(name = "GetRandSolvedPuzzle")
    List<Object[]> GetRandSolvedPuzzle();


    /*
    @Procedure(name = "GetAttemptedPuzzle")
    List<Object[]> GetAttemptedPuzzle(@Param("puzzleId") long puzzleId, @Param("username") String username);

    @Procedure(name = "GetSolvedPuzzle")
    List<Object[]> GetSolvedPuzzle(@Param("puzzleId") long puzzleId, @Param("username") String username);
*/
}
