package com.example.WebQuizEngine.quiz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/quiz")
public class QuizController {

    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "hello";
    }
}

