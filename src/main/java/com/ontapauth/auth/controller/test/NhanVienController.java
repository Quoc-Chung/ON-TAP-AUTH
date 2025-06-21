package com.ontapauth.auth.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nhanvien")
public class NhanVienController {
  @GetMapping("/work")
  public String employeeOnly() {
    return "Chào nhân viên";
  }
}