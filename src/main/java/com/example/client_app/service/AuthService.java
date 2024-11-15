package com.example.client_app.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.client_app.config.CookiesStore;
import com.example.client_app.entity.Login;
import com.example.client_app.entity.Register;
import com.example.client_app.model.LoginResponse;
import com.example.client_app.model.RegisterResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
  private RestTemplate restTemplate;
  private CookiesStore cookiesStore;

  public LoginResponse login(Login login) {
    ResponseEntity<LoginResponse> response = this.restTemplate
      .exchange(
        "http://localhost:8080/login",
        HttpMethod.POST,
        new HttpEntity<Login>(login),
        new ParameterizedTypeReference<LoginResponse>() {}
      );
    
    String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    LoginResponse loginResponse = response.getBody();

    cookiesStore.setCookie(cookie);

    return loginResponse;
  }
  
  public RegisterResponse register(Register register) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/register",
        HttpMethod.POST,
        new HttpEntity<Register>(register),
        new ParameterizedTypeReference<RegisterResponse>() {}
      ).getBody();
  }
  
  public void logout() {
    this.restTemplate
      .exchange(
        "http://localhost:8080/logout",
        HttpMethod.POST,
        new HttpEntity<>(this.CookieHeader()),
        new ParameterizedTypeReference<>() {}
      ).getBody();

    cookiesStore.setCookie("");
  }

  public HttpHeaders CookieHeader() {
    String cookie = cookiesStore.getCookie();
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.COOKIE, cookie);

    return headers;
  }
}
