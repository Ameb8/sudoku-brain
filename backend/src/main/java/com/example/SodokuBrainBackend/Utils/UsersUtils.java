package com.example.SodokuBrainBackend.Utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import com.example.SodokuBrainBackend.Security.CustomOAuth2User;
import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Users.UsersRepository;


public class UsersUtils {
    private final UsersRepository usersRepository;

    @Autowired
    public UsersUtils(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Gets Users object of currently authenticated account
     *
     * @return Users object
     */
    public Optional<Users> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        String authId = oauthUser.getAuthId();
        return usersRepository.findByAuthId(authId);
    }
}
