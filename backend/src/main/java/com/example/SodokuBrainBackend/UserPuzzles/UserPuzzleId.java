package com.example.SodokuBrainBackend.UserPuzzles;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserPuzzleId implements Serializable {
    private Long userId;
    private Long puzzleId;

    public UserPuzzleId(Long userId, Long puzzleId) {
        this.userId = userId;
        this.puzzleId = puzzleId;
    }

    public UserPuzzleId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(Long puzzleId) {
        this.puzzleId = puzzleId;
    }

    /**
     * Equals override for UserPuzzleId Objects
     * @param o Other object to compare
     * @return True if objects equal,  otherwise false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserPuzzleId that = (UserPuzzleId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(puzzleId, that.puzzleId);
    }

    /**
     * Hashes UserPuzzleId object based on username and puzzleId
     * @return Hashcode for UserPuzzleId object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userId, puzzleId);
    }
}
