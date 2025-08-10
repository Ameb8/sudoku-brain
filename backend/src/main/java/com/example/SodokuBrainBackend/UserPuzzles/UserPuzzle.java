package com.example.SodokuBrainBackend.UserPuzzles;

import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Users.Users;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public class UserPuzzle implements Serializable {
    @EmbeddedId
    private UserPuzzleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @MapsId("puzzleId")
    @JoinColumn(name = "puzzle_id")
    private Puzzle puzzle;
    @Column(name = "current_state")
    private String currentState;
    @Column(name = "seconds_worked_on")
    private Integer secondsWorkedOn;
    @Column(name = "hints_used")
    private Integer hintsUsed;
    @Column(name = "started_on")
    private LocalDate startedOn;

    public UserPuzzle(UserPuzzleId id, Users user, Puzzle puzzle, String currentState, Integer secondsWorkedOn, Integer hintsUsed, LocalDate startedOn) {
        this.id = id;
        this.user = user;
        this.puzzle = puzzle;
        this.currentState = currentState;
        this.secondsWorkedOn = secondsWorkedOn;
        this.hintsUsed = hintsUsed;
        this.startedOn = startedOn;
    }

    public UserPuzzle() {}

    public boolean isSolved() {
        String ans = puzzle.getSolutionVals();

        for(int i = 0; i < currentState.length(); i++) {
            if(currentState.charAt(i) != ans.charAt(i))
                return false;
        }

        return true;
    }

    public void updatePuzzle(UserPuzzle other) {
        this.currentState = other.currentState;
        this.secondsWorkedOn += other.secondsWorkedOn;
        this.hintsUsed += other.hintsUsed;
    }

    public UserPuzzleId getId() {
        return id;
    }

    public void setId(UserPuzzleId id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
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

    public LocalDate getStartedOn() {
        return startedOn;
    }

    public void setStartedOn(LocalDate startedOn) {
        this.startedOn = startedOn;
    }
}
