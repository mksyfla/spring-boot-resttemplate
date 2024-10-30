package com.example.client_app.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.client_app.entity.Region;
import com.example.client_app.model.WebResponse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class RegionService {
  private RestTemplate restTemplate;

  public List<Region> getAll() {
    WebResponse<List<Region>> data = this.restTemplate
      .exchange(
        "http://localhost:8080/region",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<WebResponse<List<Region>>>() {}
      )
      .getBody();

    return data.getPayload();
  }
  
  public Region create(Region region) {
    WebResponse<Region> data = this.restTemplate
      .exchange(
        "http://localhost:8080/region",
        HttpMethod.POST,
        new HttpEntity<Region>(region),
        new ParameterizedTypeReference<WebResponse<Region>>() {}
      )
      .getBody();

    return data.getPayload();
  }
  
  public Region delete(Integer id) {
    WebResponse<Region> data = this.restTemplate
      .exchange(
        "http://localhost:8080/region/"+id,
        HttpMethod.DELETE,
        null,
        new ParameterizedTypeReference<WebResponse<Region>>() {}
      )
      .getBody();

    return data.getPayload();
  }

  public Region getById(Integer id) {
    WebResponse<Region> data = this.restTemplate
      .exchange(
        "http://localhost:8080/region/"+id,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<WebResponse<Region>>() {}
      )
      .getBody();

    return data.getPayload();
  }

  public Region update(Integer id, Region region) {
    WebResponse<Region> data = this.restTemplate
    .exchange(
      "http://localhost:8080/region/"+id,
      HttpMethod.PUT,
      new HttpEntity<Region>(region),
      new ParameterizedTypeReference<WebResponse<Region>>() {}
    )
    .getBody();

  return data.getPayload();
  }
}
