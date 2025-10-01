package com.example.SodokuBrainBackend.PuzzleAttempt.DTO;


public class Move {
    private int cell;     // 0 - 80
    private int value;    // 1 - 9 (or 0 if clearing a cell)
    private int hints;
    private int seconds;

    public Move() {}

    public Move(int cell, int value, int hints, int seconds) {
        this.cell = cell;
        this.value = value;
        this.hints = hints;
        this.seconds = seconds;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getHints() {
        return hints;
    }

    public void setHints(int hints) {
        this.hints = hints;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
