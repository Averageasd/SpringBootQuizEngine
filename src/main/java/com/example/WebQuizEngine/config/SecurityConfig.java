package com.example.WebQuizEngine.config;

import com.example.WebQuizEngine.user.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JWTFilter jwtFilter;

    public SecurityConfig(UserDetailServiceImpl userDetailService, JWTFilter jwtFilter) {
        this.userDetailsService = userDetailService;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // disable csrf for local development
        return httpSecurity
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request ->
                        request
                                // don't force auth on these 2 routes
                                .requestMatchers("api/user/login", "api/user/register")
                                .permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> Customizer.withDefaults())
                // filter jwt before filtering username and password
                // do not apply jwtFilters to login and register routes
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // how we want to handle authentication in our app
    // authentication provider authenticates user using data from our database
    // perform auth logic
    @Bean
    public AuthenticationProvider authenticationProvider() {

        //  UsernamePasswordAuthenticationFilter intercepts the request and calls this DaoProvider
        // to authenticate using name and password
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // no password encoder for now
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));

        // UserDetailService supports loading user data from our database using username
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    // after receiving the authentication object, it will talk to authentication provider
    // auth provider will handle auth logic. it will check user by username. then it will check the password
    // against stored hashed password. then it will validate jwt/oauth2 if configured
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}