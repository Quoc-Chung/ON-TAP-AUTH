package com.ontapauth.auth.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
  @GetMapping("/profile")
  public String userOrAbove() {
    return "Chào người dùng hoặc cao hơn";
  }
}