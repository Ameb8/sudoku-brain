package com.example.SodokuBrainBackend.DailyChallenge;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.example.SodokuBrainBackend.Users.Users;
import org.apache.catalina.User;


@Embeddable
public class DailyPuzzleAttemptId {
    private LocalDate day;
    private Users user;

    public DailyPuzzleAttemptId() {}

    public DailyPuzzleAttemptId(LocalDate day, Users user) {
        this.day = day;
        this.user = user;
    }

    // Getters and setters
    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public Users getUser() {
        return user;
    }

    public void setPuzzleAttemptId(Users user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(!(o instanceof DailyPuzzleAttemptId))
            return false;

        DailyPuzzleAttemptId that = (DailyPuzzleAttemptId) o;

        return Objects.equals(day, that.day) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, user);
    }
}
