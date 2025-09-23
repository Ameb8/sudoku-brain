package com.example.SodokuBrainBackend.DailyChallenge.DTO;

import com.example.SodokuBrainBackend.Puzzle.DTO.PuzzleDTO;

import java.time.LocalDate;

public class DailyPuzzleDTO {
    private LocalDate day;
    private PuzzleDTO puzzle;

    public DailyPuzzleDTO(LocalDate day, PuzzleDTO puzzle) {
        this.day = day;
        this.puzzle = puzzle;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public PuzzleDTO getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(PuzzleDTO puzzle) {
        this.puzzle = puzzle;
    }
}