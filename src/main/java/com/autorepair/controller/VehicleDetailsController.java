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
import com.autorepair.request.VehicleDetailsRequest;
import com.autorepair.response.VehicleDetailsResponse;
import com.autorepair.service.VehicleDetailsService;
import com.autorepair.service.error.InvalidRequestArgumentException;
import com.autorepair.service.result.Response;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@Slf4j
public class VehicleDetailsController {

  VehicleDetailsService vehicleDetailsService;

  @GetMapping(value = "/servicemanagement/api/v1.0/public/vehicles")
  ResponseEntity<Response<List<VehicleDetailsResponse>>> getVehicleList() {

    log.info("get the Vehicle list");

    List<VehicleDetailsResponse> vehicleResponseWrapperList =
        vehicleDetailsService.getAllVehicleDetails();
    return new ResponseEntity<>(Response.success(vehicleResponseWrapperList), HttpStatus.OK);
  }

  @GetMapping(value = "/servicemanagement/api/v1.0/public/vehicle/{make}")
  ResponseEntity<Response<List<VehicleDetailsResponse>>> getVehiclesDetailsV1(
      @PathVariable("make") String make) {
    log.info("inputs make details to get the vehicle details {}", make);
    List<VehicleDetailsResponse> vehicleDetailsResponse =
        vehicleDetailsService.getVehicleDetailsById(make);
    return new ResponseEntity<>(Response.success(vehicleDetailsResponse), HttpStatus.OK);
  }
  
  @GetMapping(value = "/servicemanagement/api/v1.0/public/vehicle/{make}/{model}")
  ResponseEntity<Response<List<VehicleDetailsResponse>>> getVehicleDetailsV1(
      @PathVariable("make") String make, @PathVariable("model") String model) {
    log.info("inputs to get the vehicle details make :{}, model :{}", make, model);
    List<VehicleDetailsResponse> vehicleDetailsResponse =
        vehicleDetailsService.getVehicleDetailsByMakeAndModel(make, model);
    return new ResponseEntity<>(Response.success(vehicleDetailsResponse), HttpStatus.OK);
  }
  
  @GetMapping(value = "/servicemanagement/api/v1.0/public/vehicle/makes")
  ResponseEntity<Response<List<String>>> getVehicleMakes(){
    log.info("inputs make details to get the vehicle makes");
    List<String> makeslDetails =
        vehicleDetailsService.getVehicleMakesDetails();
    return new ResponseEntity<>(Response.success(makeslDetails), HttpStatus.OK);
  }
  
  @GetMapping(value = "/servicemanagement/api/v1.0/public/vehicle/model/{make}")
  ResponseEntity<Response<List<String>>> getVehicleModels(
      @PathVariable("make") String make) {
    log.info("inputs make details to get the vehicle models {}", make);
    List<String> modelDetails =
        vehicleDetailsService.getModelDetailsByMake(make);
    return new ResponseEntity<>(Response.success(modelDetails), HttpStatus.OK);
  }
  
  @GetMapping(value = "/servicemanagement/api/v1.0/public/vehicle/years/{make}/{model}")
  ResponseEntity<Response<List<String>>> getVehicleModelYears(
      @PathVariable("make") String make, @PathVariable("model") String model) {
    log.info("inputs to get the vehicle details make :{}, model :{}", make, model);
    List<String> modelDetails =
        vehicleDetailsService.getYearDetailsByMakeModel(make, model);
    return new ResponseEntity<>(Response.success(modelDetails), HttpStatus.OK);
  }
  
  @PostMapping(value = "/servicemanagement/api/v1.0/public/vehicle")
  ResponseEntity<Response<VehicleDetailsResponse>> createVehicleDetailsV1(
      @RequestBody VehicleDetailsRequest vehicleDetailsRequest, BindingResult bindingResult) {
    log.info("creating new vehicle details :{}", vehicleDetailsRequest);

    if (bindingResult.hasErrors()) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    VehicleDetailsResponse vehicleDetailsResponse =
        vehicleDetailsService.saveVehicleDetails(vehicleDetailsRequest);
    return new ResponseEntity<>(Response.success(vehicleDetailsResponse), HttpStatus.OK);
  }

  @PutMapping(value = "/servicemanagement/api/v1.0/public/vehicle/{vehicleId}")
  ResponseEntity<Response<VehicleDetailsResponse>> updateVehicleDetailsV1(
      @PathVariable("vehicleId") String vehicleId,
      @RequestBody @Validated VehicleDetailsRequest vehicleDetailsRequest,
      BindingResult bindingResult) {
    log.info("updating vehicle details for vehicleId: {}", vehicleId);

    if (bindingResult.hasErrors() || Objects.isNull(vehicleId)) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    VehicleDetailsResponse vehicleResponseWrapper =
        vehicleDetailsService.updateVehicleDetails(vehicleId, vehicleDetailsRequest);
    if (Objects.nonNull(vehicleResponseWrapper)) {
      return new ResponseEntity<>(Response.success(vehicleResponseWrapper), HttpStatus.OK);
    }
    return new ResponseEntity<>(Response.failure("400", "vehicle Details Not Found", ""),
        HttpStatus.BAD_REQUEST);
  }

  @DeleteMapping(value = "/servicemanagement/api/v1.0/public/vehicle/{vehicleId}")
  ResponseEntity<Response<VehicleDetailsResponse>> deleteVehicleDetailsV1(
      @PathVariable("vehicleId") String vehicleId, BindingResult bindingResult) {
    log.info("updating vehicle details for vehicleId: {}", vehicleId);

    if (bindingResult.hasErrors() || Objects.isNull(vehicleId)) {
      log.error("errors :" + bindingResult.getAllErrors());
      throw new InvalidRequestArgumentException(
          bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    VehicleDetailsResponse vehicleResponseWrapper =
        vehicleDetailsService.deleteVehicleDetails(vehicleId);
    if (Objects.nonNull(vehicleResponseWrapper)) {
      return new ResponseEntity<>(Response.success(vehicleResponseWrapper), HttpStatus.OK);
    }
    return new ResponseEntity<>(Response.failure("400", "Vehicle Details Not Found", ""),
        HttpStatus.BAD_REQUEST);
  }
}
