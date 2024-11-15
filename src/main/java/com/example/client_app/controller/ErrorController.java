package com.example.client_app.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class ErrorController {
  @ExceptionHandler(HttpClientErrorException.class)
  public String httpClientErrorException(HttpClientErrorException exception, Model model) {
    // Add error details to the model
    model.addAttribute("status", exception.getStatusCode());
    model.addAttribute("message", exception.getMessage());

    // Return the name of the HTML file (e.g., "error.html")
    return "error";
  }
}
