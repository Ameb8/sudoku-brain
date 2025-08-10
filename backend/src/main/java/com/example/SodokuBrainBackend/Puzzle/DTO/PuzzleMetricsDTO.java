package com.example.SodokuBrainBackend.Puzzle.DTO;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;

import java.time.LocalDate;


@SqlResultSetMapping(
        name = "PuzzleMetricsMapping",
        classes = @ConstructorResult(
                targetClass =  PuzzleMetricsDTO.class,
                columns = {
                        @ColumnResult(name = "")
                }
        )
)
public class PuzzleMetricsDTO {
    private long numAttempted;
    private long numSolved;
    private double avgRating;
    private long numRated;
    private double avgSolveTime;
    private long timeWorkedOn;
    private double avgHintsUsed;
    private long totalHintsUsed;
    private int record;
    private String recordHolder;
    private LocalDate solvedOn;

    public PuzzleMetricsDTO(long numAttempted, long numSolved, double avgRating, long numRated, double avgSolveTime, long timeWorkedOn, double avgHintsUsed, long totalHintsUsed, int record, String recordHolder, LocalDate solvedOn) {
        this.numAttempted = numAttempted;
        this.numSolved = numSolved;
        this.avgRating = avgRating;
        this.numRated = numRated;
        this.avgSolveTime = avgSolveTime;
        this.timeWorkedOn = timeWorkedOn;
        this.avgHintsUsed = avgHintsUsed;
        this.totalHintsUsed = totalHintsUsed;
        this.record = record;
        this.recordHolder = recordHolder;
        this.solvedOn = solvedOn;
    }

    public long getNumAttempted() {
        return numAttempted;
    }

    public void setNumAttempted(long numAttempted) {
        this.numAttempted = numAttempted;
    }

    public long getNumSolved() {
        return numSolved;
    }

    public void setNumSolved(long numSolved) {
        this.numSolved = numSolved;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public long getNumRated() {
        return numRated;
    }

    public void setNumRated(long numRated) {
        this.numRated = numRated;
    }

    public double getAvgSolveTime() {
        return avgSolveTime;
    }

    public void setAvgSolveTime(double avgSolveTime) {
        this.avgSolveTime = avgSolveTime;
    }

    public long getTimeWorkedOn() {
        return timeWorkedOn;
    }

    public void setTimeWorkedOn(long timeWorkedOn) {
        this.timeWorkedOn = timeWorkedOn;
    }

    public double getAvgHintsUsed() {
        return avgHintsUsed;
    }

    public void setAvgHintsUsed(double avgHintsUsed) {
        this.avgHintsUsed = avgHintsUsed;
    }

    public long getTotalHintsUsed() {
        return totalHintsUsed;
    }

    public void setTotalHintsUsed(long totalHintsUsed) {
        this.totalHintsUsed = totalHintsUsed;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }

    public String getRecordHolder() {
        return recordHolder;
    }

    public void setRecordHolder(String recordHolder) {
        this.recordHolder = recordHolder;
    }

    public LocalDate getSolvedOn() {
        return solvedOn;
    }

    public void setSolvedOn(LocalDate solvedOn) {
        this.solvedOn = solvedOn;
    }
}
