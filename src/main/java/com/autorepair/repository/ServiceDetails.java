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
@Document(collection = "service")
public class ServiceDetails {

  @Id
  private String id;
  private String serviceId;
  private String categoryId;
  private String serviceName;
  private String serviceDescription;
  private double servicePrice;
  private String serviceDuration;
  private String status;

}
