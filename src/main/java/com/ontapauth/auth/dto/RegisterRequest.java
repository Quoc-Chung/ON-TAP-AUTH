package com.ontapauth.auth.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*- Thông thường việc đăng kí tài khoản chỉ có cho user -*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String username;
  private String email;
  private String password;
  private List<String> role;
  private List<String> permission;

}
