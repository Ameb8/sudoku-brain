package com.example.SodokuBrainBackend.DailyChallenge.DTO;

import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.PuzzleAttemptDTO;

public class DailyPuzzleAttemptDTO {
    private PuzzleAttemptDTO puzzleAttempt;

    public DailyPuzzleAttemptDTO() {}

    public DailyPuzzleAttemptDTO(PuzzleAttemptDTO puzzleAttempt) {
        this.puzzleAttempt = puzzleAttempt;
    }

    public PuzzleAttemptDTO getPuzzleAttempt() {
        return puzzleAttempt;
    }

    public void setPuzzleAttempt(PuzzleAttemptDTO puzzleAttempt) {
        this.puzzleAttempt = puzzleAttempt;
    }
}
