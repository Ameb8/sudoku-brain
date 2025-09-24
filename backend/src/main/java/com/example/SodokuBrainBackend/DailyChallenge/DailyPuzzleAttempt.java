package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.PuzzleAttempt.PuzzleAttempt;
import com.example.SodokuBrainBackend.Users.Users;
import jakarta.persistence.*;

@Entity
@Table(name = "daily_puzzle_attempt")
public class DailyPuzzleAttempt {

    @EmbeddedId
    private DailyPuzzleAttemptId id;

    @MapsId("day")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day", referencedColumnName = "day", nullable = false)
    private DailyPuzzle dailyPuzzle;



    @MapsId("puzzleAttemptId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puzzle_attempt_id", referencedColumnName = "id", nullable = false)
    private PuzzleAttempt puzzleAttempt;


    public DailyPuzzleAttempt() {}

    public DailyPuzzleAttempt(DailyPuzzle dailyPuzzle, PuzzleAttempt puzzleAttempt, Users user) {
        this.id = new DailyPuzzleAttemptId(dailyPuzzle.getDay(), puzzleAttempt.getId());
        this.dailyPuzzle = dailyPuzzle;
        this.puzzleAttempt = puzzleAttempt;
    }


    public DailyPuzzleAttemptId getId() {
        return id;
    }

    public void setId(DailyPuzzleAttemptId id) {
        this.id = id;
    }

    public DailyPuzzle getDailyPuzzle() {
        return dailyPuzzle;
    }

    public void setDailyPuzzle(DailyPuzzle dailyPuzzle) {
        this.dailyPuzzle = dailyPuzzle;
    }

    public PuzzleAttempt getPuzzleAttempt() {
        return puzzleAttempt;
    }

    public void setPuzzleAttempt(PuzzleAttempt puzzleAttempt) {
        this.puzzleAttempt = puzzleAttempt;
    }
}