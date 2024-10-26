package com.autorepair.service;

import java.util.List;
import org.springframework.stereotype.Component;
import com.autorepair.request.VehicleDetailsRequest;
import com.autorepair.response.VehicleDetailsResponse;

@Component
public interface VehicleDetailsService {

  List<VehicleDetailsResponse> getAllVehicleDetails();

  List<VehicleDetailsResponse> getVehicleDetailsById(String make);
  
  List<VehicleDetailsResponse> getVehicleDetailsByMakeAndModel(String make, String model);
  
  List<String> getVehicleMakesDetails();
  
  List<String> getModelDetailsByMake(String make);
  
  List<String> getYearDetailsByMakeModel(String make, String model);

  VehicleDetailsResponse saveVehicleDetails(VehicleDetailsRequest vehicleDetailsRequest);

  VehicleDetailsResponse updateVehicleDetails(String vehicleId, VehicleDetailsRequest vehicleDetailsRequest);

  VehicleDetailsResponse deleteVehicleDetails(String vehicleId);

}
