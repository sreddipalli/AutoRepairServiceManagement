package com.autorepair.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import com.autorepair.constants.ServiceConstants;
import com.autorepair.repository.VehicleDetails;
import com.autorepair.repository.VehicleEntityRepository;
import com.autorepair.request.VehicleDetailsRequest;
import com.autorepair.response.VehicleDetailsResponse;
import com.autorepair.service.error.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class VehicleDetailsServiceImpl implements VehicleDetailsService {

  VehicleEntityRepository vehicleRepository;
  ObjectMapper objectMapper;
  ModelMapper modelMapper;
  MongoTemplate mongoTemplate;

  private static final String LOG_VEHICLE_DETAILS = "Service details :{}";
  private static final String ERROR_VEHICLE_DETAILS = "Service details";
  private static final String SERVICE_RESPONSE = "ServiceResponseWrapper :{}";
  private static final String SERVICE_ERROR_MSG =
      "Vehicle details for input details not found or input details are not valid";

  @Override
  public List<VehicleDetailsResponse> getAllVehicleDetails() {

    List<VehicleDetails> vehicleList = vehicleRepository.findAll();

    log.info(LOG_VEHICLE_DETAILS, vehicleList);

    List<VehicleDetailsResponse> vehicleResponseWrappers =
        vehicleList.stream().map(vehicleDetails -> {
          VehicleDetailsResponse vehicleResponse = new VehicleDetailsResponse();
          vehicleResponse = modelMapper.map(vehicleDetails, VehicleDetailsResponse.class);

          log.info("Mapped vehicle Details VehicleResponse:{}", vehicleResponse);
          return vehicleResponse;
        }).toList();

    log.info("Mapped Service Details :{}", vehicleResponseWrappers);
    return vehicleResponseWrappers;
  }

  @Override
  @SneakyThrows
  public List<VehicleDetailsResponse> getVehicleDetailsById(String make) {

    Optional<List<VehicleDetails>> vehicleList = vehicleRepository.findByMake(make);

    log.info(LOG_VEHICLE_DETAILS, vehicleList);
    
    List<VehicleDetailsResponse> vehicleResponseWrappers =
        vehicleList.get().stream().map(vehicleDetails -> {
          VehicleDetailsResponse vehicleResponse = new VehicleDetailsResponse();
          log.info(" Before Mapped Vendor Details vendorResponse:{}", vehicleResponse);
          vehicleResponse = modelMapper.map(vehicleDetails, VehicleDetailsResponse.class);

          log.info("Mapped Vendor Details vendorResponse:{}", vehicleResponse);
          return vehicleResponse;
        }).toList();
    log.info("Mapped Service Details :{}", vehicleResponseWrappers);

    return vehicleResponseWrappers;
  }
  
  @Override
  public List<String> getVehicleMakesDetails() {
    
    List<String> makes = mongoTemplate.findDistinct("make", VehicleDetails.class, String.class);
    
    log.info("Makes : {}", makes);
    return makes;
  }
  
  @Override
  public List<String> getModelDetailsByMake(String make) {
    Query query = new Query();
    // Add custom criteria to the query
    query.addCriteria(Criteria.where("make").is(make));
    
    List<String> models = mongoTemplate.findDistinct(query, "model", VehicleDetails.class, String.class);
    
    log.info("models : {}", models);
    return models;
  }
  
  @Override
  public List<String> getYearDetailsByMakeModel(String make, String model) {
    Query query = new Query();
    // Add custom criteria to the query
    query.addCriteria(Criteria.where("make").is(make));
    query.addCriteria(Criteria.where("model").is(model));
    
    List<String> years = mongoTemplate.findDistinct(query, "year", VehicleDetails.class, String.class);
    
    log.info("years : {}", years);
    return years;
  }

  @Override
  public List<VehicleDetailsResponse> getVehicleDetailsByMakeAndModel(String make, String model) {
    Optional<List<VehicleDetails>> vehicleList = vehicleRepository.findByMakeAndModel(make, model);

    log.info(LOG_VEHICLE_DETAILS, vehicleList);

    List<VehicleDetailsResponse> vehicleResponseWrappers =
        vehicleList.get().stream().map(vehicleDetails -> {
          VehicleDetailsResponse vehicleResponse = new VehicleDetailsResponse();
          log.info(" Before Mapped Vendor Details vendorResponse:{}", vehicleResponse);
          vehicleResponse = modelMapper.map(vehicleDetails, VehicleDetailsResponse.class);

          log.info("Mapped Vendor Details vendorResponse:{}", vehicleResponse);
          return vehicleResponse;
        }).toList();
    log.info("Mapped Service Details :{}", vehicleResponseWrappers);
    return vehicleResponseWrappers;
  }

  @Override
  @SneakyThrows
  public VehicleDetailsResponse saveVehicleDetails(VehicleDetailsRequest vehicleDetailsRequest) {

    log.info(LOG_VEHICLE_DETAILS, vehicleDetailsRequest);

    VehicleDetails vehicleDetails = modelMapper.map(vehicleDetailsRequest, VehicleDetails.class);

    log.info(LOG_VEHICLE_DETAILS + "Entity :", vehicleDetails);

    vehicleDetails = vehicleRepository.save(vehicleDetails);
    VehicleDetailsResponse vehicleResponse =
        modelMapper.map(vehicleDetails, VehicleDetailsResponse.class);

    log.info(SERVICE_RESPONSE, vehicleResponse);
    return vehicleResponse;
  }


  @Override
  @SneakyThrows
  public VehicleDetailsResponse updateVehicleDetails(String vehicleId,
      VehicleDetailsRequest vehicleDetailsRequest) {

    log.info(LOG_VEHICLE_DETAILS, vehicleDetailsRequest);

    Optional<VehicleDetails> vehicleDetailsResponse = vehicleRepository.findById(vehicleId);

    log.info(LOG_VEHICLE_DETAILS, vehicleDetailsResponse);
    if (!vehicleDetailsResponse.isPresent()) {
      log.error(SERVICE_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_VEHICLE_DETAILS,
          vehicleId);
    }
    VehicleDetails vehicleDetails = modelMapper.map(vehicleDetailsRequest, VehicleDetails.class);
    vehicleDetails.setId(vehicleDetailsResponse.get().getId());
    log.info(LOG_VEHICLE_DETAILS + "Entity :", vehicleDetails);

    vehicleDetails = vehicleRepository.save(vehicleDetails);
    VehicleDetailsResponse vehicleResponse =
        modelMapper.map(vehicleDetails, VehicleDetailsResponse.class);

    log.info(SERVICE_RESPONSE, vehicleResponse);
    return vehicleResponse;
  }

  @Override
  @SneakyThrows
  public VehicleDetailsResponse deleteVehicleDetails(String vehicleId) {
    log.info("vehicleId :", vehicleId);

    Optional<VehicleDetails> vehicleDetailsResponse = vehicleRepository.findById(vehicleId);

    log.info(LOG_VEHICLE_DETAILS, vehicleDetailsResponse);
    if (!vehicleDetailsResponse.isPresent()) {
      log.error(SERVICE_ERROR_MSG);
      throw new ResourceNotFoundException(ServiceConstants.INPUT_ERROR_CODE, ERROR_VEHICLE_DETAILS,
          vehicleId);
    }

    vehicleRepository.deleteById(vehicleId);

    return null;
  }

}
