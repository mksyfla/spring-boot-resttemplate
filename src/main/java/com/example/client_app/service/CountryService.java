package com.example.client_app.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.client_app.config.CookiesStore;
import com.example.client_app.entity.Country;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CountryService {
  private RestTemplate restTemplate;
  private CookiesStore cookiesStore;

  public List<Country> getAll() {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/country",
        HttpMethod.GET,
        new HttpEntity<>(this.CookieHeader()),
        new ParameterizedTypeReference<List<Country>>() {}
      )
      .getBody();
  }
  
  public Country create(Country country) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/country",
        HttpMethod.POST,
        new HttpEntity<Country>(country, this.CookieHeader()),
        new ParameterizedTypeReference<Country>() {}
      )
      .getBody();
  }

  public Country delete(Integer id) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/country/"+id,
        HttpMethod.DELETE,
        new HttpEntity<>(this.CookieHeader()),
        new ParameterizedTypeReference<Country>() {}
      )
      .getBody();
  }

  public Country getById(Integer id) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/country/" + id,
        HttpMethod.GET,
        new HttpEntity<>(this.CookieHeader()),
        new ParameterizedTypeReference<Country>() {}
      )
      .getBody();
  }

  public Country update(Integer id, Country country) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/country/"+id,
        HttpMethod.PUT,
        new HttpEntity<Country>(country, this.CookieHeader()),
        new ParameterizedTypeReference<Country>() {}
      )
      .getBody();
  }

  public HttpHeaders CookieHeader() {
    String cookie = cookiesStore.getCookie();
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.COOKIE, cookie);

    return headers;
  }
}
