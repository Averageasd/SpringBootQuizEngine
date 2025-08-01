package com.example.WebQuizEngine.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
public class ErrorMessageGeneratorUtil {

    public List<String> getBindingErrorMessages(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();
    }
}
