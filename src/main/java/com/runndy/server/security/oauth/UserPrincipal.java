package com.runndy.server.security.oauth;

import java.util.Collection;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public record UserPrincipal(
    Long id, String provider, String providerId,
    String email, String name, Collection<? extends GrantedAuthority> authorities
) implements OAuth2User, UserDetails {

  @Override
  public Map<String, Object> getAttributes() {
    return Map.of("email", email, "name", name);
  }

  @Override
  public String getName() {
    return provider + ":" + providerId;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return "";
  }

  @Override
  public String getUsername() {
    return email != null ? email : getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
