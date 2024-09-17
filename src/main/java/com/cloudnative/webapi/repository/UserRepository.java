package com.cloudnative.webapi.repository;

import com.cloudnative.webapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    Boolean existsByUsername(String username);

    User findByVerificationToken(String token);
}
