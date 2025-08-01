package com.example.WebQuizEngine.user.controller;

import com.example.WebQuizEngine.user.dto.CreateUserDTO;
import com.example.WebQuizEngine.user.dto.LoginUserDTO;
import com.example.WebQuizEngine.user.service.JWTService;
import com.example.WebQuizEngine.user.service.UserService;
import com.example.WebQuizEngine.util.ErrorMessageGeneratorUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private ErrorMessageGeneratorUtil errorMessageGeneratorUtil;
    private final JWTService jwtService;

    public UserController(
            UserService userService,
            ErrorMessageGeneratorUtil errorMessageGeneratorUtil,
            JWTService jwtService) {
        this.userService = userService;
        this.errorMessageGeneratorUtil = errorMessageGeneratorUtil;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Valid @RequestBody LoginUserDTO loginUserDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessageGeneratorUtil
                            .getBindingErrorMessages(bindingResult));
        }
        userService.verifyUser(loginUserDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(jwtService.generateToken(loginUserDTO.userName()));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody CreateUserDTO createUserDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorMessageGeneratorUtil
                            .getBindingErrorMessages(bindingResult));
        }
        userService.register(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
