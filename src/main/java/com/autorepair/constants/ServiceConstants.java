package com.autorepair.constants;

import org.springframework.stereotype.Component;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceConstants {

  public static final String DRAFT = "draft";
  public static final String PUBLISHED = "published";
  public static final String DELETED = "deleted";
  public static final String ACTIVE = "active";
  public static final String IN_ACTIVE = "inActive";
  public static final String EXPIRED = "expired";

  public static final String INVALID_INPUT = "INVALID_INPUT";
  public static final String BUSINESS_EXCEPTION = "BUSINESS_EXCEPTION";
  public static final String GENERIC_EXCEPTION = "GENERIC_EXCEPTION";
  public static final String INPUT_ERROR_CODE = "IERR001";
  public static final String STATUS_ERROR_CODE = "IERR002";
  public static final String BUSINESS_ERROR_CODE = "BERR001";
  public static final String SERVER_ERROR_CODE = "SERR001";
  public static final String SCHEDULE_CREATION_ERROR_CODE = "SCHDERR001";
  public static final String SCHEDULE_STATUS_ERROR_CODE = "SCHDERR002";
  public static final String SCHEDULE_UPDATE_STATUS_ERROR_CODE = "SCHDERR003";
  public static final String GENERIC_ERROR_CODE = "500";
  public static final String SLOT_BOOKING_ERROR_CODE = "SLOTERR001";
  public static final String SLOT_CANCELLATION_ERROR_CODE = "SLOTERR002";
}
