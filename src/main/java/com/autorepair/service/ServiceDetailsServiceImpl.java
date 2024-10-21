package com.autorepair.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.autorepair.constants.ServiceConstants;
import com.autorepair.repository.ServiceDetails;
import com.autorepair.repository.ServiceEntityRepository;
import com.autorepair.request.ServiceDetailsRequest;
import com.autorepair.response.ServiceDetailsResponse;
import com.autorepair.service.error.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ServiceDetailsServiceImpl implements ServiceDetailsService {

  ServiceEntityRepository serviceRepository;
  ObjectMapper objectMapper;
  ModelMapper modelMapper;
  
  private static final String LOG_SERVICE_DETAILS = "Service details :{}";
  private static final String ERROR_SERVICE_DETAILS = "Service details";
  private static final String SERVICE_RESPONSE = "ServiceResponseWrapper :{}";
  private static final String SERVICE_ERROR_MSG =
      "Service details for service Id not found or Service Id is not valid";

  @Override
  public List<ServiceDetailsResponse> getAllServiceDetails() {

    List<ServiceDetails> serviceList = serviceRepository.findAll();

    log.info(LOG_SERVICE_DETAILS, serviceList);

    List<ServiceDetailsResponse> serviceResponseWrappers =
        serviceList.stream().map(serviceDetails -> {
          ServiceDetailsResponse serviceResponse = new ServiceDetailsResponse();
          serviceResponse = modelMapper.map(serviceDetails, ServiceDetailsResponse.class);

          log.info("Mapped Vendor Details vendorResponse:{}", serviceResponse);
          return serviceResponse;
        }).toList();

    log.info("Mapped Service Details :{}", serviceResponseWrappers);
    return serviceResponseWrappers;
  }

  @Override
  @SneakyThrows
  public ServiceDetailsResponse getServiceDetailsById(String serviceId) {

    Optional<ServiceDetails> serviceDetails = serviceRepository.findByServiceId(serviceId);

    log.info(LOG_SERVICE_DETAILS, serviceDetails);

    if (!serviceDetails.isPresent()) {
      log.error(SERVICE_ERROR_MSG);
      log.error(SERVICE_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_SERVICE_DETAILS,
          serviceId);
    }

    ServiceDetailsResponse serviceResponse =
        modelMapper.map(serviceDetails, ServiceDetailsResponse.class);

    log.info(SERVICE_RESPONSE, serviceResponse);
    return serviceResponse;
  }
  
  @Override
  public ServiceDetailsResponse getServiceDetailsByCategoryId(String categoryId) {
    Optional<ServiceDetails> serviceDetails = serviceRepository.findByCategoryId(categoryId);

    log.info(LOG_SERVICE_DETAILS, serviceDetails);

    if (!serviceDetails.isPresent()) {
      log.error(SERVICE_ERROR_MSG);
      log.error(SERVICE_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_SERVICE_DETAILS,
          categoryId);
    }

    ServiceDetailsResponse serviceResponse =
        modelMapper.map(serviceDetails, ServiceDetailsResponse.class);

    log.info(SERVICE_RESPONSE, serviceResponse);
    return serviceResponse;
  }

  @Override
  @SneakyThrows
  public ServiceDetailsResponse saveServiceDetails(ServiceDetailsRequest serDetailsRequest) {

    log.info(LOG_SERVICE_DETAILS, serDetailsRequest);

    ServiceDetails serviceDetails = modelMapper.map(serDetailsRequest, ServiceDetails.class);

    log.info(LOG_SERVICE_DETAILS + "Entity :", serviceDetails);

    serviceDetails = serviceRepository.save(serviceDetails);
    ServiceDetailsResponse serviceResponse =
        modelMapper.map(serviceDetails, ServiceDetailsResponse.class);

    log.info(SERVICE_RESPONSE, serviceResponse);
    return serviceResponse;
  }


  @Override
  @SneakyThrows
  public ServiceDetailsResponse updateServiceDetails(String serviceId,
      ServiceDetailsRequest serDetailsRequest) {

    log.info(LOG_SERVICE_DETAILS, serDetailsRequest);

    Optional<ServiceDetails> serviceDetailsResponse = serviceRepository.findById(serviceId);

    log.info(LOG_SERVICE_DETAILS, serviceDetailsResponse);
    if (!serviceDetailsResponse.isPresent()) {
      log.error(SERVICE_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_SERVICE_DETAILS,
          serviceId);
    }
    ServiceDetails serviceDetails = modelMapper.map(serDetailsRequest, ServiceDetails.class);
    serviceDetails.setServiceId(serviceDetailsResponse.get().getServiceId());
    log.info(LOG_SERVICE_DETAILS + "Entity :", serviceDetails);

    serviceDetails = serviceRepository.save(serviceDetails);
    ServiceDetailsResponse serviceResponse =
        modelMapper.map(serviceDetails, ServiceDetailsResponse.class);

    log.info(SERVICE_RESPONSE, serviceResponse);
    return serviceResponse;
  }

  @Override
  @SneakyThrows
  public ServiceDetailsResponse deleteServiceDetails(String serviceId) {
    log.info("ServiceId :", serviceId);

    Optional<ServiceDetails> serviceDetailsResponse = serviceRepository.findById(serviceId);

    log.info(LOG_SERVICE_DETAILS, serviceDetailsResponse);
    if (!serviceDetailsResponse.isPresent()) {
      log.error(SERVICE_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_SERVICE_DETAILS,
          serviceId);
    }

    ServiceDetails serviceDetails = serviceDetailsResponse.get();
    serviceDetails.setStatus(ServiceConstants.IN_ACTIVE);

    serviceRepository.save(serviceDetails);

    return null;
  }

}
