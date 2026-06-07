package com.skoolbus.service;

import com.skoolbus.dto.DashboardResponse;
import com.skoolbus.model.Bus;
import com.skoolbus.model.UserMaster;
import com.skoolbus.repository.BusRepository;
import com.skoolbus.repository.IncidentRepository;
import com.skoolbus.repository.NotificationEventRepository;
import com.skoolbus.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {
    private final BusRepository busRepository;
    private final StudentRepository studentRepository;
    private final NotificationEventRepository notificationEventRepository;
    private final IncidentRepository incidentRepository;

    public DashboardService(BusRepository busRepository, StudentRepository studentRepository, NotificationEventRepository notificationEventRepository, IncidentRepository incidentRepository) {
        this.busRepository = busRepository;
        this.studentRepository = studentRepository;
        this.notificationEventRepository = notificationEventRepository;
        this.incidentRepository = incidentRepository;
    }

    public DashboardResponse guardianDashboard(Long guardianId) {
        Bus bus = busRepository.findById(guardianId).orElseGet(() -> busRepository.findAll().stream().findFirst().orElseThrow());
        return dashboardForBus(bus);
    }

    public DashboardResponse dashboardForUser(UserMaster user) {
        Bus bus = user.getBusId() == null
                ? busRepository.findAll().stream().findFirst().orElseThrow()
                : busRepository.findById(user.getBusId()).orElseGet(() -> busRepository.findAll().stream().findFirst().orElseThrow());
        return dashboardForBus(bus);
    }

    private DashboardResponse dashboardForBus(Bus bus) {
        return new DashboardResponse(
                bus,
                studentRepository.findByBusId(bus.getId()).stream().map(DashboardResponse.StudentJourney::from).toList(),
                notificationEventRepository.findTop12ByBusIdOrderByOccurredAtAsc(bus.getId()),
                incidentRepository.findByBusIdOrderByCreatedAtDesc(bus.getId()).stream().map(DashboardResponse.IncidentSummary::from).toList()
        );
    }
}
