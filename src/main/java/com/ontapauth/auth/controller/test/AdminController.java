package com.ontapauth.auth.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
  @GetMapping("/dashboard")
  public String adminOnly() {
    return "Chào ADMIN Nha";
  }
}
