package com.ontapauth.auth.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/products")
public class UserController {

  @GetMapping("/")
  public ResponseEntity<String> getProduct() {
    return ResponseEntity.ok("Thông tin sản phẩm được user lấy thành công");
  }

    /* - Không có khả năng truy cập -*/
    @PostMapping("/")
    public ResponseEntity<String> postProduct() {
      return ResponseEntity.ok("Gui thong Tin San Pham được user lấy thành công ");
    }
}
