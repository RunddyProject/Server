package com.runndy.server.config;

import com.runndy.server.domain.user.service.UserService;
import com.runndy.server.security.jwt.JwtAuthenticationFilter;
import com.runndy.server.security.jwt.JwtTokenProvider;
import com.runndy.server.security.jwt.TokenStore;
import com.runndy.server.security.oauth.CustomOAuth2UserService;
import com.runndy.server.security.oauth.OAuth2SuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain filterChain(HttpSecurity http,
      OAuth2SuccessHandler successHandler,
      CustomOAuth2UserService oAuth2UserService,
      JwtAuthenticationFilter jwtFilter) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // Allow unauthenticated access to these endpoints
            .anyRequest().permitAll()
        )
        .oauth2Login(oauth -> oauth
            .userInfoEndpoint(u -> u.userService(oAuth2UserService))
            .successHandler(successHandler)
        )
        .exceptionHandling(e -> e
            // 인증이 필요한 자원에서만 401을 리턴하도록 (메서드 보안이 트리거될 때)
            .authenticationEntryPoint((req, res, ex) -> {
              res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
              res.setContentType("application/json");
              res.getWriter().write("{\"message\":\"Unauthorized\"}");
            })
            .accessDeniedHandler((req, res, ex) -> {
              res.setStatus(HttpServletResponse.SC_FORBIDDEN);
              res.setContentType("application/json");
              res.getWriter().write("{\"message\":\"Forbidden\"}");
            })
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwt, TokenStore store, UserService userService) {
    return new JwtAuthenticationFilter(jwt, store, userService);
  }
}
