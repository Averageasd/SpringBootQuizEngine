package com.example.WebQuizEngine.domain.user.controller;

import com.example.WebQuizEngine.domain.user.dto.CreateUserDTO;
import com.example.WebQuizEngine.domain.user.dto.LoginUserDTO;
import com.example.WebQuizEngine.domain.user.service.JWTService;
import com.example.WebQuizEngine.domain.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    public UserController(
            UserService userService,
            JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody LoginUserDTO loginUserDTO) {
        userService.verifyUser(loginUserDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(jwtService.generateToken(loginUserDTO.userName()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @Valid @RequestBody CreateUserDTO createUserDTO
    ) {
        userService.register(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
