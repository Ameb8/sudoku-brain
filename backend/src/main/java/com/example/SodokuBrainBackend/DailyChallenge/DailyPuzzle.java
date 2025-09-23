package com.example.SodokuBrainBackend.DailyChallenge;

import jakarta.persistence.*;

import java.time.LocalDate;

import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Puzzle.Puzzle;


public class DailyPuzzle {

    @Id
    @Column(name = "day", nullable = false)
    private LocalDate day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_id", nullable = false)
    private Puzzle puzzle;

    // Constructors
    public DailyPuzzle() {}

    public DailyPuzzle(LocalDate day, Puzzle puzzle) {
        this.day = day;
        this.puzzle = puzzle;
    }

    // Getters and setters
    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}

