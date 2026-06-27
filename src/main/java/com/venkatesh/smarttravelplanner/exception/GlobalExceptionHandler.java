package com.venkatesh.smarttravelplanner.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {

        model.addAttribute("errorMessage",
                "City not found. Please enter a valid city.");

        return "index";
    }

}