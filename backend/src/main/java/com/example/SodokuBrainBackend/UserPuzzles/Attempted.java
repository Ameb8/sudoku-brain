package com.example.SodokuBrainBackend.UserPuzzles;

import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Users.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "attempted")
public class Attempted extends UserPuzzle {

    public Attempted(UserPuzzleId id, Users user, Puzzle puzzle, String currentState, Integer secondsWorkedOn, Integer hintsUsed, LocalDate startedOn) {
        super(id, user, puzzle, currentState, secondsWorkedOn, hintsUsed, startedOn);
    }

    public Attempted() {
    }
}
