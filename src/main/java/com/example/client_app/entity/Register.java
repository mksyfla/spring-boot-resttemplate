package com.example.client_app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Register {
  private String name;
  private String email;
  private String phone;
  private String username;
  private String password;
}
