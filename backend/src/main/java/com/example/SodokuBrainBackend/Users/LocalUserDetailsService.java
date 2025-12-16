package com.example.SodokuBrainBackend.Users;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public LocalUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        if (!"LOCAL".equals(user.getAuthProvider())) {
            throw new UsernameNotFoundException("Not a local user");
        }

        return new LocalUserDetails(user);
    }
}
