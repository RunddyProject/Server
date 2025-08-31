package com.runndy.server.security.oauth;

import com.runndy.server.domain.user.service.dto.response.SelectLoginUserResponseDto;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Builder
public record UserPrincipal(
    Long id, String provider, String providerId,
    String email, String name, Collection<? extends GrantedAuthority> authorities
) implements OAuth2User, UserDetails {

  public static UserPrincipal of(SelectLoginUserResponseDto userDto) {
    List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(userDto.getUserType()));

    return UserPrincipal.builder()
                        .provider(userDto.getSocialType())
                        .providerId(userDto.getSocialId())
                        .email(userDto.getEmail())
                        .name(userDto.getUserName())
                        .authorities(roles)
                        .build();

  }

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
