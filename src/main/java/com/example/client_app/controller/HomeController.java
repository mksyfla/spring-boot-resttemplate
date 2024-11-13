package com.example.client_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.client_app.config.CookiesStore;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
  private CookiesStore cookiesStore;

  @GetMapping
  public String index(Model model) {
    model.addAttribute("name", "SIBKM");
    model.addAttribute("isAuth", this.getCookie());
    return "index";
  }

  public String getCookie() {
    return cookiesStore.getCookie();
  }
}
