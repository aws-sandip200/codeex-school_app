package com.skoolbus.config;

import com.skoolbus.model.Bus;
import com.skoolbus.model.Incident;
import com.skoolbus.model.NotificationEvent;
import com.skoolbus.model.Student;
import com.skoolbus.model.UserMaster;
import com.skoolbus.model.UserRole;
import com.skoolbus.repository.BusRepository;
import com.skoolbus.repository.IncidentRepository;
import com.skoolbus.repository.NotificationEventRepository;
import com.skoolbus.repository.StudentRepository;
import com.skoolbus.repository.UserMasterRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedDemoData(BusRepository buses, StudentRepository students, NotificationEventRepository events, IncidentRepository incidents, UserMasterRepository users) {
        return args -> {
            if (buses.count() > 0) return;

            Bus bus = new Bus();
            bus.setBusCode("G071845");
            bus.setPlateNumber("KA-05-MSB-1845");
            bus.setRouteName("East City Route - Sector 14");
            bus.setDriverName("Ravi Kumar");
            bus.setDriverPhone("+91 98765 43210");
            bus.setStatus("DELAYED");
            bus.setCurrentLatitude(12.9716);
            bus.setCurrentLongitude(77.5946);
            bus.setEtaMinutes(2);
            bus.setDelayMinutes(4);
            bus.setOccupancy(28);
            buses.save(bus);

            List<Student> demoStudents = students.saveAll(List.of(
                    student("Aarav Sharma", "Grade 4", "Maple Apartments", "Neha Sharma", "+91 90000 10001", "PICKED_UP_FROM_STOP", "WAITING_AT_SCHOOL", bus),
                    student("Maya Iyer", "Grade 2", "Lake View Gate", "Priya Iyer", "+91 90000 10002", "PICKED_UP_FROM_STOP", "EXTRA_CLASS", bus)
            ));

            users.saveAll(List.of(
                    user("student", "703b0a3d6ad75b649a28adde7d83c6251da457549263bc7ff45ec709b0a8448b", "Aarav Sharma", UserRole.STUDENT, "+91 90000 10001", "aarav@example.com", demoStudents.getFirst().getId(), bus.getId()),
                    user("parent", "82e3edf5f5f3a46b5f94579b61817fd9a1f356adcef5ee22da3b96ef775c4860", "Neha Sharma", UserRole.PARENT, "+91 90000 10001", "neha@example.com", demoStudents.getFirst().getId(), bus.getId()),
                    user("driver", "494d022492052a06f8f81949639a1d148c1051fa3d4e4688fbd96efe649cd382", "Ravi Kumar", UserRole.DRIVER, "+91 98765 43210", "ravi.driver@example.com", null, bus.getId()),
                    user("admin", "240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9", "School Transport Admin", UserRole.ADMIN, "+91 90000 99999", "transport-admin@example.com", null, bus.getId())
            ));

            events.saveAll(List.of(
                event("DEPARTURE", "Bus departed", "Good Morning. The bus G071845 has departed from school and will reach your stop on time.", "PUSH", "2026-06-06T08:00:00Z", bus),
                event("DELAY", "Traffic delay", "The bus G071845 would be late by 4 minutes due to heavy traffic.", "PUSH + SMS", "2026-06-06T08:14:00Z", bus),
                event("NEAR_STOP", "Two minutes away", "Bus G071845 will reach your stop within 2 minutes.", "PUSH", "2026-06-06T08:28:00Z", bus),
                event("ARRIVED_STOP", "Bus at stop", "Bus G071845 arrived at your stop.", "PUSH", "2026-06-06T08:30:00Z", bus),
                event("NFC_SCAN", "Child boarded", "Your child has been safely picked up from the bus stop.", "PUSH", "2026-06-06T08:50:00Z", bus),
                event("SCHOOL_DROPOFF", "Reached school", "Your child was dropped off at school safely. Have a good day.", "PUSH", "2026-06-06T09:15:00Z", bus),
                event("ACTIVITY_DELAY", "Activity delay", "The bus G071845 is delayed by an hour to depart from school due to annual day practice.", "PUSH + SMS", "2026-06-06T16:00:00Z", bus),
                event("INCIDENT", "Alternate bus assigned", "The alternative bus G633393 is on the way and will be arriving soon at your bus stop.", "PUSH + CALL", "2026-06-06T17:10:00Z", bus)
            ));

            Incident incident = new Incident();
            incident.setBus(bus);
            incident.setSeverity("HIGH");
            incident.setStatus("ALTERNATE_BUS_DISPATCHED");
            incident.setDescription("Primary bus reported engine overheating near Ring Road while students were onboard.");
            incident.setResolutionPlan("Notify guardians, dispatch alternate bus G633393, transfer students with attendant verification, and record NFC scans after handoff.");
            incident.setCreatedAt(OffsetDateTime.parse("2026-06-06T17:00:00Z"));
            incidents.save(incident);
        };
    }

    private Student student(String name, String grade, String stop, String guardian, String phone, String morning, String evening, Bus bus) {
        Student student = new Student();
        student.setStudentName(name);
        student.setGrade(grade);
        student.setPickupStop(stop);
        student.setGuardianName(guardian);
        student.setGuardianPhone(phone);
        student.setMorningStatus(morning);
        student.setEveningStatus(evening);
        student.setLastEventAt(OffsetDateTime.now(ZoneOffset.UTC));
        student.setBus(bus);
        return student;
    }

    private UserMaster user(String username, String passwordHash, String displayName, UserRole role, String phone, String email, Long studentId, Long busId) {
        UserMaster user = new UserMaster();
        user.setUsername(username);
        user.setPasswordHash(passwordHash);
        user.setDisplayName(displayName);
        user.setRole(role);
        user.setPhone(phone);
        user.setEmail(email);
        user.setStudentId(studentId);
        user.setBusId(busId);
        user.setActive(true);
        user.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
        return user;
    }

    private NotificationEvent event(String type, String title, String message, String channel, String occurredAt, Bus bus) {
        NotificationEvent event = new NotificationEvent();
        event.setEventType(type);
        event.setTitle(title);
        event.setMessage(message);
        event.setChannel(channel);
        event.setOccurredAt(OffsetDateTime.parse(occurredAt));
        event.setBus(bus);
        return event;
    }
}
