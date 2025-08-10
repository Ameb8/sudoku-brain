package com.example.SodokuBrainBackend.UserPuzzles;

import com.example.SodokuBrainBackend.Users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttemptedRepository extends JpaRepository<Attempted, UserPuzzleId> {
    List<Attempted> findByUser(Users user);
}
