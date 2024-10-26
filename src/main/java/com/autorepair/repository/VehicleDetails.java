package com.autorepair.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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
@Document(collection = "vehicleDetails")
public class VehicleDetails {

  @Id
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
