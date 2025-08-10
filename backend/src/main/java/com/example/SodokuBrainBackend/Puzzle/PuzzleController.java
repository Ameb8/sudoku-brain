package com.example.SodokuBrainBackend.Puzzle;

import com.example.SodokuBrainBackend.Puzzle.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {
    @Autowired
    private PuzzleService puzzleService;

    @Value("${api.key}")
    private String apiKey;

    /**
     * POST method for uploading puzzles to database
     *
     * @param providedKey API key required to upload
     * @param puzzle to be uploaded
     * @return response confirming success or failure
     */
    @PostMapping
    public ResponseEntity<?> createPuzzle(
            @RequestHeader(value = "X-API-KEY", required = false) String providedKey,
            @RequestBody Puzzle puzzle) {

        if (providedKey == null || !providedKey.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing API key");
        }

        Puzzle savedPuzzle = puzzleService.savePuzzle(puzzle);
        return ResponseEntity.ok(savedPuzzle);
    }

    /**
     * Gets all created puzzles
     *
     * @return List of all puzzles
     */
    @GetMapping
    public List<PuzzleDTO> getAllPuzzles() {
        return puzzleService.getAllPuzzles();
    }

    /**
     * Gets puzzle with specified ID
     *
     * @param id Unique identifier of puzzle
     * @return PuzzleDTO with passed ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<PuzzleDTO> getPuzzleById(@PathVariable Long id) {
        return puzzleService.getPuzzleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Gets random puzzle
     *
     * @return Random PuzzleDTO Object
     */
    @GetMapping("/random")
    public ResponseEntity<PuzzleDTO> getRandomPuzzle() {
        return puzzleService.getRandomPuzzle()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves list of all puzzle's solved by user
     *
     * @param username User's unique username
     * @return List of PuzzleDTO object
     */
    @GetMapping("/secured/solved/{username}")
    public List<SolvedPuzzleDTO> getSolvedPuzzles(@PathVariable String username) {
        return puzzleService.getSolvedPuzzlesByUser(username);
    }

    /**
     * Retrieves all puzzle attempted by specified user
     *
     * @param username User's unique username
     * @return List of PuzzleDTO object
     */
    @GetMapping("/secured/attempted/{username}")
    public List<AttemptedPuzzleDTO> getAttemptedPuzzles(@PathVariable String username) {
        return puzzleService.getAttemptedPuzzlesByUser(username);
    }

    /**
     * Gets metrics for specified puzzle
     *
     * @param id Unique identifier for puzzle
     * @return PuzzleMetricsDTO object for puzzle
     */
    @GetMapping("/{id}/metrics")
    public ResponseEntity<PuzzleMetricsDTO> getPuzzleMetrics(@PathVariable Long id) {
        PuzzleMetricsDTO metrics = puzzleService.getPuzzleMetricsDTO(id);

        return ResponseEntity.ok(metrics);
    }
}