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
import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.PuzzleUpdateDTO;


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
    }*/

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

    public Optional<PuzzleAttempt> applyMoves(PuzzleUpdateDTO puzzleUpdate) {
        Optional<PuzzleAttempt> optAttempt = puzzleAttemptRepository.findById(puzzleUpdate.getPuzzleAttemptId());

        if(optAttempt.isEmpty()) // Invalid PuzzleAttempt ID
            return Optional.empty();
            //    throw new ResourceNotFoundException("Puzzle Attempt not found");

        // Get puzzle and attempt
        PuzzleAttempt attempt = optAttempt.get();
        Puzzle puzzle = attempt.getPuzzle();
        String puzzleState = attempt.getCurrentState();

        int wrongCells = cellsLeft(attempt, puzzle);
        StringBuilder newPuzzleState = new StringBuilder(attempt.getCurrentState());

        for(Move move : puzzleUpdate.getMoves()) { // Update puzzle by move
            // Validate move data
            if(move.getSeconds() < 0 || move.getHints() < 0)
                return Optional.empty();

            // Get move
            char newVal = (char) ('0' + move.getValue());
            int cell = move.getCell();
            char oldVal = newPuzzleState.charAt(cell);
            char correctVal = puzzle.getSolutionVals().charAt(cell);

            newPuzzleState.setCharAt(move.getCell(), newVal); // Update board

            // Update mistake count
            if(correctVal != newVal) {
                //attempt.incError();

                if(oldVal == correctVal)
                    wrongCells++;
            } else if(oldVal != correctVal) {
                wrongCells--;
            }

            // Update puzzle attempt
            attempt.useHints(move.getHints());
            attempt.addSeconds(move.getSeconds());

            if(wrongCells == 0) { // Puzzle solved
                attempt.setSolvedOn(LocalDateTime.now());
                break;
            }

        }

        return Optional.of(puzzleAttemptRepository.save(attempt));
    }


    private int cellsLeft(PuzzleAttempt attempt, Puzzle puzzle) {
        String solution = puzzle.getSolutionVals();
        String puzzleState = attempt.getCurrentState();
        int wrongCells = 0;

        for(int i = 0; i < solution.length(); i++) {
            if(puzzleState.charAt(i) != solution.charAt(i))
                wrongCells++;
        }

        return wrongCells;
    }

    private PuzzleAttempt initDefaultAttempt(Puzzle puzzle, Users user) {
        // Set default values for new attempt
        PuzzleAttempt newAttempt = new PuzzleAttempt();
        newAttempt.setStartedOn(LocalDateTime.now());
        newAttempt.setCurrentState(puzzle.getPuzzleVals());
        newAttempt.setUser(user);
        newAttempt.setHintsUsed(0);
        newAttempt.setSecondsWorkedOn(0);
        newAttempt.setPuzzle(puzzle);

        return newAttempt;
    }
}