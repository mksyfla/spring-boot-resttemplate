package com.example.client_app.service;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.client_app.entity.Login;
import com.example.client_app.entity.Register;
import com.example.client_app.model.LoginResponse;
import com.example.client_app.model.RegisterResponse;
import com.example.client_app.model.WebResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
  private RestTemplate restTemplate;

  public LoginResponse login(Login login) {
    ResponseEntity<WebResponse<LoginResponse>> response = this.restTemplate
      .exchange(
        "http://localhost:8080/login",
        HttpMethod.POST,
        new HttpEntity<Login>(login),
        new ParameterizedTypeReference<WebResponse<LoginResponse>>() {}
      );

    List<String> cookies = response.getHeaders().get("Set-Cookie");
    for (String i : cookies) {
      log.info(i);
    }

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
}
