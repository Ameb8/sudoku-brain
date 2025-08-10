package com.example.SodokuBrainBackend.UserPuzzles;

import com.example.SodokuBrainBackend.Puzzle.Puzzle;
import com.example.SodokuBrainBackend.Puzzle.PuzzleRepository;
import com.example.SodokuBrainBackend.Security.CustomOAuth2User;
import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Users.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserPuzzleService {
    private final AttemptedRepository attemptedRepository;
    private final SolvedRepository solvedRepository;
    private final PuzzleRepository puzzleRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public UserPuzzleService(AttemptedRepository attemptedRepository, SolvedRepository solvedRepository, PuzzleRepository puzzleRepository, UsersRepository usersRepository) {
        this.attemptedRepository = attemptedRepository;
        this.solvedRepository = solvedRepository;
        this.puzzleRepository = puzzleRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * Gets Puzzle progress from specific user
     * Creates default Attempted object if does not exist
     *
     * @param puzzleId ID of puzzle to search for
     * @return UserPuzzle object holding user progress
     */
    public UserPuzzle getUserPuzzle(long puzzleId) {
        //get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String authId = oauthUser.getAuthId();
        Optional<Users> optUser = usersRepository.findByAuthId(authId);

        if(optUser.isEmpty())
            return null;

        Users user = optUser.get();


        UserPuzzleId userPuzzleId = new UserPuzzleId(user.getUserId(), puzzleId);

        //check in Attempted table
        Optional<Attempted> attempted = attemptedRepository.findById(userPuzzleId);
        if (attempted.isPresent()) {
            System.out.println("not found in attempted"); //DEBUG
            return attempted.get();
        }
        //check in Solved table
        Optional<Solved> solved = solvedRepository.findById(userPuzzleId);
        if (solved.isPresent())
            return solved.get();
        

        //check if puzzle exists
        Optional<Puzzle> puzzle = puzzleRepository.findById(userPuzzleId.getPuzzleId());

        Attempted defaultPuzzle = new Attempted(
                userPuzzleId,
                user,
                puzzle.get(),
                puzzle.get().getPuzzleVals(),
                0,
                0,
                LocalDate.now()
        );

        attemptedRepository.save(defaultPuzzle);

        return defaultPuzzle;
    }

    /**
     * Updates User's puzzle progress
     * Retains one UserPuzzle object in database
     * @param updatePuzzle Holds updated progress
     * @return Updated UserPuzzle object
     */
    @Transactional
    public UserPuzzle updateUserPuzzle(Attempted updatePuzzle) {
        UserPuzzleId userPuzzleId = updatePuzzle.getId();
        Optional<Solved> solvedOpt = solvedRepository.findById(userPuzzleId);

        if(solvedOpt.isPresent())
            return solvedOpt.get();

        Optional<Attempted> attemptedOpt = attemptedRepository.findById(userPuzzleId);

        if(attemptedOpt.isPresent()) {
            Attempted attempt = attemptedOpt.get();
            Attempted updatedPuzzle = updatePuzzle(attempt, updatePuzzle);

            if(updatedPuzzle != null && isSolved(updatedPuzzle)) { //puzzle just solved
                Solved newUpdate = new Solved(updatedPuzzle);
                solvedRepository.save(newUpdate);
                attemptedRepository.delete(attempt);
                return newUpdate;
            } else if(updatePuzzle != null && !isSolved(updatedPuzzle)) { //not yet solved
                attemptedRepository.save(updatedPuzzle);
                return updatePuzzle;
            }
        }

        return null;
    }

    public List<Attempted> getAttemptedByUser() {
        Optional<Users> optUser = getAuthenticatedUser();

        if(!optUser.isPresent())
            return null;

        Users user = optUser.get();

        return attemptedRepository.findByUser(user);
    }

    public List<Solved> getSolvedByUser() {
        Optional<Users> optUser = getAuthenticatedUser();

        if(!optUser.isPresent())
            return null;

        Users user = optUser.get();

        return solvedRepository.findByUser(user);
    }

    /**
     * Updates passed object with new progress
     * @param userPuzzle Current database object
     * @param update Object holding new progress
     * @return  Updated Attempted object
     */
    private Attempted updatePuzzle(Attempted userPuzzle, Attempted update) {
        UserPuzzleId puzzleId = userPuzzle.getId();
        Optional<Users> user = usersRepository.findById(puzzleId.getUserId());
        Optional<Puzzle> puzzle = puzzleRepository.findById(puzzleId.getPuzzleId());

        if(user.isPresent() && puzzle.isPresent()) {
            return new Attempted( //return updated UserPuzzle
                    puzzleId,
                    user.get(),
                    puzzle.get(),
                    update.getCurrentState(),
                    userPuzzle.getSecondsWorkedOn() + update.getSecondsWorkedOn(),
                    userPuzzle.getHintsUsed() + update.getHintsUsed(),
                    userPuzzle.getStartedOn()
            );
        }

        return null; //puzzle or user doesn't exist
    }

    public Solved ratePuzzle(Long puzzleId, Byte rating) {
        Optional<Users> optUser = getAuthenticatedUser();

        if(optUser.isEmpty())
            return null;

        Users user = optUser.get();
        Long userId = user.getUserId();
        UserPuzzleId id = new UserPuzzleId(puzzleId, userId);
        Optional<Solved> optUpdate = solvedRepository.findById(id);

        if(optUpdate.isEmpty())
            return null;

        Solved update = optUpdate.get();
        update.setRating(rating);
        solvedRepository.save(update);

        return update;
    }

    /**
     * Determines if puzzle is solved
     *
     * @param updates User's updated progress
     * @return True if puzzle solved
     */
    private boolean isSolved(Attempted updates) {
        Optional<Puzzle> optPuzzle = puzzleRepository.findById(updates.getId().getPuzzleId());

        if(optPuzzle.isPresent()) {
            Puzzle puzzle = optPuzzle.get();

            return puzzle.getSolutionVals().equals(updates.getCurrentState());
        }

        return false;
    }

    /**
     * Gets Users object of currently authenticated account
     *
     * @return Users object
     */
    private Optional<Users> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String authId = oauthUser.getAuthId();
        return usersRepository.findByAuthId(authId);
    }
}
