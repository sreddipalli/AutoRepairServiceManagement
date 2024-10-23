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
import com.autorepair.request.ServiceDetailsRequest;
import com.autorepair.response.ServiceDetailsResponse;
import com.autorepair.service.ServiceDetailsService;
import com.autorepair.service.error.InvalidRequestArgumentException;
import com.autorepair.service.result.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class ServiceController {

  ServiceDetailsService serviceDetailsService;

  @GetMapping(value = "/servicemanagement/api/v1.0/public/services")
  ResponseEntity<Response<List<ServiceDetailsResponse>>> getServiceList() {

    log.info("get the Service list");

    List<ServiceDetailsResponse> serviceResponseWrapperList =
        serviceDetailsService.getAllServiceDetails();
    return new ResponseEntity<>(Response.success(serviceResponseWrapperList), HttpStatus.OK);
  }

  @GetMapping(value = "/servicemanagement/api/v1.0/public/service/{serviceId}")
  ResponseEntity<Response<ServiceDetailsResponse>> getServiceDetailsV1(
      @PathVariable("serviceId") String serviceId) {
    log.info("inputs serviceId to get the service details {}", serviceId);
    ServiceDetailsResponse serviceDetailsResponse =
        serviceDetailsService.getServiceDetailsById(serviceId);
    return new ResponseEntity<>(Response.success(serviceDetailsResponse), HttpStatus.OK);
  }
  
  @GetMapping(value = "/servicemanagement/api/v1.0/category/{categoryId}/services")
  ResponseEntity<Response<ServiceDetailsResponse>> getServiceDetailsByCategoryV1(
      @PathVariable("categoryId") String categoryId) {
    log.info("inputs serviceId to get the service details {}", categoryId);
    ServiceDetailsResponse serviceDetailsResponse =
        serviceDetailsService.getServiceDetailsByCategoryId(categoryId);
    return new ResponseEntity<>(Response.success(serviceDetailsResponse), HttpStatus.OK);
  }

  @PostMapping(value = "/servicemanagement/api/v1.0/service")
  ResponseEntity<Response<ServiceDetailsResponse>> createServiceDetailsV1(
      @RequestBody ServiceDetailsRequest serviceDetailsRequest, BindingResult bindingResult) {
    log.info("creating new Service");

    if (bindingResult.hasErrors()) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    ServiceDetailsResponse serviceDetailsResponse =
        serviceDetailsService.saveServiceDetails(serviceDetailsRequest);
    return new ResponseEntity<>(Response.success(serviceDetailsResponse), HttpStatus.OK);
  }

  @PutMapping(value = "/servicemanagement/api/v1.0/service/{serviceId}")
  ResponseEntity<Response<ServiceDetailsResponse>> updateServiceDetailsV1(
      @PathVariable("serviceId") String serviceId,
      @RequestBody @Validated ServiceDetailsRequest serviceDetailsRequest,
      BindingResult bindingResult) {
    log.info("updating service details for serviceId: {}", serviceId);

    if (bindingResult.hasErrors() || Objects.isNull(serviceId)) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    ServiceDetailsResponse serviceResponseWrapper =
        serviceDetailsService.updateServiceDetails(serviceId, serviceDetailsRequest);
    if (Objects.nonNull(serviceResponseWrapper)) {
      return new ResponseEntity<>(Response.success(serviceResponseWrapper), HttpStatus.OK);
    }
    return new ResponseEntity<>(Response.failure("400", "Service Details Not Found", ""),
        HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping(value = "/servicemanagement/api/v1.0/service/{serviceId}")
  ResponseEntity<Response<ServiceDetailsResponse>> deleteServiceDetailsV1(
      @PathVariable("serviceId") String serviceId, BindingResult bindingResult) {
    log.info("updating service details for service: {}", serviceId);

    if (bindingResult.hasErrors() || Objects.isNull(serviceId)) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    ServiceDetailsResponse serviceResponseWrapper =
        serviceDetailsService.deleteServiceDetails(serviceId);
    if (Objects.nonNull(serviceResponseWrapper)) {
      return new ResponseEntity<>(Response.success(serviceResponseWrapper), HttpStatus.OK);
    }
    return new ResponseEntity<>(Response.failure("400", "Service Details Not Found", ""),
        HttpStatus.BAD_REQUEST);
  }
}
