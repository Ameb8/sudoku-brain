package com.example.SodokuBrainBackend.PuzzleAttempt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;

import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Puzzle.PuzzleRepository;
import com.example.SodokuBrainBackend.Security.CustomOAuth2User;
import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Users.UsersRepository;
import com.example.SodokuBrainBackend.PuzzleAttempt.DTO.Move;


@Service
public class PuzzleAttemptService {

    private final PuzzleAttemptRepository puzzleAttemptRepository;

    public PuzzleAttemptService(PuzzleAttemptRepository puzzleAttemptRepository) {
        this.puzzleAttemptRepository = puzzleAttemptRepository;
    }

    public PuzzleAttempt applyMoves(Long puzzleAttemptId, List<Move> moves) {
        PuzzleAttempt attempt = puzzleAttemptRepository.findById(Math.toIntExact(puzzleAttemptId))
                .orElseThrow(() -> new ResourceNotFoundException("PuzzleAttempt not found"));

        attempt.applyMoves(moves);
        return puzzleAttemptRepository.save(attempt);
    }
}