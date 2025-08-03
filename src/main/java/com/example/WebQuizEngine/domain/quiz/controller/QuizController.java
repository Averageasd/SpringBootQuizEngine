package com.example.WebQuizEngine.domain.quiz.controller;

import com.example.WebQuizEngine.domain.quiz.dto.CreateQuizRequestDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @PostMapping(path = "")
    public String helloWorld(@Valid @RequestBody CreateQuizRequestDTO createQuizRequestDTO){
        return "hello";
    }
}

