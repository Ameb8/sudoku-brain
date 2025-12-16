package com.example.SodokuBrainBackend.Users;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.SodokuBrainBackend.Users.DTO.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SodokuBrainBackend.Puzzle.DTO.AttemptedPuzzleDTO;
import com.example.SodokuBrainBackend.Security.CustomOAuth2User;

import com.example.SodokuBrainBackend.Users.DTO.LeaderboardDTO;
import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Users.UsersRepository;
import org.springframework.web.bind.annotation.RequestBody;


@Service
public class UsersService {

    private UsersRepository usersRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public Users saveUser(Users user) {return usersRepository.save(user);}

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    /*public Optional<Users> getUserByUsername(String username) {
        return usersRepository.findById(username);
    }*/

    public Optional<Users> getUserByEmail(String email) {
        return Optional.ofNullable(usersRepository.findByEmail(email));
    }

    public Optional<Users> changeUsername(String username) {
        Optional<Users> optUser = getAuthenticatedUser();

        if(optUser.isEmpty())
            return Optional.empty();

        if (usersRepository.existsByUsername(username))
            return Optional.empty(); // Username already exists

        Users user = optUser.get();
        user.setUsername(username);
        usersRepository.save(user);

        return Optional.of(user);
    }

    /*public void deleteUserByUsername(String username) {
        usersRepository.deleteById(username);
    }*/

    /**
     * Gets metrics for User Leaderboard based off page size and offset
     *
     * @param pageOffset page number to be retrieved
     * @param pageSize page size to be viewed
     * @return List of LeaderboardDTO objects
     */
    @Transactional
    public List<LeaderboardDTO> getLeaderboard(long pageOffset, int pageSize) {
        List<Object[]> leaders = usersRepository.GetLeaderboard(pageOffset * pageSize, pageSize);
        List<LeaderboardDTO> leaderboard = new ArrayList<>();

        for(Object[] data : leaders) {
            String username = (String) data[0];
            long puzzlesSolved = (long) data[1];
            double avgSolveTime = ((BigDecimal) data[2]).doubleValue();
            double avgHintsUsed = ((BigDecimal) data[3]).doubleValue();
            LeaderboardDTO user = new LeaderboardDTO(username, puzzlesSolved, avgSolveTime, avgHintsUsed);
            leaderboard.add(user);
        }

        return leaderboard;
    }

    /**
     * Gets number of users
     *
     * @return Number of users
     */
    @Transactional
    public long getNumUsers() {
        List<Object[]> users = usersRepository.GetNumUsers();
        Object[] data = users.getFirst();

        return (long) data[0];
    }

    /**
     * Gets Users object of currently authenticated account
     *
     * @return Users object
     */
    public Optional<Users> getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();

        Object principal = auth.getPrincipal();

        if (principal instanceof CustomOAuth2User oauth) {
            return usersRepository.findByAuthId(oauth.getAuthId());
        }

        if (principal instanceof UserDetails userDetails) {
            return usersRepository.findByUsername(userDetails.getUsername());
        }

        return Optional.empty();
    }

    public Optional<Users> registerLocalUser(@RequestBody RegisterRequest req) {
        // Check if username is taken
        if (isUsernameTaken(req.getUsername()))
            return Optional.empty();

        Users user = new Users();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setAuthProvider("LOCAL");
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setCreatedOn(LocalDateTime.now());

        saveUser(user);
        return Optional.of(user);
    }

    public boolean isUsernameTaken(String username) {
        return usersRepository.existsByUsername(username);
    }
}