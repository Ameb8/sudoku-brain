package com.example.SodokuBrainBackend.Security;

import com.example.SodokuBrainBackend.Users.Users;
import com.example.SodokuBrainBackend.Users.UsersRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UsersRepository userRepository;

    /**
     * Loads user from Google and saves to project database
     *
     * @param userRequest Authentication request
     * @return CustomOAuth2USer object
     * @throws OAuth2AuthenticationException
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String email = oauth2User.getAttribute("email");
        String authId = oauth2User.getAttribute("sub"); // Google's unique user ID
        String profilePicture = oauth2User.getAttribute("picture");
        String googleName = oauth2User.getAttribute("name"); // Name from Google profile

        // Generate a default username
        String defaultUsername = generateUniqueUsername(googleName, email);

        Optional<Users> existingUser = Optional.ofNullable(userRepository.findByEmail(email));

        Users user = existingUser.orElseGet(() -> {
            Users newUser = new Users();
            newUser.setEmail(email);
            newUser.setAuthId(authId);
            newUser.setAuthProvider("GOOGLE");
            newUser.setUsername(defaultUsername);
            newUser.setProfilePicture(profilePicture);
            newUser.setCreatedOn(LocalDateTime.now());
            return userRepository.save(newUser);
        });

        return new CustomOAuth2User(oauth2User);
    }

    /**
     * Generates initial username for new accounts based off authentication data
     * @param googleName Name associated with Google authorization
     * @param email Google's registered email name for user
     * @return Unique default username for new account
     */
    private String generateUniqueUsername(String googleName, String email) {
        String baseUsername = (googleName != null) ? googleName.replaceAll("\\s+", "").toLowerCase() :
                email.split("@")[0].toLowerCase();

        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }
}
