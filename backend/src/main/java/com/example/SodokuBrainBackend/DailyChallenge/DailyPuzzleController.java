package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleResponseDTO;
import com.example.SodokuBrainBackend.Security.CustomOAuth2User;
import com.example.SodokuBrainBackend.Users.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Autowired
    private UsersUtils usersUtils;

    public DailyPuzzleController(DailyPuzzleService dailyPuzzleService, DailyPuzzleAttemptService dailyPuzzleAttemptService) {
        this.dailyPuzzleService = dailyPuzzleService;
        this.dailyPuzzleAttemptService = dailyPuzzleAttemptService;
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
