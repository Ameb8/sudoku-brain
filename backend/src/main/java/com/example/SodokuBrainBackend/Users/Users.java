package com.example.SodokuBrainBackend.Users;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;

    @Column(name = "auth_provider", nullable = true)
    private String authProvider;

    @Column(name = "auth_id", nullable = true)
    private String authId;

    @Column(name = "email", unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "created_on", nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @Column(name = "password_hash", nullable = true)
    private String passwordHash;


    public Users(Long userId, String authProvider, String authId, String username, String profilePicture, LocalDateTime createdOn, String passwordHash) {
        this.userId = userId;
        this.authProvider = authProvider;
        this.authId = authId;
        this.username = username;
        this.profilePicture = profilePicture;
        this.createdOn = createdOn;
        this.passwordHash = passwordHash;
    }

    public Users() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(String authProvider) {
        this.authProvider = authProvider;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdO) {
        this.createdOn = createdO;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return userId != null && userId.equals(users.userId);
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }
}

