package com.skoolbus.repository;

import com.skoolbus.model.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long> {
    List<NotificationEvent> findTop12ByBusIdOrderByOccurredAtAsc(Long busId);
}
