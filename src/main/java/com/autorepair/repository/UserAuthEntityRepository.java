package com.autorepair.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserAuthEntityRepository extends MongoRepository<UserAuth, String> {

  Optional<UserAuth> findByEmail(String email);

}


