package com.autorepair.service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autorepair.request.CategoryDetailsRequest;
import com.autorepair.response.CategoryDetailsResponse;

@Component
public interface CategoryDetailsService {

  List<CategoryDetailsResponse> getAllCategoryDetails();

  CategoryDetailsResponse getCategoryDetailsById(String categoryId);

  CategoryDetailsResponse saveCategoryDetails(CategoryDetailsRequest categoryDetailsRequest);

  CategoryDetailsResponse updateCategoryDetails(String categoryId,
      CategoryDetailsRequest categoryDetailsRequest);

  CategoryDetailsResponse deleteCategoryDetails(String categoryId);
}
