package com.autorepair.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryEntityRepository extends MongoRepository<CategoryDetails, String> {

  Optional<CategoryDetails> findByCategoryId(String categoryId);


}
