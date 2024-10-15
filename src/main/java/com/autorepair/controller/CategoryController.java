package com.autorepair.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.autorepair.request.CategoryDetailsRequest;
import com.autorepair.response.CategoryDetailsResponse;
import com.autorepair.service.CategoryDetailsService;
import com.autorepair.service.error.InvalidRequestArgumentException;
import com.autorepair.service.result.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class CategoryController {

  CategoryDetailsService categoryDetailsService;

  @GetMapping(value = "/servicemanagement/api/v1.0/category")
  ResponseEntity<Response<List<CategoryDetailsResponse>>> getServiceList() {

    log.info("get the Service list");

    List<CategoryDetailsResponse> categoryDetailsList =
        categoryDetailsService.getAllCategoryDetails();
    return new ResponseEntity<>(Response.success(categoryDetailsList), HttpStatus.OK);
  }

  @GetMapping(value = "/servicemanagement/api/v1.0/category/{categoryId}")
  ResponseEntity<Response<CategoryDetailsResponse>> getcategoryDetailsV1(
      @PathVariable("categoryId") String categoryId) {
    log.info("inputs categoryId to get the category details {}", categoryId);
    CategoryDetailsResponse CategoryDetailsResponse =
        categoryDetailsService.getCategoryDetailsById(categoryId);
    return new ResponseEntity<>(Response.success(CategoryDetailsResponse), HttpStatus.OK);
  }

  @PostMapping(value = "/servicemanagement/api/v1.0/category")
  ResponseEntity<Response<CategoryDetailsResponse>> createcategoryDetailsV1(
      @RequestBody CategoryDetailsRequest categoryDetailsRequest, BindingResult bindingResult) {
    log.info("creating new category");

    if (bindingResult.hasErrors()) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    CategoryDetailsResponse categoryDetailsResponse =
        categoryDetailsService.saveCategoryDetails(categoryDetailsRequest);
    return new ResponseEntity<>(Response.success(categoryDetailsResponse), HttpStatus.OK);
  }

  @PutMapping(value = "/servicemanagement/api/v1.0/category/{categoryId}")
  ResponseEntity<Response<CategoryDetailsResponse>> updatecategoryDetailsV1(
      @PathVariable("categoryId") String categoryId,
      @RequestBody @Validated CategoryDetailsRequest categoryDetailsRequest,
      BindingResult bindingResult) {
    log.info("updating category details for category: {}", categoryId);

    if (bindingResult.hasErrors() || Objects.isNull(categoryId)) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    CategoryDetailsResponse categoryResponseWrapper =
        categoryDetailsService.updateCategoryDetails(categoryId, categoryDetailsRequest);
    if (Objects.nonNull(categoryResponseWrapper)) {
      return new ResponseEntity<>(Response.success(categoryResponseWrapper), HttpStatus.OK);
    }
    return new ResponseEntity<>(Response.failure("400", "category Details Not Found", ""),
        HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping(value = "/servicemanagement/api/v1.0/category/{categoryId}")
  ResponseEntity<Response<CategoryDetailsResponse>> deletecategoryDetailsV1(
      @PathVariable("categoryId") String categoryId, BindingResult bindingResult) {
    log.info("updating category details for category: {}", categoryId);

    if (bindingResult.hasErrors() || Objects.isNull(categoryId)) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    CategoryDetailsResponse categoryResponseWrapper =
        categoryDetailsService.deleteCategoryDetails(categoryId);
    if (Objects.nonNull(categoryResponseWrapper)) {
      return new ResponseEntity<>(Response.success(categoryResponseWrapper), HttpStatus.OK);
    }
    return new ResponseEntity<>(Response.failure("400", "category Details Not Found", ""),
        HttpStatus.BAD_REQUEST);
  }
}
