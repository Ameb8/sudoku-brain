package com.example.SodokuBrainBackend.DailyChallenge;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class DailyPuzzleAttemptId {
    private LocalDate day;
    private Integer puzzleAttemptId;

    public DailyPuzzleAttemptId() {}

    public DailyPuzzleAttemptId(LocalDate day, Integer puzzleAttemptId) {
        this.day = day;
        this.puzzleAttemptId = puzzleAttemptId;
    }

    // Getters and setters
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

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(!(o instanceof DailyPuzzleAttemptId))
            return false;

        DailyPuzzleAttemptId that = (DailyPuzzleAttemptId) o;

        return Objects.equals(day, that.day) && Objects.equals(puzzleAttemptId, that.puzzleAttemptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, puzzleAttemptId);
    }
}
