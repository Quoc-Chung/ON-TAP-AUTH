package com.ontapauth.auth.controller.test;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/nhanvien/products")
public class NhanVienController {

  @GetMapping("/")
  public ResponseEntity<String> getProduct() {
    return ResponseEntity.ok("Thông tin sản phẩm được nhân viên lấy thành công");
  }

  @PostMapping("/")
  public ResponseEntity<String> createProduct() {
    return ResponseEntity.ok("Gửi thông tin sản phẩm được nhân viên tạo thành công");
  }

  /* Nhân viên không có quyền PUT (update), nên không khai báo */
  @PutMapping("/")
  public ResponseEntity<String> updateProduct() {
    return ResponseEntity.ok("Sua thông tin sản phẩm được nhân viên tạo thành công");
  }
}

