package com.example.SodokuBrainBackend.DailyChallenge;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface DailyPuzzleAttemptRepository extends JpaRepository<DailyPuzzleAttempt, DailyPuzzleAttemptId> { }
