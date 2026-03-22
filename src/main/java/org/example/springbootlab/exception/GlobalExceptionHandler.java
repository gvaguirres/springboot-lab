package org.example.springbootlab.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException e, Model model) {
        log.error("Global error: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "home";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception e, Model model) {
        log.error("Unexpected error: {}", e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "home";
    }
}
