
export default class Puzzle {
    constructor(puzzleId, puzzleVals, solutionVals, difficulty) {
        this.puzzleId = puzzleId;
        this.puzzleVals = puzzleVals;
        this.solutionVals = solutionVals;
        this.difficulty = difficulty;
    }

    isCellCorrect(index, value) {
        // Check if a given cell's value matches the solution
        return this.solutionVals.charAt(index) === value;
    }
}