package com.example.WebQuizEngine.user.service;

import com.example.WebQuizEngine.user.exception.InvalidUserNameOrPasswordException;
import com.example.WebQuizEngine.user.exception.errorMessage.ErrorMessages;
import com.example.WebQuizEngine.user.models.UserEntity;
import com.example.WebQuizEngine.user.models.UserPrincipal;
import com.example.WebQuizEngine.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userEntity = userRepository.findByNameQueryNative(username);
        if (userEntity == null) {
            throw new InvalidUserNameOrPasswordException(ErrorMessages.INVALID_USERNAME_PWD_ERROR_MESSAGE);
        }
        return new UserPrincipal(userEntity);
    }
}
