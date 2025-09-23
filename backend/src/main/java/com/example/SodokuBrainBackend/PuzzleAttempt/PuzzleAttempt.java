package com.example.SodokuBrainBackend.PuzzleAttempt;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.Move;

@Entity
public class PuzzleAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "puzzle_id", nullable = false)
    private Integer puzzleId;

    @Column(name = "current_state", nullable = false, length = 81)
    private String currentState;

    @Column(name = "seconds_worked_on", nullable = false)
    private Integer secondsWorkedOn;

    @Column(name = "hints_used", nullable = false)
    private Integer hintsUsed;

    @Column(name = "started_on", nullable = false)
    private LocalDateTime startedOn;

    @Column(name = "solved_on")
    private LocalDateTime solvedOn;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Integer puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public Integer getSecondsWorkedOn() {
        return secondsWorkedOn;
    }

    public void setSecondsWorkedOn(Integer secondsWorkedOn) {
        this.secondsWorkedOn = secondsWorkedOn;
    }

    public Integer getHintsUsed() {
        return hintsUsed;
    }

    public void setHintsUsed(Integer hintsUsed) {
        this.hintsUsed = hintsUsed;
    }

    public LocalDateTime getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDateTime startedOn) {
        this.startedOn = startedOn;
    }

    public LocalDateTime getSolvedOn() {
        return solvedOn;
    }

    public void setSolvedOn(LocalDateTime solvedOn) {
        this.solvedOn = solvedOn;
    }

    public void applyMoves(List<Move> moves) {
        if (this.currentState == null || this.currentState.length() != 81) {
            throw new IllegalStateException("Invalid current state");
        }

        char[] board = this.currentState.toCharArray();

        for (Move move : moves) {
            int cell = move.getCell();
            int value = move.getValue();

            if (cell < 0 || cell >= 81 || value < 0 || value > 9) {
                throw new IllegalArgumentException("Invalid move: " + move);
            }

            board[cell] = value == 0 ? '.' : (char) ('0' + value);
        }

        this.currentState = new String(board);

        if (isBoardSolved(board)) {
            this.solvedOn = LocalDateTime.now();
        }
    }

    private boolean isBoardSolved(char[] board) {
        for (char c : board) {
            if (c < '1' || c > '9') return false;
        }
        return true;
    }
}
