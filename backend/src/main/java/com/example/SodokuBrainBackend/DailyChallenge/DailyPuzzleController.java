package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleResponseDTO;
import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Puzzle.PuzzleService;
import com.example.SodokuBrainBackend.Security.CustomOAuth2User;
import com.example.SodokuBrainBackend.Users.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleAttemptUpdateRequest;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttempt;
import com.example.SodokuBrainBackend.Utils.UsersUtils;

import java.time.LocalDate;
import java.util.Optional;


@RestController
@RequestMapping("/daily-challenge")
public class DailyPuzzleController {

    private final DailyPuzzleService dailyPuzzleService;
    private final DailyPuzzleAttemptService dailyPuzzleAttemptService;
    private final PuzzleService puzzleService;

    @Autowired
    private UsersUtils usersUtils;

    @Value("${api.key}")
    private String apiKey;

    public DailyPuzzleController(DailyPuzzleService dailyPuzzleService, DailyPuzzleAttemptService dailyPuzzleAttemptService, PuzzleService puzzleService) {
        this.dailyPuzzleService = dailyPuzzleService;
        this.dailyPuzzleAttemptService = dailyPuzzleAttemptService;
        this.puzzleService = puzzleService;
    }

    /**
     * POST method for uploading daily puzzles to database
     *
     * @param providedKey API key required to upload
     * @param puzzle to be uploaded
     * @return response confirming success or failure
     */
    @PostMapping
    public ResponseEntity<?> createDailyPuzzle(
            @RequestHeader(value = "X-API-KEY", required = false) String providedKey,
            @RequestBody Puzzle puzzle)
        {

        if (providedKey == null || !providedKey.equals(apiKey)) // Validate api key
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or missing API key");

        Optional<DailyPuzzle> savedDailyPuzzle = dailyPuzzleService.setDailyPuzzle(puzzle);
        return ResponseEntity.ok(savedDailyPuzzle);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }


    /*
    @PostMapping("/update")
    public ResponseEntity<PuzzleAttempt> updateDailyPuzzleAttempt(@RequestBody DailyPuzzleAttemptUpdateRequest request) {
        PuzzleAttempt updatedAttempt = dailyPuzzleAttemptService.updateDailyPuzzleAttempt(request);
        return ResponseEntity.ok(updatedAttempt);
    }
     */

    @GetMapping("/play")
    public ResponseEntity<DailyPuzzleResponseDTO> getDailyPuzzle() {
        return dailyPuzzleService.getDailyPuzzle()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
