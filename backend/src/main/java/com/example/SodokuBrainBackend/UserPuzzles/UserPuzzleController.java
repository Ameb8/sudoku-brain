package com.example.SodokuBrainBackend.UserPuzzles;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puzzles")
public class UserPuzzleController {

    private final UserPuzzleService userPuzzleService;

    public UserPuzzleController(UserPuzzleService userPuzzleService) {
        this.userPuzzleService = userPuzzleService;
    }

    /**
     * Endpoint to retrieve user progress for specific puzzle
     *
     * @param puzzleId Id of puzzle
     * @return UserPuzzle object
     */
    @GetMapping("/secured/userpuzzle/{puzzleId}")
    public ResponseEntity<UserPuzzle> getUserPuzzle(@PathVariable long puzzleId) {
        return ResponseEntity.ok(userPuzzleService.getUserPuzzle(puzzleId));
    }

    /**
     * Updates user progress for specified puzzle
     *
     * @param updatedPuzzle Updated values for specified puzzle
     * @return Response entity
     */
    @PutMapping("/secure/update")
    public ResponseEntity<UserPuzzle> updateUserPuzzle(@RequestBody Attempted updatedPuzzle) {
        UserPuzzle result = userPuzzleService.updateUserPuzzle(updatedPuzzle);

        if (result != null)
            return ResponseEntity.ok(result);

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/secured/attempted")
    public ResponseEntity<List<Attempted>> getAttemptedPuzzles() {
        List<Attempted> attemptedPuzzles = userPuzzleService.getAttemptedByUser();
        return ResponseEntity.ok(attemptedPuzzles);
    }

    @GetMapping("/secured/solved")
    public ResponseEntity<List<Solved>> getSolvedPuzzles() {
        List<Solved> solvedPuzzles = userPuzzleService.getSolvedByUser();
        return ResponseEntity.ok(solvedPuzzles);
    }

    @PutMapping("/secured/rate/{puzzleId}/{rating}")
    public ResponseEntity<Solved> ratePuzzle(@PathVariable Long puzzleId, @PathVariable Byte rating) {
        Solved updated = userPuzzleService.ratePuzzle(puzzleId, rating);
        return ResponseEntity.ok(updated);
    }
}