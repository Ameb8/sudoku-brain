package com.example.SodokuBrainBackend.DailyChallenge;

import com.example.SodokuBrainBackend.DailyChallenge.DTO.DailyPuzzleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.example.SodokuBrainBackend.Puzzle.DTO.PuzzleDTO;
import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Puzzle.PuzzleService;



@Service
public class DailyPuzzleService {

    @Autowired
    private PuzzleService puzzleService;

    public DailyPuzzleDTO toDailyPuzzleDTO(DailyPuzzle dailyPuzzle) {
        Puzzle puzzle = dailyPuzzle.getPuzzle(); // Entity
        PuzzleDTO puzzleDTO = puzzleService.toPuzzleDTO(puzzle); // Convert to DTO
        return new DailyPuzzleDTO(dailyPuzzle.getDay(), puzzleDTO);
    }

    private get
}
