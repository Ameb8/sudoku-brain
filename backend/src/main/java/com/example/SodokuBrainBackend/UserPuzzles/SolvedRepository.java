package com.example.SodokuBrainBackend.UserPuzzles;

import com.example.SodokuBrainBackend.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolvedRepository extends JpaRepository<Solved, UserPuzzleId> {
    List<Solved> findByUser(Users user);
}
