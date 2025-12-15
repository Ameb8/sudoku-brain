package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleAttemptDTO;
import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.PuzzleAttemptDTO;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttempt;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttemptService;
import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Auth.AuthService;
import com.example.SodokuBrainBackend.Users.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class DailyPuzzleAttemptService {

    @Autowired
    private DailyPuzzleAttemptRepository dailyPuzzleAttemptRepository;

    @Autowired
    private PuzzleAttemptService puzzleAttemptService;

    @Autowired
    private UsersService usersService;

    /**
     * Returns the current user's attempt for today's daily puzzle (if it exists)
     */
    public Optional<DailyPuzzleAttempt> getDailyPuzzleAttempt() {
        // Construct DailyPuzzleAttempt Primary Key
        Optional<Users> currentUser = usersService.getAuthenticatedUser();
        LocalDate today = LocalDate.now();

        if(currentUser.isEmpty()) // No authenticated user
            return Optional.empty();

        return dailyPuzzleAttemptRepository
                .findByIdDayAndIdUserId(today, currentUser.get().getUserId());
    }

    /**
     * Converts DailyPuzzleAttempt → DailyPuzzleAttemptDTO
     */
    public DailyPuzzleAttemptDTO toDailyPuzzleAttemptDTO(DailyPuzzleAttempt attempt) {
        PuzzleAttempt puzzleAttempt = attempt.getPuzzleAttempt();

        PuzzleAttemptDTO puzzleAttemptDTO =
                puzzleAttemptService.toPuzzleAttemptDTO(puzzleAttempt);

        return new DailyPuzzleAttemptDTO(puzzleAttemptDTO);
    }
}
