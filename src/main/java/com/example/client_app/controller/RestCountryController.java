package com.example.client_app.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.client_app.entity.Country;
import com.example.client_app.service.CountryService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/country")
public class RestCountryController {
  private CountryService countryService;

  @GetMapping
  public List<Country> getAll(@RequestParam(required = false) String name) {
    return this.countryService.getAll();
  }

  @PostMapping
  public Country create(@RequestBody Country country) {
    return this.countryService.create(country);
  }

  @DeleteMapping("/{id}")
  public Country delete(@PathVariable Integer id) {
    return this.countryService.delete(id);
  }
  
  @GetMapping("/{id}")
  public Country getById(@PathVariable Integer id) {
    return this.countryService.getById(id);
  }

  @PutMapping("/{id}")
  public Country update(@PathVariable Integer id, @RequestBody Country country) {
    return this.countryService.update(id, country);
  }
}
