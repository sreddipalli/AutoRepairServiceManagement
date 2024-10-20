package com.autorepair.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class OAuth2LoginSecurityConfig {

  @Autowired
  private CustomJwtGrantedAuthoritiesConverter customJwtGrantedAuthoritiesConverter;

  @Value("${frontend.url:http://localhost:3000}")
  private String frontendUrl;
  
  //@Value("${google.issuer-uri}")
  private String googleIssuerUri = "https://accounts.google.com";
  private String facebookIssuerUri = "https://accounts.google.com";

  //@Value("${facebook.jwk-set-uri}")
  private String facebookJwkSetUri = "https://www.facebook.com/.well-known/oauth/openid/jwks/";

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http//.csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth.requestMatchers("/servicemanagement/api/v1.0/public/**", "/vendorservice/api/v1.0/vendors/**").permitAll().anyRequest().authenticated())
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
        .build(); 
  }
  
  @Bean
  public JwtDecoder jwtDecoder() {
    return token -> {
      // Decode the token using Google's issuer URI
      log.info("token: {}", token);
      DecodedJWT decodedJWT = JWT.decode(token);
      // Get the issuer
      String issuer = decodedJWT.getIssuer();
      log.info("issuer: {}", issuer);
      Jwt jwt;
      if (googleIssuerUri.equals(issuer)) {
        jwt = JwtDecoders.fromIssuerLocation(googleIssuerUri).decode(token);
      } else  { //if(facebookIssuerUri.equals(issuer))
        JwtDecoder facebookJwtDecoder = NimbusJwtDecoder.withJwkSetUri(facebookJwkSetUri).build();
        jwt = facebookJwtDecoder.decode(token);
      }
      return jwt;
    };
  } 

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    log.info("jwtAuthenticationConverter method called");
    converter.setJwtGrantedAuthoritiesConverter(customJwtGrantedAuthoritiesConverter);
    return converter;
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of(frontendUrl));
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
        new UrlBasedCorsConfigurationSource();
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", configuration);
    return urlBasedCorsConfigurationSource;
  }
}
