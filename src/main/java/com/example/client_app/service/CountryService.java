package com.example.client_app.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.client_app.entity.Country;
import com.example.client_app.model.CountryResponse;
import com.example.client_app.model.WebResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CountryService {
  private RestTemplate restTemplate;

  public List<CountryResponse> getAll(String name) {
    name = (name != null) ? name : "";

    WebResponse<List<CountryResponse>> data = this.restTemplate
      .exchange(
        "http://localhost:8080/country?name=" + name,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<WebResponse<List<CountryResponse>>>() {}
      )
      .getBody();

    return data.getPayload();
  }
  
  public CountryResponse create(Country country) {
    WebResponse<CountryResponse> data = this.restTemplate
      .exchange(
        "http://localhost:8080/country",
        HttpMethod.POST,
        new HttpEntity<Country>(country),
        new ParameterizedTypeReference<WebResponse<CountryResponse>>() {}
      )
      .getBody();

    return data.getPayload();
  }

  public CountryResponse delete(Integer id) {
    WebResponse<CountryResponse> data = this.restTemplate
      .exchange(
        "http://localhost:8080/country/"+id,
        HttpMethod.DELETE,
        null,
        new ParameterizedTypeReference<WebResponse<CountryResponse>>() {}
      )
      .getBody();

    return data.getPayload();
  }

  public CountryResponse getById(Integer id) {
    WebResponse<CountryResponse> data = this.restTemplate
      .exchange(
        "http://localhost:8080/country/" + id,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<WebResponse<CountryResponse>>() {}
      )
      .getBody();

    return data.getPayload();
  }

  public CountryResponse update(Integer id, Country country) {
    log.info(country.getCode());
    log.info(country.getName());

    WebResponse<CountryResponse> data = this.restTemplate
    .exchange(
      "http://localhost:8080/country/"+id,
      HttpMethod.PUT,
      new HttpEntity<Country>(country),
      new ParameterizedTypeReference<WebResponse<CountryResponse>>() {}
    )
    .getBody();

    return data.getPayload();
  }
}
