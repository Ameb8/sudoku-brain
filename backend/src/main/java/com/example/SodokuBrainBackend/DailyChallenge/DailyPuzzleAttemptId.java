package com.example.SodokuBrainBackend.DailyChallenge;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.example.SodokuBrainBackend.Users.Users;
import org.apache.catalina.User;


@Embeddable
public class DailyPuzzleAttemptId implements Serializable {

    private LocalDate day;
    private Long userId;

    public DailyPuzzleAttemptId() {}

    public DailyPuzzleAttemptId(LocalDate day, Long userId) {
        this.day = day;
        this.userId = userId;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (!(o instanceof DailyPuzzleAttemptId))
            return false;

        DailyPuzzleAttemptId that = (DailyPuzzleAttemptId) o;

        return Objects.equals(day, that.day) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, userId);
    }
}