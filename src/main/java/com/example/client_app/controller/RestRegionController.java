package com.example.client_app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.client_app.entity.Region;
import com.example.client_app.service.RegionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/region")
public class RestRegionController {
  private RegionService regionService;

  @GetMapping
  public List<Region> getAll(@RequestParam(required = false) String name, Model model) {
    return this.regionService.getAll(name);
  }

  @PostMapping
  public Region create(@RequestBody Region region) {
    return this.regionService.create(region);
  }
  
  @DeleteMapping("/{id}")
  public Region delete(@PathVariable Integer id) {
    return this.regionService.delete(id);
  }

  @PutMapping("/{id}")
  public Region update(@PathVariable Integer id, @RequestBody Region region) {
    return this.regionService.update(id, region);
  }
  
  @GetMapping("/{id}")
  public Region getById(@PathVariable Integer id) {
    return this.regionService.getById(id);
  }
}
