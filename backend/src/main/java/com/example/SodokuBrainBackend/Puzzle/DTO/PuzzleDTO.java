package com.example.SodokuBrainBackend.Puzzle.DTO;

public class PuzzleDTO {
    private Long puzzleId;
    private String puzzleVals, solutionVals;

    public PuzzleDTO(Long puzzleId, String puzzleVals, String solutionVals) {
        this.puzzleId = puzzleId;
        this.puzzleVals = puzzleVals;
        this.solutionVals = solutionVals;
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
}
