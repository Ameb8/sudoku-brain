package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleAttemptDTO;
import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleDTO;
import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SodokuBrainBackend.Puzzle.DTO.PuzzleDTO;
import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Puzzle.PuzzleService;

import java.time.LocalDate;
import java.util.Optional;


@Service
public class DailyPuzzleService {
    private static final int MAX_NEXT_DAYS = 365;

    @Autowired
    private PuzzleService puzzleService;

    @Autowired
    private DailyPuzzleAttemptService dailyPuzzleAttemptService;

    @Autowired
    private DailyPuzzleRepository dailyPuzzleRepository;

    public DailyPuzzleDTO toDailyPuzzleDTO(DailyPuzzle dailyPuzzle) {
        Puzzle puzzle = dailyPuzzle.getPuzzle(); // Entity
        PuzzleDTO puzzleDTO = puzzleService.toPuzzleDTO(puzzle); // Convert to DTO
        return new DailyPuzzleDTO(dailyPuzzle.getDay(), puzzleDTO);
    }

    public DailyPuzzleResponseDTO toDailyPuzzleResponseDTO(DailyPuzzle puzzle, Optional<DailyPuzzleAttempt> optAttempt) {
        // Create response DTO with DailyPuzzle DTO
        DailyPuzzleResponseDTO response = new DailyPuzzleResponseDTO();
        response.setDailyPuzzle(toDailyPuzzleDTO(puzzle));

        if(optAttempt.isPresent()) { // Set attemptDTO if present
            DailyPuzzleAttempt attempt = optAttempt.get();
            DailyPuzzleAttemptDTO attemptDTO = dailyPuzzleAttemptService.toDailyPuzzleAttemptDTO(attempt);
            response.setDailyAttempt(attemptDTO);
        }

        return response;
    }

    public Optional<DailyPuzzleResponseDTO> getDailyPuzzle() {
        // Get daily puzzle and attempt
        Optional<DailyPuzzleAttempt> optDailyAttempt = dailyPuzzleAttemptService.getDailyPuzzleAttempt();
        Optional<DailyPuzzle> optDailyPuzzle = dailyPuzzleRepository.findById(LocalDate.now());

        if(optDailyPuzzle.isEmpty()) // Daily puzzle does not exist
            return Optional.empty();

        // Return response DTO
        return Optional.of(toDailyPuzzleResponseDTO(optDailyPuzzle.get(), optDailyAttempt));
    }

    public LocalDate findNextMissingDate() {
        LocalDate date = LocalDate.now();

        for(int i = 0; i < MAX_NEXT_DAYS; i++) {
            if (!dailyPuzzleRepository.existsByDay(date))
                return date; // No daily puzzle exists for this day

            date = date.plusDays(1);
        }

        return null;
    }

    public Optional<DailyPuzzle> setDailyPuzzle(Puzzle puzzle) {
        LocalDate challengeDate = findNextMissingDate();

        if(challengeDate == null)
            return Optional.empty();

        Puzzle savedPuzzle = puzzleService.savePuzzle(puzzle); // Save puzzle to DB

        if(savedPuzzle == null) // Puzzle failed to save
            return Optional.empty();

        // Create and save daily puzzle
        DailyPuzzle dailyPuzzle = new DailyPuzzle(challengeDate, savedPuzzle);

        return Optional.of(dailyPuzzleRepository.save(dailyPuzzle));
    }
}
