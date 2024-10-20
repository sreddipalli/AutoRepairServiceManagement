package com.autorepair.config;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.autorepair.repository.UserAuth;
import com.autorepair.service.UserAuthService;
import com.autorepair.service.error.GenericException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomJwtGrantedAuthoritiesConverter
    implements Converter<Jwt, Collection<GrantedAuthority>> {

  private final UserAuthService userAuthService;

  @Override
  public Collection<GrantedAuthority> convert(Jwt jwt) {
    String email = jwt.getClaim("email");
    
    Optional<UserAuth> userDetails = userAuthService.findByEmail(email);
    if (userDetails.isPresent()) {
      String token = userDetails.get().getToken();
      log.info("JWT input token value :{}", jwt.getTokenValue());
      log.info("token in Database :{}", token);

      if (Objects.isNull(token) || !token.equalsIgnoreCase(jwt.getTokenValue())) {
        throw new GenericException("401", "Unauthorized");
      }

    } else {
      throw new GenericException("401", "Unauthorized");
    }
    return List.of(new SimpleGrantedAuthority(userDetails.get().getRole()));
  }
}
