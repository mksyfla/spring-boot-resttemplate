package com.example.client_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.client_app.entity.Login;
import com.example.client_app.entity.Register;
import com.example.client_app.service.AuthService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class AuthController {
  @Autowired
  private AuthService authService;
  
  @GetMapping("/login")
  public String loginView(Model model) {
    model.addAttribute("login", new Login());
    return "auth/login";
  }
  
  @PostMapping("/login")
  public String login(Login login) {
    this.authService.login(login);
    return "redirect:/region";
  }
  
  @GetMapping("/register")
  public String signUpView(Model model) {
    model.addAttribute("register", new Register());
    return "auth/register";
  }
  
  @PostMapping("/register")
  public String signUp(Register register) {
    this.authService.register(register);
    return "redirect:/login";
  }
}
