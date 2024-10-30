package com.example.client_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.client_app.entity.Region;
import com.example.client_app.service.RegionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/region")
public class RegionController {
  private RegionService regionService;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("regions", this.regionService.getAll());
    return "region/index";
  }
  
  @GetMapping("/create")
  public String createView(Model model) {
    model.addAttribute("region", new Region());
    return "region/create-form";
  }

  @PostMapping
  public String create(Region region) {
    this.regionService.create(region);
    return "redirect:/region";
  }
  
  @PostMapping("/delete/{id}")
  public String delete(@PathVariable Integer id) {
    log.info(""+id);

    this.regionService.delete(id);
    return "redirect:/region";
  }

  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Integer id, Model model) {
    Region region = this.regionService.getById(id);
    model.addAttribute("region", new Region());
    model.addAttribute("prevRegion", region);
    return "region/update-form";
  }

  @PostMapping("/update/{id}")
  public String update(@PathVariable Integer id, Region region) {
    log.info(""+id);

    this.regionService.update(id, region);
    return "redirect:/region";
  }
  
  @GetMapping("/{id}")
  public String getById(@PathVariable Integer id, Model model) {
    Region region = this.regionService.getById(id);

    model.addAttribute("region", region);
    return "region/detail";
  }
}
