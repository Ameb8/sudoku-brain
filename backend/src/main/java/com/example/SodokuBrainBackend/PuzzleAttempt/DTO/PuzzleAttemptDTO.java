package com.example.SodokuBrainBackend.PuzzleAttempt.DTO;

import java.time.LocalDateTime;


public class PuzzleAttemptDTO {
    private String currentState;
    private Integer secondsWorkedOn;
    private Integer hintsUsed;
    private LocalDateTime startedOn;
    private LocalDateTime solvedOn;

    public PuzzleAttemptDTO(
                                String currentState,
                                Integer secondsWorkedOn,
                                Integer hintsUsed,
                                LocalDateTime startedOn,
                                LocalDateTime solvedOn
                            ) {
        this.currentState = currentState;
        this.secondsWorkedOn = secondsWorkedOn;
        this.hintsUsed = hintsUsed;
        this.startedOn = startedOn;
        this.solvedOn = solvedOn;
    }

    public PuzzleAttemptDTO() {}

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
}
