package com.example.SodokuBrainBackend.Puzzle.DTO;

import java.time.LocalDate;

public class SolvedPuzzleDTO extends  PuzzleDTO{
    private int secondsToSolve, hintsUsed;
    private Byte rating;
    private LocalDate startedOn, solvedOn;

    public SolvedPuzzleDTO(Long puzzleId, String puzzleVals, String solutionVals, int secondsToSolve, int hintsUsed, Byte rating, LocalDate startedOn, LocalDate solvedOn) {
        super(puzzleId, puzzleVals, solutionVals);
        this.secondsToSolve = secondsToSolve;
        this.hintsUsed = hintsUsed;
        this.rating = rating;
        this.startedOn = startedOn;
        this.solvedOn = solvedOn;
    }

    public int getSecondsToSolve() {
        return secondsToSolve;
    }

    public void setSecondsToSolve(int secondsToSolve) {
        this.secondsToSolve = secondsToSolve;
    }

    public int getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(int hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDate getSolvedOn() {
        return solvedOn;
    }

    public void setSolvedOn(LocalDate solvedOn) {
        this.solvedOn = solvedOn;
    }
}
