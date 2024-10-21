package com.autorepair.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceEntityRepository extends MongoRepository<ServiceDetails, String> {

  Optional<ServiceDetails> findByServiceId(String serviceId);
  
  Optional<ServiceDetails> findByCategoryId(String categoryId);


}
