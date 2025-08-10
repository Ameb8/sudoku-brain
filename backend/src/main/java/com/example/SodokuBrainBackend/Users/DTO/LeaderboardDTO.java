package com.example.SodokuBrainBackend.Users.DTO;

public class LeaderboardDTO {
    private String username;
    private long puzzlesSolved;
    private double avgSolveTime;
    private double avgHintsUsed;

    public LeaderboardDTO(String username, long puzzlesSolved, double avgSolveTime, double avgHintsUsed) {
        this.username = username;
        this.puzzlesSolved = puzzlesSolved;
        this.avgSolveTime = avgSolveTime;
        this.avgHintsUsed = avgHintsUsed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getPuzzlesSolved() {
        return puzzlesSolved;
    }

    public void setPuzzlesSolved(long puzzlesSolved) {
        this.puzzlesSolved = puzzlesSolved;
    }

    public double getAvgSolveTime() {
        return avgSolveTime;
    }

    public void setAvgSolveTime(double avgSolveTime) {
        this.avgSolveTime = avgSolveTime;
    }

    public double getAvgHintsUsed() {
        return avgHintsUsed;
    }

    public void setAvgHintsUsed(double avgHintsUsed) {
        this.avgHintsUsed = avgHintsUsed;
    }
}
