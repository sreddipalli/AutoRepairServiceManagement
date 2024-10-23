package com.autorepair.service;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.autorepair.repository.UserAuth;
import com.autorepair.repository.UserAuthEntityRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class UserAuthServiceImpl implements UserAuthService {

  private final UserAuthEntityRepository userAuthRepository;


  @Override
  public Optional<UserAuth> findByEmail(String email) {
    return userAuthRepository.findByEmail(email);
  }

  @Override
  public void save(UserAuth user) {
    userAuthRepository.save(user);
  }
}
