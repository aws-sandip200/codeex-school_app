package com.skoolbus.dto;

import com.skoolbus.model.Bus;
import com.skoolbus.model.Incident;
import com.skoolbus.model.NotificationEvent;
import com.skoolbus.model.Student;
import java.time.OffsetDateTime;
import java.util.List;

public record DashboardResponse(Bus bus, List<StudentJourney> students, List<NotificationEvent> notifications, List<IncidentSummary> incidents) {
    public record StudentJourney(String studentName, String grade, String pickupStop, String guardianName, String guardianPhone, String morningStatus, String eveningStatus, OffsetDateTime lastEventAt) {
        public static StudentJourney from(Student student) {
            return new StudentJourney(student.getStudentName(), student.getGrade(), student.getPickupStop(), student.getGuardianName(), student.getGuardianPhone(), student.getMorningStatus(), student.getEveningStatus(), student.getLastEventAt());
        }
    }

    public record IncidentSummary(Long id, String busCode, String severity, String status, String description, String resolutionPlan, OffsetDateTime createdAt) {
        public static IncidentSummary from(Incident incident) {
            return new IncidentSummary(incident.getId(), incident.getBus().getBusCode(), incident.getSeverity(), incident.getStatus(), incident.getDescription(), incident.getResolutionPlan(), incident.getCreatedAt());
        }
    }
}
