package com.autorepair.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.autorepair.constants.ServiceConstants;
import com.autorepair.repository.CategoryDetails;
import com.autorepair.repository.CategoryEntityRepository;
import com.autorepair.request.CategoryDetailsRequest;
import com.autorepair.response.CategoryDetailsResponse;
import com.autorepair.service.error.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class CategoryDetailsServiceImpl implements CategoryDetailsService {

  CategoryEntityRepository categoryRepository;
  ObjectMapper objectMapper;
  ModelMapper modelMapper;

  private static final String LOG_CATEGORY_DETAILS = "Category details :{}";
  private static final String ERROR_CATEGORY_DETAILS = "Category details";
  private static final String CATEGORY_RESPONSE = "CategoryResponseWrapper :{}";
  private static final String CATEGORY_ERROR_MSG =
      "Category details for Category Id not found or Category Id is not valid";

  @Override
  public List<CategoryDetailsResponse> getAllCategoryDetails() {

    List<CategoryDetails> categoryList = categoryRepository.findAll();

    log.info(LOG_CATEGORY_DETAILS, categoryList);

    List<CategoryDetailsResponse> categoryResponseWrappers =
        categoryList.stream().map(categoryDetails -> {
          CategoryDetailsResponse categoryResponse = new CategoryDetailsResponse();
          categoryResponse = modelMapper.map(categoryDetails, CategoryDetailsResponse.class);

          log.info("Mapped Category Details CategoryResponse:{}", categoryResponse);
          return categoryResponse;
        }).toList();

    log.info("Mapped Service Details :{}", categoryResponseWrappers);
    return categoryResponseWrappers;
  }

  @Override
  @SneakyThrows
  public CategoryDetailsResponse getCategoryDetailsById(String categoryId) {

    Optional<CategoryDetails> categoryDetails = categoryRepository.findByCategoryId(categoryId);

    log.info(LOG_CATEGORY_DETAILS, categoryDetails);

    if (!categoryDetails.isPresent()) {
      log.error(CATEGORY_ERROR_MSG);
      log.error(CATEGORY_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_CATEGORY_DETAILS,
          categoryId);
    }

    CategoryDetailsResponse categoryResponse =
        modelMapper.map(categoryDetails, CategoryDetailsResponse.class);

    log.info(CATEGORY_RESPONSE, categoryResponse);
    return categoryResponse;
  }

  @Override
  @SneakyThrows
  public CategoryDetailsResponse saveCategoryDetails(
      CategoryDetailsRequest categoryDetailsRequest) {

    log.info(LOG_CATEGORY_DETAILS, categoryDetailsRequest);

    CategoryDetails categoryDetails =
        modelMapper.map(categoryDetailsRequest, CategoryDetails.class);

    log.info(LOG_CATEGORY_DETAILS + "Entity :", categoryDetails);

    categoryDetails = categoryRepository.save(categoryDetails);
    CategoryDetailsResponse categoryResponse =
        modelMapper.map(categoryDetails, CategoryDetailsResponse.class);

    log.info(CATEGORY_RESPONSE, categoryResponse);
    return categoryResponse;
  }


  @Override
  @SneakyThrows
  public CategoryDetailsResponse updateCategoryDetails(String categoryId,
      CategoryDetailsRequest categoryDetailsRequest) {

    log.info(LOG_CATEGORY_DETAILS, categoryDetailsRequest);

    Optional<CategoryDetails> categoryDetailsResponse = categoryRepository.findById(categoryId);

    log.info(LOG_CATEGORY_DETAILS, categoryDetailsResponse);
    if (!categoryDetailsResponse.isPresent()) {
      log.error(CATEGORY_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_CATEGORY_DETAILS,
          categoryId);
    }
    CategoryDetails categoryDetails =
        modelMapper.map(categoryDetailsRequest, CategoryDetails.class);
    categoryDetails.setCategoryId(categoryDetailsResponse.get().getCategoryId());
    log.info(LOG_CATEGORY_DETAILS + "Entity :", categoryDetails);

    categoryDetails = categoryRepository.save(categoryDetails);
    CategoryDetailsResponse categoryResponse =
        modelMapper.map(categoryDetails, CategoryDetailsResponse.class);

    log.info(CATEGORY_RESPONSE, categoryResponse);
    return categoryResponse;
  }

  @Override
  @SneakyThrows
  public CategoryDetailsResponse deleteCategoryDetails(String categoryId) {
    log.info("categoryId :", categoryId);

    Optional<CategoryDetails> categoryDetailsResponse = categoryRepository.findById(categoryId);

    log.info(LOG_CATEGORY_DETAILS, categoryDetailsResponse);
    if (!categoryDetailsResponse.isPresent()) {
      log.error(CATEGORY_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_CATEGORY_DETAILS,
          categoryId);
    }

    CategoryDetails categoryDetails = categoryDetailsResponse.get();
    categoryDetails.setStatus(ServiceConstants.IN_ACTIVE);

    categoryRepository.save(categoryDetails);

    return null;
  }
}
