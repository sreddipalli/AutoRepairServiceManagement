package com.autorepair.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceDetailsRequest {

  private String serviceId;
  private String categoryId;
  private String serviceName;
  private String serviceDescription;
  private double servicePrice;
  private String serviceDuration;
  private String status;
}
