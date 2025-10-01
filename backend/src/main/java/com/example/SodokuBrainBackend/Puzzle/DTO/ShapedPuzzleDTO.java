package com.example.SodokuBrainBackend.Puzzle.DTO;

public class ShapedPuzzleDTO {
    private PuzzleDTO puzzle;
    private String shapeName;

    public ShapedPuzzleDTO(PuzzleDTO puzzle, String shapeName) {
        this.puzzle = puzzle;
        this.shapeName = shapeName;
    }

    public ShapedPuzzleDTO() {}

    public PuzzleDTO getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(PuzzleDTO puzzle) {
        this.puzzle = puzzle;
    }

    public String getShapeName() {
        return shapeName;
    }

    public void setShapeName(String shapeName) {
        this.shapeName = shapeName;
    }
}
