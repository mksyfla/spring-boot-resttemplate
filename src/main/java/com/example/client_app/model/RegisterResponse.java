package com.example.client_app.model;

import com.example.client_app.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterResponse {
  private Integer id;
  private String name;
  private String email;
  private String phone;
  private User user;
}
