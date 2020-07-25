package com.challenge.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT s.* FROM user s WHERE s.user_uuid = ?1", nativeQuery = true)
    User findByUuid(UUID userUUID);
}
