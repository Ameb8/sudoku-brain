package com.example.SodokuBrainBackend.PuzzleAttempt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttemptService;
import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.PuzzleUpdateDTO;

import java.util.Optional;


@RestController
@RequestMapping("/puzzle-attempt")
public class PuzzleAttemptController {

    private final PuzzleAttemptService puzzleAttemptService;

    public PuzzleAttemptController(PuzzleAttemptService puzzleAttemptService) {
        this.puzzleAttemptService = puzzleAttemptService;
    }

    @PostMapping("/apply-moves")
    public ResponseEntity<?> applyMoves(@RequestBody PuzzleUpdateDTO puzzleUpdate) {
        Optional<PuzzleAttempt> result = puzzleAttemptService.applyMoves(puzzleUpdate);

        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid puzzle attempt ID or invalid move data");
        }

        return ResponseEntity.ok(result.get());
    }
}

