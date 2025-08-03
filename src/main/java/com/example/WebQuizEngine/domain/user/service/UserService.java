package com.example.WebQuizEngine.domain.user.service;

import com.example.WebQuizEngine.domain.user.dto.CreateUserDTO;
import com.example.WebQuizEngine.domain.user.dto.LoginUserDTO;
import com.example.WebQuizEngine.domain.user.exception.CredentialsAlreadyExistException;
import com.example.WebQuizEngine.domain.user.exception.InvalidUserNameOrPasswordException;
import com.example.WebQuizEngine.domain.user.exception.errorMessage.ErrorMessages;
import com.example.WebQuizEngine.domain.user.models.UserEntity;
import com.example.WebQuizEngine.domain.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        this.authenticationManager = authenticationManager;
    }

    public void register(CreateUserDTO createUserDTO) {
        UserEntity userEntity = userRepository.findByNameQueryNative(createUserDTO.userName());
        if (userEntity != null) {
            throw new CredentialsAlreadyExistException(ErrorMessages.CREDENTIALS_ALREADY_EXISTS_ERROR_MESSAGE);
        }
        String hashedPassword = bCryptPasswordEncoder.encode(createUserDTO.userPassword());
        userEntity = new UserEntity(
                createUserDTO.userName(),
                createUserDTO.userPassword(),
                hashedPassword
        );
        userRepository.save(userEntity);
    }

    public void verifyUser(LoginUserDTO loginUserDTO) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginUserDTO.userName(),
                                loginUserDTO.userPassword()
                        )
                );

        if (!authentication.isAuthenticated()) {
            throw new InvalidUserNameOrPasswordException(ErrorMessages.INVALID_USERNAME_PWD_ERROR_MESSAGE);
        }
    }
}
