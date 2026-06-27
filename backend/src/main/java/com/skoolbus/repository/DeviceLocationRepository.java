package com.skoolbus.repository;

import com.skoolbus.model.DeviceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceLocationRepository extends JpaRepository<DeviceLocation, Long> {
    List<DeviceLocation> findTop24ByOrderByTrackedAtDesc();
}
