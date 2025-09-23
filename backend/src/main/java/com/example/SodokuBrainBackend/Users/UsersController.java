package com.example.SodokuBrainBackend.Users;

import com.example.SodokuBrainBackend.Users.DTO.LeaderboardDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        Users savedUser = usersService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Gets user info for logged in users
     *
     * @param principal OAuth2 user info
     * @returnUser information
     */
    @GetMapping("/secured/me")
    public ResponseEntity<Users> getLoggedInUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = principal.getAttribute("email");
        Optional<Users> userOptional = usersService.getUserByEmail(email);

        return userOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/secured/update")
    public ResponseEntity<?> updateUser(@RequestBody Users updatedUser) {
        Optional<Users> userOptional = usersService.getAuthenticatedUser();

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Users existingUser = userOptional.get();

        // Only update the fields that are present in the request
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getUsername() != null) {
            // Ensure the new username is unique
            if (usersService.isUsernameTaken(updatedUser.getUsername())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken.");
            }
            existingUser.setUsername(updatedUser.getUsername());
        }

        if (updatedUser.getProfilePicture() != null) {
            existingUser.setProfilePicture(updatedUser.getProfilePicture());
        }

        // Save the updated user details
        Users savedUser = usersService.saveUser(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Gets all users in database
     *
     * @return List of all users
     */
    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }

    /**
     * Gets user with passed username
     *
     * @param username Unique username
     * @return Users object of account with username
     *
    @GetMapping("/secured/{username}")
    public ResponseEntity<Optional<Users>> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(usersService.getUserByUsername(username));
    }*/

    /**
     * Gets user with passed email
     *
     * @param email Unique email
     * @return Users object of account with email
     */
    @GetMapping("/secured/email/{email}")
    public ResponseEntity<Users> getUserByEmail(@PathVariable String email) {
        Optional<Users> user = usersService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/secured/username/{username}")
    public ResponseEntity<?> changeUsername(@PathVariable String username) {
        return usersService.changeUsername(username)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to change username."));
    }

    /**
     * Deletes account with given username
     *
     * @param //user Unique username of account to be deleted
     * @return
     *
    @DeleteMapping("/secured/{username}")
    public ResponseEntity<Void> deleteUserByUsername(@PathVariable String username) {
        usersService.deleteUserByUsername(username);
        return ResponseEntity.noContent().build();
    }*/

    @GetMapping("/leaderboard/{pageOffset}/{pageSize}")
    public List<LeaderboardDTO> getLeaderboard(@PathVariable long pageOffset, @PathVariable int pageSize) {
        return usersService.getLeaderboard(pageOffset, pageSize);
    }

    /*
    @GetMapping("/secured/rank/{user}")
    public Long getUserRank(@PathVariable Users user) {

    }*/

    /**
     * Endpoint to retreive total number of users
     *
     * @return Number of users
     */
    @GetMapping("/numUsers")
    public long getNumUsers() {
        return usersService.getNumUsers();
    }

}