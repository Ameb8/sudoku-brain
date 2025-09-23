package com.example.SodokuBrainBackend.DailyChallenge;

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

    private final DailyPuzzleAttemptService dailyPuzzleAttemptService;

    @Autowired
    private UsersUtils usersUtils;

    public DailyPuzzleController(DailyPuzzleAttemptService dailyPuzzleAttemptService) {
        this.dailyPuzzleAttemptService = dailyPuzzleAttemptService;
    }

    @PostMapping("/update")
    public ResponseEntity<PuzzleAttempt> updateDailyPuzzleAttempt(@RequestBody DailyPuzzleAttemptUpdateRequest request) {
        PuzzleAttempt updatedAttempt = dailyPuzzleAttemptService.updateDailyPuzzleAttempt(request);
        return ResponseEntity.ok(updatedAttempt);
    }

    @GetMapping("/api/daily-puzzle/today")
    public ResponseEntity<?> getTodayPuzzle() {
        LocalDate today = LocalDate.now();
        Optional<Users> optUser = usersUtils.getAuthenticatedUser();

        if(!optUser.isPresent()) { // User not logged in

        }

        // Not logged in? Just return the puzzle
        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.ok(Map.of("dailyPuzzle", puzzle));
        }

        // Logged in: Get or create attempt
        CustomOAuth2User user = (CustomOAuth2User) auth.getPrincipal();
        Long userId = user.getUserId();

        PuzzleAttempt attempt = dailyPuzzleAttemptService.getOrCreateAttempt(today, userId, puzzle);

        return ResponseEntity.ok(Map.of(
                "dailyPuzzle", puzzle,
                "attempt", attempt
        ));
    }
}
