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
@Document(collection = "userAuth")
public class UserAuth {
  
  @Id
  private String email;
  private String password;
  private String firstName;
  private String lastname;
  private String role;
  private String token;
  private String source;
  private String status;
  private String createdDate;
  private String createdBy;
  private String lastUpdatedDate;
  private String lastUpdatedBy;

}
