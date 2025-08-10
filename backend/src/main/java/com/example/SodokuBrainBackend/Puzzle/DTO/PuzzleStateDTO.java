package com.example.SodokuBrainBackend.Puzzle.DTO;

import java.time.LocalDate;
import java.util.List;

public class PuzzleStateDTO {
    private String currentState;
    /*private Integer secondsWorkedOn;
    private Integer hintsUsed;
    private LocalDate startedOn;
    private LocalDate solvedOn;

    private PuzzleStateDTO(String currentState, Integer secondsWorkedOn, Integer hintsUsed, LocalDate startedOn, LocalDate solvedOn) {
        this.currentState = currentState;
        this.secondsWorkedOn = secondsWorkedOn;
        this.hintsUsed = hintsUsed;
        this.startedOn = startedOn;
        this.solvedOn = solvedOn;
    }

    public static PuzzleStateDTO getUserPuzzle(Object[] values) {
        if(values == null) { //initiate new puzzle
            return null;
        }

        Object[] vals = values.getFirst();

        String currentState = (String) vals[1];
        int secondsWorkedOn = (int) vals[2];
        int hintsUsed = (int) vals[3];
        LocalDate startedOn = ((java.sql.Date) vals[4]).toLocalDate();
        LocalDate solvedOn = ((java.sql.Date) vals[5]).toLocalDate();


        return new PuzzleStateDTO(currentState, secondsWorkedOn, hintsUsed, startedOn, solvedOn);
    }

    public static PuzzleStateDTO getAttempted(List<Object[]> values) {
        Object[] vals = values.getFirst();

        String currentState = (String) vals[1];
        int secondsWorkedOn = (int) vals[2];
        int hintsUsed = (int) vals[3];
        LocalDate startedOn = ((java.sql.Date) vals[4]).toLocalDate();
        LocalDate solvedOn = ((java.sql.Date) vals[5]).toLocalDate();


        return new PuzzleStateDTO(currentState, secondsWorkedOn, hintsUsed, startedOn, solvedOn);
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public LocalDate getSolvedOn() {
        return solvedOn;
    }

    public void setSolvedOn(LocalDate solvedOn) {
        this.solvedOn = solvedOn;
    }
*/

}
