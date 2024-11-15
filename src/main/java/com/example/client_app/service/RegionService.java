package com.example.client_app.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.client_app.config.CookiesStore;
import com.example.client_app.entity.Region;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RegionService {
  private RestTemplate restTemplate;
  private CookiesStore cookiesStore;

  public List<Region> getAll() {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/region",
        HttpMethod.GET,
        new HttpEntity<Region>(this.CookieHeader()),
        new ParameterizedTypeReference<List<Region>>() {}
      )
      .getBody();
  }
  
  public Region create(Region region) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/region",
        HttpMethod.POST,
        new HttpEntity<Region>(region, this.CookieHeader()),
        new ParameterizedTypeReference<Region>() {}
      )
      .getBody();
  }
  
  public Region delete(Integer id) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/region/"+id,
        HttpMethod.DELETE,
        new HttpEntity<Region>(this.CookieHeader()),
        new ParameterizedTypeReference<Region>() {}
      )
      .getBody();
  }

  public Region getById(Integer id) {
    return this.restTemplate
      .exchange(
        "http://localhost:8080/region/"+id,
        HttpMethod.GET,
        new HttpEntity<Region>(this.CookieHeader()),
        new ParameterizedTypeReference<Region>() {}
      )
      .getBody();
  }

  public Region update(Integer id, Region region) {
    return this.restTemplate
    .exchange(
      "http://localhost:8080/region/"+id,
      HttpMethod.PUT,
      new HttpEntity<Region>(region, this.CookieHeader()),
      new ParameterizedTypeReference<Region>() {}
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
