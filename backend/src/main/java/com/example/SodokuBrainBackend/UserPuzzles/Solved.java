package com.example.SodokuBrainBackend.UserPuzzles;

import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Users.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "solved")
public class Solved extends UserPuzzle {
    private LocalDate solvedOn;
    private Byte rating;

    public Solved(UserPuzzleId id, Users user, Puzzle puzzle, String currentState, Integer secondsWorkedOn, Integer hintsUsed, LocalDate startedOn, LocalDate solvedOn, Byte rating) {
        super(id, user, puzzle, currentState, secondsWorkedOn, hintsUsed, startedOn);
        this.solvedOn = solvedOn;
        this.rating = rating;
    }

    public Solved(Attempted other) {
        super(other.getId(), other.getUser(), other.getPuzzle(), other.getCurrentState(), other.getSecondsWorkedOn(), other.getHintsUsed(), other.getStartedOn());
        this.solvedOn = LocalDate.now();
        this.rating = null;
    }

    public Solved() {
    }

    public LocalDate getSolvedOn() {
        return solvedOn;
    }

    public void setSolvedOn(LocalDate solvedOn) {
        this.solvedOn = solvedOn;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }
}
