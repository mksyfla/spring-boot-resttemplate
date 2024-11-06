package com.example.client_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.client_app.entity.Country;
import com.example.client_app.model.CountryResponse;
import com.example.client_app.service.CountryService;
import com.example.client_app.service.RegionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@RequestMapping("/country")
@Slf4j
public class CountryController {
  private CountryService countryService;
  private RegionService regionService;

  @GetMapping
  public String getAll(@RequestParam(required = false) String name, Model model) {
    log.info("query parameter: " + name);

    model.addAttribute("countries", this.countryService.getAll(name));
    model.addAttribute("isActive", "country");
    return "country/index";
  }

  @GetMapping("/create")
  public String createView(Model model) {
    model.addAttribute("regions", this.regionService.getAll(null));
    model.addAttribute("country", new Country());

    return "country/create-form";
  }

  @PostMapping
  public String create(Country country) {
    this.countryService.create(country);
    return "redirect:/country";
  }

  @DeleteMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {
    this.countryService.delete(id);
    return "redirect:/country";
  }
  
  @GetMapping("/{id}")
  public String getById(@PathVariable Integer id, Model model) {
    model.addAttribute("country", this.countryService.getById(id));
    return "country/detail";
  }
  
  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Integer id, Model model) {
    CountryResponse c = this.countryService.getById(id);
    Country country = new Country(c.getCountryId(), c.getCountryCode(), c.getCountryName(), c.getRegionId());

    model.addAttribute("country", country);
    model.addAttribute("regions", this.regionService.getAll(null));
    // model.addAttribute("prevCountry", country);

    return "country/update-form";
  }

  @PutMapping("/update/{id}")
  public String update(@PathVariable Integer id, Country country) {
    log.info(""+id);
    
    this.countryService.update(id, country);
    return "redirect:/country";
  }
}
