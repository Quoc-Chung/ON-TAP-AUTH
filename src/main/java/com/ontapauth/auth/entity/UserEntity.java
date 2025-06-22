package com.ontapauth.auth.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private String email;
  private boolean enabled = true;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private RoleEntity role;

  /*- Quyền của người dùng -*/
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getCode()));
  }

  /*- Tài khoản còn hạn sử dụng không  -*/
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /*- Tài khoản bị khóa không -*/
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /*- Mật khẩu hết hạn chưa -*/
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  /*- Tài khoản có bị vô hiệu hóa không  -*/
  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
