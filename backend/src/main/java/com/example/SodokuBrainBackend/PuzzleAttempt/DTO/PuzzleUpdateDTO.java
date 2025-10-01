package com.example.SodokuBrainBackend.PuzzleAttempt.DTO;

import java.util.List;

public class PuzzleUpdateDTO {
    private int puzzleAttemptId;
    private List<Move> moves;

    public PuzzleUpdateDTO(int puzzleAttemptId, List<Move> moves) {
        this.puzzleAttemptId = puzzleAttemptId;
        this.moves = moves;
    }

    public PuzzleUpdateDTO() { }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public int getPuzzleAttemptId() {
        return puzzleAttemptId;
    }

    public void setPuzzleAttemptId(int puzzleAttemptId) {
        this.puzzleAttemptId = puzzleAttemptId;
    }
}
