package com.autorepair.config;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "error-codes")
public class ErrorProperties {

  private Map<String, String> codes;

  public Map<String, String> getCodes() {
    return codes;
  }
}
