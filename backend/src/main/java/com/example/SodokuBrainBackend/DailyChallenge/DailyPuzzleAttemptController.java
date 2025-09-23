package com.example.SodokuBrainBackend.DailyChallenge;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleAttemptUpdateRequest;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttempt;

@RestController
@RequestMapping("/api/daily-puzzle-attempt")
public class DailyPuzzleAttemptController {

    private final DailyPuzzleAttemptService dailyPuzzleAttemptService;

    public DailyPuzzleAttemptController(DailyPuzzleAttemptService dailyPuzzleAttemptService) {
        this.dailyPuzzleAttemptService = dailyPuzzleAttemptService;
    }

    @PostMapping("/update")
    public ResponseEntity<PuzzleAttempt> updateDailyPuzzleAttempt(@RequestBody DailyPuzzleAttemptUpdateRequest request) {
        PuzzleAttempt updatedAttempt = dailyPuzzleAttemptService.updateDailyPuzzleAttempt(request);
        return ResponseEntity.ok(updatedAttempt);
    }
}
