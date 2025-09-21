package com.example.SodokuBrainBackend.Puzzle.DTO;

import com.example.SodokuBrainBackend.Puzzle.Difficulty;

public class PuzzleDTO {
    private Long puzzleId;
    private String puzzleVals, solutionVals;
    private Difficulty difficulty;

    public PuzzleDTO(Long puzzleId, String puzzleVals, String solutionVals, Difficulty difficulty) {
        this.puzzleId = puzzleId;
        this.puzzleVals = puzzleVals;
        this.solutionVals = solutionVals;
        this.difficulty = difficulty;
    }

    public Long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPuzzleVals() {
        return puzzleVals;
    }

    public void setPuzzleVals(String puzzleVals) {
        this.puzzleVals = puzzleVals;
    }

    public String getSolutionVals() {
        return solutionVals;
    }

    public void setSolutionVals(String solutionVals) {
        this.solutionVals = solutionVals;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

}
