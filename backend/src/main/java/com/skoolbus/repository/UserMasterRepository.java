package com.skoolbus.repository;

import com.skoolbus.model.UserMaster;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMasterRepository extends JpaRepository<UserMaster, Long> {
    Optional<UserMaster> findByUsernameIgnoreCaseAndActiveTrue(String username);
}
