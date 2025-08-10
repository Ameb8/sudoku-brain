package com.example.SodokuBrainBackend.Puzzle.DTO;

import java.time.LocalDate;

public class AttemptedPuzzleDTO extends PuzzleDTO {
    private int secondsWorkedOn, hintsUsed;
    private LocalDate startedOn;

    public AttemptedPuzzleDTO(Long puzzleId, String puzzleVals, String solutionVals, int secondsWorkedOn, int hintsUsed, LocalDate startedOn) {
        super(puzzleId, puzzleVals, solutionVals);
        this.secondsWorkedOn = secondsWorkedOn;
        this.hintsUsed = hintsUsed;
        this.startedOn = startedOn;
    }

    public int getSecondsWorkedOn() {
        return secondsWorkedOn;
    }

    public void setSecondsWorkedOn(int secondsWorkedOn) {
        this.secondsWorkedOn = secondsWorkedOn;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }
}
