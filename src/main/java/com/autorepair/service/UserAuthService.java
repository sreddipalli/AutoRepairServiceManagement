package com.autorepair.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.autorepair.repository.UserAuth;

@Service
public interface UserAuthService {

  Optional<UserAuth> findByEmail(String email);

  void save(UserAuth user);
}
