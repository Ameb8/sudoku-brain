package com.example.SodokuBrainBackend.Puzzle.DTO;

import java.time.LocalDate;

public class UserPuzzleDTO {
    private long puzzleId;
    private String username;
    private Integer secondsWorkedOn;
    private Integer hintsUsed;
    private LocalDate startedOn;
    private LocalDate solvedOn;
    private Byte rating;
    private String currentState;
}
