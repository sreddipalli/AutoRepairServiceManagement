package com.autorepair.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleEntityRepository extends MongoRepository<VehicleDetails, String> {
  
  Optional<List<VehicleDetails>> findByMake(String make);
  
  Optional<List<VehicleDetails>> findByMakeAndModel(String make, String model);

}
