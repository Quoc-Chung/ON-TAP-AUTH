package com.ontapauth.auth.controller.test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/products")
public class AdminController {

  @GetMapping("/")
  public ResponseEntity<String> getProduct() {
    return ResponseEntity.ok("Thông tin sản phẩm được admin lấy thành công");
  }

  @PostMapping("/")
  public ResponseEntity<String> createProduct() {
    return ResponseEntity.ok("Gửi thông tin sản phẩm được admin tạo thành công");
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateProduct(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Sửa thông tin sản phẩm với id " + id + " được admin thành công");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
    return ResponseEntity.ok("Xóa thông tin sản phẩm với id " + id + " được admin thành công");
  }
}

