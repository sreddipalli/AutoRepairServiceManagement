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
public class VehicleDetailsRequest {

  private String make;
  private String model;
  private int cylinders;
  private String drive;
  private String fuelType;
  private String fuelType1;
  private String transmission;
  private String year;
  private String baseModel;
}
