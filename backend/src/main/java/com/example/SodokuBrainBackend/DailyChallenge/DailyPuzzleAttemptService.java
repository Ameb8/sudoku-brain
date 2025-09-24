package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleAttemptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttempt;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttemptRepository;
import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.Move;
import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleAttemptUpdateRequest;
import com.example.SodokuBrainBackend.Utils.UsersUtils;
import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttemptService;
import com.example.SodokuBrainBackend.Utils.UsersUtils;


@Service
public class DailyPuzzleAttemptService {

    private final DailyPuzzleAttemptRepository dailyPuzzleAttemptRepository;
    private final PuzzleAttemptRepository puzzleAttemptRepository;
    private final DailyPuzzleRepository dailyPuzzleRepository;
    private final PuzzleAttemptService puzzleAttemptService;

    @Autowired
    private UsersUtils usersUtils;

    public DailyPuzzleAttemptService(DailyPuzzleAttemptRepository dailyPuzzleAttemptRepository,
                                        PuzzleAttemptRepository puzzleAttemptRepository,
                                        DailyPuzzleRepository dailyPuzzleRepository,
                                        PuzzleAttemptService puzzleAttemptService
                                    ) {
        this.dailyPuzzleAttemptRepository = dailyPuzzleAttemptRepository;
        this.puzzleAttemptRepository = puzzleAttemptRepository;
        this.dailyPuzzleRepository = dailyPuzzleRepository;
        this.puzzleAttemptService = puzzleAttemptService;
    }

    @Transactional
    public PuzzleAttempt updateDailyPuzzleAttempt(DailyPuzzleAttemptUpdateRequest request) {
        DailyPuzzleAttemptId id = new DailyPuzzleAttemptId(request.getDay(), request.getPuzzleAttemptId());

        DailyPuzzleAttempt dailyAttempt = dailyPuzzleAttemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("DailyPuzzleAttempt not found"));

        PuzzleAttempt attempt = dailyAttempt.getPuzzleAttempt();
        if (attempt == null) {
            throw new RuntimeException("PuzzleAttempt not found in DailyPuzzleAttempt");
        }

        attempt.applyMoves(request.getMoves());

        // Save the updated PuzzleAttempt entity
        return puzzleAttemptRepository.save(attempt);
    }

    public Optional<DailyPuzzleAttempt> getDailyPuzzleAttempt() {
        Optional<Users> optUser = usersUtils.getAuthenticatedUser();

        if(optUser.isEmpty()) { // User not present
            return Optional.empty();
        }

        Users user = optUser.get();

        // Get existing DailyPuzzleAttempt
        DailyPuzzleAttemptId attemptId = new DailyPuzzleAttemptId(LocalDate.now(), user);
        Optional<DailyPuzzleAttempt> optAttempt = dailyPuzzleAttemptRepository.findById(attemptId);


        if(optAttempt.isEmpty()) { // Create new DailyPuzzleAttempt
            return getDefaultDailyPuzzleAttempt(user);
        }

        return optAttempt;
    }

    public DailyPuzzleAttemptDTO toDailyPuzzleAttemptDTO(DailyPuzzleAttempt dailyAttempt) {
        PuzzleAttempt attempt = dailyAttempt.getPuzzleAttempt();
        return new DailyPuzzleAttemptDTO(puzzleAttemptService.toPuzzleAttemptDTO(attempt));
    }

    private Optional<DailyPuzzleAttempt> getDefaultDailyPuzzleAttempt(Users user) {
        Optional<DailyPuzzle> optDailyPuzzle = dailyPuzzleRepository.findById(LocalDate.now());

        if(optDailyPuzzle.isEmpty()) // Daily Puzzle doesn't exist
            return Optional.empty();

        Long dailyPuzzleId = optDailyPuzzle.get().getPuzzle().getPuzzleId(); // Get daily puzzle's ID
        Optional<PuzzleAttempt> optNewAttempt = puzzleAttemptService.getDefaultPuzzleAttempt(dailyPuzzleId, user);

        if(optNewAttempt.isEmpty()) // Puzzle attempt could not be created
            return Optional.empty();

        // create default attempt
        PuzzleAttempt newAttempt = optNewAttempt.get();
        DailyPuzzleAttempt dailyAttempt = new DailyPuzzleAttempt(optDailyPuzzle.get(), newAttempt, user);

        return Optional.of(dailyPuzzleAttemptRepository.save(dailyAttempt));
    }
}


