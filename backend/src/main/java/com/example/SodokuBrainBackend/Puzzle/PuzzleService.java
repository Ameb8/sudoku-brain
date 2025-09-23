package com.example.SodokuBrainBackend.Puzzle;

import com.example.SodokuBrainBackend.Puzzle.DTO.*;
import com.example.SodokuBrainBackend.Users.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class PuzzleService {
    @Autowired
    private PuzzleRepository puzzleRepository;


    public PuzzleDTO toPuzzleDTO(Puzzle puzzle) {
        return new PuzzleDTO(puzzle.getPuzzleId(), puzzle.getPuzzleVals(), puzzle.getSolutionVals(), puzzle.getDifficulty());
    }

    //gets puzzle by ID
    public Optional<PuzzleDTO> getPuzzleById(Long id) {
        return puzzleRepository.findById(id).map(this::toPuzzleDTO);
    }

    public Puzzle savePuzzle(Puzzle puzzle) { return puzzleRepository.save(puzzle); }

    //gets a random puzzle
    public Optional<PuzzleDTO> getRandomPuzzle() {
        Puzzle randomPuzzle = puzzleRepository.findRandomPuzzle();
        if (randomPuzzle == null) return Optional.empty();
        return Optional.of(toPuzzleDTO(randomPuzzle));
    }

    //gets random puzzle with specified difficulty
    public Optional<PuzzleDTO> getRandomPuzzleByDifficulty(Difficulty difficulty) {
        Puzzle randomPuzzle = puzzleRepository.findRandomPuzzleByDifficulty(difficulty.name());
        if (randomPuzzle == null) return Optional.empty();
        return Optional.of(toPuzzleDTO(randomPuzzle));
    }


    //geta all puzzles
    public List<PuzzleDTO> getAllPuzzles() {
        return puzzleRepository.findAll()
                .stream()
                .map(this::toPuzzleDTO)
                .collect(Collectors.toList());
    }

    /*
    @Transactional
    public PuzzleStateDTO getUserPuzzle(long puzzleId, String username) {
        List<Object[]> userPuzzle = puzzleRepository.GetSolvedPuzzle(puzzleId, username);
        userPuzzle.addAll(puzzleRepository.GetSolvedPuzzle(puzzleId, username));

        return PuzzleStateDTO.getUserPuzzle(userPuzzle);

    }*/


    //gets list of solved puzzles for a user
    @Transactional
    public List<SolvedPuzzleDTO> getSolvedPuzzlesByUser(String username) {
        List<Object[]> puzzleData = puzzleRepository.GetSolvedPuzzles(username);
        List<SolvedPuzzleDTO> solvedPuzzles = new ArrayList<>();

        for (Object[] data : puzzleData) {
            Integer puzzleId = (Integer) data[0];
            String puzzleVals = (String) data[1];
            String solutionVals = (String) data[2];
            Integer secondsToSolve = (Integer) data[3];
            Integer hintsUsed = (Integer) data[4];
            Byte rating = (Byte) data[5];
            LocalDate startedOn = ((java.sql.Date) data[6]).toLocalDate();
            LocalDate solvedOn = ((java.sql.Date) data[7]).toLocalDate();

            //Integer ratingInt = Integer.valueOf(rating);
            Long puzzleIdLong = puzzleId.longValue();

            if(secondsToSolve == null)
                secondsToSolve = 0;
            if(hintsUsed == null)
                hintsUsed = 0;

            SolvedPuzzleDTO solvedPuzzle = new SolvedPuzzleDTO(puzzleIdLong, puzzleVals, solutionVals, Difficulty.MEDIUM, secondsToSolve, hintsUsed, rating, startedOn, solvedOn);
            solvedPuzzles.add(solvedPuzzle);
        }

        return solvedPuzzles;
    }

    //gets list of attempted puzzle by user
    @Transactional
    public List<AttemptedPuzzleDTO> getAttemptedPuzzlesByUser(String username) {
        List<Object[]> puzzleData = puzzleRepository.GetAttemptedPuzzles(username);
        List<AttemptedPuzzleDTO> attemptedPuzzles = new ArrayList<>();

        for(Object[] data : puzzleData) {
            Long puzzleId = Long.valueOf((Integer) data[0]);
            String puzzleVals = (String) data[1];
            String solutionVals = (String) data[2];
            Integer secondsWorkedOn = (Integer) data[3];
            Integer hintsUsed = (Integer) data[4];
            LocalDate startedOn = ((java.sql.Date) data[5]).toLocalDate();

            if(secondsWorkedOn == null)
                secondsWorkedOn = 0;
            if(hintsUsed == null)
                hintsUsed = 0;

            AttemptedPuzzleDTO attemptedPuzzle = new AttemptedPuzzleDTO(puzzleId, puzzleVals, solutionVals, Difficulty.MEDIUM, secondsWorkedOn, hintsUsed, startedOn);
            attemptedPuzzles.add(attemptedPuzzle);
        }

        return attemptedPuzzles;
    }

/*



 */
    @Transactional
    public PuzzleMetricsDTO getPuzzleMetricsDTO(Long id) {
        //convert to PuzzleMetricsDTO
        List<Object[]> metricsList = puzzleRepository.GetPuzzleMetrics(id);
        Object[] metrics = metricsList.getFirst();
        long numAttempted = ((BigDecimal) metrics[0]).longValue();
        long numSolved = ((BigDecimal) metrics[1]).longValue();
        double avgRating = ((BigDecimal) metrics[2]).doubleValue();
        long numRated = ((BigDecimal) metrics[3]).longValue();
        double avgSolveTime = ((BigDecimal) metrics[4]).doubleValue();
        long timeWorkedOn = ((BigDecimal) metrics[5]).longValue();
        double avgHintsUsed = ((BigDecimal) metrics[3]).doubleValue();
        long totalHintsUsed = ((BigDecimal) metrics[3]).longValue();
        Integer record = (Integer) metrics[8];
        String recordHolder = (String) metrics[9];
        java.sql.Date solvedOn = (java.sql.Date) metrics[10];

        //handle null values
        LocalDate recordDate = null;
        if(record == null)
            record = 0;
        if(recordHolder == null)
            recordHolder = "";
        if(solvedOn != null)
            recordDate = solvedOn.toLocalDate();

        //instantiate PuzzleMetricsDTO
        return new PuzzleMetricsDTO(
                numAttempted,
                numSolved,
                avgRating,
                numRated,
                avgSolveTime,
                timeWorkedOn,
                avgHintsUsed,
                totalHintsUsed,
                record,
                recordHolder,
                recordDate
        );
    }
}