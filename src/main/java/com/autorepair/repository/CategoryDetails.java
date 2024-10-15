package com.autorepair.repository;

import java.util.List;
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
@Document(collection = "category")
public class CategoryDetails {

  @Id
  private String id;
  private String categoryId;
  private String categoryName;
  private String categoryDescription;
  private String status;
  private List<Question> categoryQuestions;

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @ToString
  public static class Question {
    private String questionId;
    private String question;
    private List<String> options;
    private List<SubOption> subOptions;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @ToString
  public static class SubOption {
    private String type;
    private List<String> options;
  }

}
