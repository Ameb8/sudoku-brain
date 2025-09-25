package com.example.SodokuBrainBackend.PuzzleAttempt;

import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.PuzzleAttemptDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Puzzle.PuzzleRepository;
import com.example.SodokuBrainBackend.Security.CustomOAuth2User;
import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Users.UsersRepository;
import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.Move;


@Service
public class PuzzleAttemptService {

    private final PuzzleAttemptRepository puzzleAttemptRepository;
    private final PuzzleRepository puzzleRepository;

    public PuzzleAttemptService(PuzzleAttemptRepository puzzleAttemptRepository, PuzzleRepository puzzleRepository) {
        this.puzzleAttemptRepository = puzzleAttemptRepository;
        this.puzzleRepository = puzzleRepository;
    }
/*
    public PuzzleAttempt applyMoves(Long puzzleAttemptId, List<Move> moves) {
        PuzzleAttempt attempt = puzzleAttemptRepository.findById(Math.toIntExact(puzzleAttemptId))
                .orElseThrow(() -> new ResourceNotFoundException("PuzzleAttempt not found"));

        attempt.applyMoves(moves);
        return puzzleAttemptRepository.save(attempt);
    }
*/
    public Optional<PuzzleAttempt> getDefaultPuzzleAttempt(long puzzleId, Users user) {
        if(user == null) // User does not exist
            return Optional.empty();

        Optional<Puzzle> optPuzzle = puzzleRepository.findById(puzzleId);

        if(optPuzzle.isEmpty()) // Puzzle does not exist
            return Optional.empty();

        Puzzle puzzle = optPuzzle.get();

        // Save default attempted object to db
        PuzzleAttempt newAttempt = initDefaultAttempt(puzzle, user);
        return Optional.of(puzzleAttemptRepository.save(newAttempt));
    }

    public PuzzleAttemptDTO toPuzzleAttemptDTO(PuzzleAttempt attempt) {
        return new PuzzleAttemptDTO(
                attempt.getCurrentState(),
                attempt.getSecondsWorkedOn(),
                attempt.getHintsUsed(),
                attempt.getStartedOn(),
                attempt.getSolvedOn()
        );
    }

    private PuzzleAttempt initDefaultAttempt(Puzzle puzzle, Users user) {
        // Set default values for new attempt
        PuzzleAttempt newAttempt = new PuzzleAttempt();
        newAttempt.setStartedOn(LocalDateTime.now());
        newAttempt.setCurrentState(puzzle.getPuzzleVals());
        newAttempt.setUser(user);

        return newAttempt;
    }
}