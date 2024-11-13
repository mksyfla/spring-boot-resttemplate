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
import com.example.client_app.model.WebResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
  private RestTemplate restTemplate;
  private CookiesStore cookiesStore;

  public LoginResponse login(Login login) {
    ResponseEntity<WebResponse<LoginResponse>> response = this.restTemplate
      .exchange(
        "http://localhost:8080/login",
        HttpMethod.POST,
        new HttpEntity<Login>(login),
        new ParameterizedTypeReference<WebResponse<LoginResponse>>() {}
      );
    
    String cookie = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);

    cookiesStore.setCookie(cookie);

    return response.getBody().getPayload();
  }
  
  public RegisterResponse register(Register register) {
    WebResponse<RegisterResponse> data = this.restTemplate
      .exchange(
        "http://localhost:8080/register",
        HttpMethod.POST,
        new HttpEntity<Register>(register),
        new ParameterizedTypeReference<WebResponse<RegisterResponse>>() {}
      ).getBody();

    return data.getPayload();
  }
  
  public void logout() {
    this.restTemplate
      .exchange(
        "http://localhost:8080/logout",
        HttpMethod.POST,
        new HttpEntity<Register>(this.CookieHeader()),
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
