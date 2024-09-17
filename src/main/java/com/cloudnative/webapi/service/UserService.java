package com.cloudnative.webapi.service;

import com.cloudnative.webapi.model.request.CreateUserRequest;
import com.cloudnative.webapi.model.request.UpdateUserRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> getUser(String username);

    ResponseEntity<?> createUser(CreateUserRequest request);

    ResponseEntity<?> updateUser(UpdateUserRequest request);

    ResponseEntity<?> verifyEmail(String token);
}
