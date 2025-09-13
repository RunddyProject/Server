package com.runndy.server.security.jwt;

import com.runndy.server.domain.user.enums.SocialType;
import com.runndy.server.domain.user.service.UserService;
import com.runndy.server.domain.user.service.dto.LoginUserInfoDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwt;
  private final TokenStore tokenStore;
  private final UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
      FilterChain chain)
      throws ServletException, IOException {
    String header = req.getHeader(HttpHeaders.AUTHORIZATION);

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7);
      try {
        Jws<Claims> jws = jwt.parse(token);
        Claims c = jws.getBody();
        String jti = c.getId();

        // 블랙리스트 확인
        if (!tokenStore.isAccessBlacklisted(jti)) {
          String subject = c.getSubject(); // provider:providerId
          String provider = subject.split(":")[0];
          String providerId = subject.split(":")[1];

          LoginUserInfoDto subjectDto = LoginUserInfoDto.of(SocialType.valueOf(provider.toUpperCase()),
              providerId);
          LoginUserInfoDto loginUserInfoDto = userService.getLoginUser(subjectDto);

          @SuppressWarnings("unchecked")
          List<SimpleGrantedAuthority> auths = ((List<String>) c.get("roles")).stream()
                                                                              .map(
                                                                                  SimpleGrantedAuthority::new)
                                                                              .toList();

          UsernamePasswordAuthenticationToken authentication =
              new UsernamePasswordAuthenticationToken(loginUserInfoDto, null, auths);

          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (JwtException e) {
        // 무시하고 익명으로 진행
      }
    }
    chain.doFilter(req, res);
  }
}
