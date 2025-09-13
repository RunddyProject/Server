package com.runndy.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private static final String BEARER_TOKEN_PREFIX = "Bearer";

  @Bean
  public OpenAPI openAPI() {
    String securityJwtName = "JWT";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(securityJwtName);
    Components components = new Components()
        .addSecuritySchemes(securityJwtName, new SecurityScheme()
            .name(securityJwtName)
            .type(SecurityScheme.Type.HTTP)
            .scheme(BEARER_TOKEN_PREFIX)
            .bearerFormat(securityJwtName));

    Server local = new Server();
    local.setUrl("http://localhost:8080/");

    Server dev = new Server();
    dev.setUrl("https://api.runddy.co.kr/");

    return new OpenAPI()
        .info(apiInfo())
        .addSecurityItem(securityRequirement)
        .components(components)
        .servers(List.of(dev, local));
  }

  private Info apiInfo() {
    return new Info()
        .title("Runddy API Docs") // API의 제목
        .description("""
            <a href = "https://api.runddy.co.kr/oauth2/authorization/kakao" target="blank"> API 서버 카카오 로그인 </a> <br>
            <a href = "https://api.runddy.co.kr/oauth2/authorization/naver" target="blank"> API 서버 네이버 로그인 </a> <br>
            <a href = "http://localhost:8080/oauth2/authorization/kakao" target="blank"> 로컬 서버 카카오 로그인 </a> <br>
            <a href = "http://localhost:8080/oauth2/authorization/naver" target="blank"> 로컬 서버 네이버 로그인 </a>
            """)
        .version("1.0.0"); // API의 버전
  }
}