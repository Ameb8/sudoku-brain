package com.example.SodokuBrainBackend.Config;

import com.example.SodokuBrainBackend.Security.CustomOAuth2UserService;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;
import com.example.SodokuBrainBackend.Users.LocalUserDetailsService;


@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final LocalUserDetailsService localUserDetailsService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                          LocalUserDetailsService localUserDetailsService) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.localUserDetailsService = localUserDetailsService;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            LocalUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Handles login authentication for users
     * @param http Http Authentication request
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authenticationProvider(authenticationProvider(
                    localUserDetailsService, passwordEncoder()
                ))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/secured/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService)
                                )
                                .successHandler((request, response, authentication) -> {
                                    response.sendRedirect("http://localhost:5173");
                                })
                )
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .formLogin(form -> form
                    .loginProcessingUrl("/api/login")
                    .successHandler((req, res, auth) -> res.setStatus(200))
                    .failureHandler((req, res, ex) -> res.setStatus(401))
                );


        return http.build();
    }
}

