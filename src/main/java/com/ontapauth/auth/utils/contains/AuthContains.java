package com.ontapauth.auth.utils.contains;

import java.util.Set;

public class AuthContains {
  /* Danh sách hợp lệ */
  public static final Set<String> VALID_ROLES = Set.of("USER", "NHANVIEN", "ADMIN");
  public static final Set<String> VALID_PERMISSIONS = Set.of("GET", "POST", "PUT", "DELETE");

}
