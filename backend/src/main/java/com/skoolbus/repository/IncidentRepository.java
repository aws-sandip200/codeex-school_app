package com.skoolbus.repository;

import com.skoolbus.model.Incident;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
    List<Incident> findByBusIdOrderByCreatedAtDesc(Long busId);
}
