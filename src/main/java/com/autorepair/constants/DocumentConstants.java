package com.autorepair.constants;

import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentConstants {

  public static final String INVALID_INPUT = "INVALID_INPUT";
  public static final String GENERIC_EXCEPTION = "GENERIC_EXCEPTION";
}
