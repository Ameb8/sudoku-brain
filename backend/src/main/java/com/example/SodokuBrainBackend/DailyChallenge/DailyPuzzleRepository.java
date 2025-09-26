package com.example.SodokuBrainBackend.DailyChallenge;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;

public interface DailyPuzzleRepository extends JpaRepository<DailyPuzzle, LocalDate> {
    boolean existsByDay(LocalDate day);
}
