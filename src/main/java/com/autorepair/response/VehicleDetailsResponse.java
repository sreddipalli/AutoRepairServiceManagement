package com.autorepair.response;

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
public class VehicleDetailsResponse {

  private String id;
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
