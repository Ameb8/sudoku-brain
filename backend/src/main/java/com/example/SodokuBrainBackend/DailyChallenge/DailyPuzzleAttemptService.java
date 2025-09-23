package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.Utils.UsersUtils;
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

@Service
public class DailyPuzzleAttemptService {

    private final DailyPuzzleAttemptRepository dailyPuzzleAttemptRepository;
    private final PuzzleAttemptRepository puzzleAttemptRepository;

    @Autowired
    private UsersUtils usersUtils;

    public DailyPuzzleAttemptService(DailyPuzzleAttemptRepository dailyPuzzleAttemptRepository,
                                     PuzzleAttemptRepository puzzleAttemptRepository) {
        this.dailyPuzzleAttemptRepository = dailyPuzzleAttemptRepository;
        this.puzzleAttemptRepository = puzzleAttemptRepository;
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

    public DailyPuzzleAttempt getDailyPuzzleAttempt() {
        Optional<DailyPuzzleAttempt> attempt = dailyPuzzleAttemptRepository.findById(LocalDate.now());

        usersUtils.getAuthenticatedUser();


    }
}
