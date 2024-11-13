package com.example.client_app.config;

import org.springframework.stereotype.Component;

@Component
public class CookiesStore {
  private static String cookieString;

  public void setCookie(String cookie) {
    CookiesStore.cookieString = cookie;
  }

  public String getCookie() {
    return cookieString;
  }
}
