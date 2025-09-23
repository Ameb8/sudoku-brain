package com.example.SodokuBrainBackend.DailyChallenge.DTO;

import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.Move;

import java.time.LocalDate;
import java.util.List;

public class DailyPuzzleAttemptUpdateRequest {
    private LocalDate day;
    private Integer puzzleAttemptId;
    private List<Move> moves;


    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Integer getPuzzleAttemptId() {
        return puzzleAttemptId;
    }

    public void setPuzzleAttemptId(Integer puzzleAttemptId) {
        this.puzzleAttemptId = puzzleAttemptId;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}
