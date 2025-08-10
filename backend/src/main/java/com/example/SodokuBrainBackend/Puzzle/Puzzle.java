package com.example.SodokuBrainBackend.Puzzle;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Puzzle {
    @Id
    @Column(name = "puzzle_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long puzzleId;

    @Column(name = "puzzle_vals", length = 81, nullable = false, unique = true)
    private String puzzleVals;

    @Column(name = "solution_vals", length = 81, nullable = false)
    private String solutionVals;

    @Column(name = "num_clues", nullable = false)
    private int numClues;

    public Puzzle(Long puzzleId, String puzzleVals, String solutionVals, int numClues) {
        this.puzzleId = puzzleId;
        this.puzzleVals = puzzleVals;
        this.solutionVals = solutionVals;
        this.numClues = numClues;
    }

    public Puzzle() { }

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

    public int getNumClues() {
        return numClues;
    }

    public void setNumClues(int numClues) {
        this.numClues = numClues;
    }
}
