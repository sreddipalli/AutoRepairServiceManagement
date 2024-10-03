package com.autorepair.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryEntityRepository extends MongoRepository<CategoryDetails, String> {


}
