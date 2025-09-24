package com.example.SodokuBrainBackend.DailyChallenge.DTO;

public class DailyPuzzleResponseDTO {
    private DailyPuzzleDTO dailyPuzzle;
    private DailyPuzzleAttemptDTO dailyAttempt;

    public DailyPuzzleResponseDTO(DailyPuzzleAttemptDTO dailyAttempt, DailyPuzzleDTO dailyPuzzle) {
        this.dailyAttempt = dailyAttempt;
        this.dailyPuzzle = dailyPuzzle;
    }

    public DailyPuzzleResponseDTO() { }

    public DailyPuzzleDTO getDailyPuzzle() {
        return dailyPuzzle;
    }

    public void setDailyPuzzle(DailyPuzzleDTO dailyPuzzle) {
        this.dailyPuzzle = dailyPuzzle;
    }

    public DailyPuzzleAttemptDTO getDailyAttempt() {
        return dailyAttempt;
    }

    public void setDailyAttempt(DailyPuzzleAttemptDTO dailyAttempt) {
        this.dailyAttempt = dailyAttempt;
    }
}
