package com.example.SodokuBrainBackend.PuzzleAttempt.DTO;


public class Move {
    private int cell;     // 0 - 80
    private int value;    // 1 - 9 (or 0 if clearing a cell)
    private int hints;

    public Move() {}

    public Move(int cell, int value) {
        this.cell = cell;
        this.value = value;
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
}
