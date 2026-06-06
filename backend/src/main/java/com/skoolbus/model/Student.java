package com.skoolbus.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "students")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String studentName;
    private String grade;
    private String pickupStop;
    private String guardianName;
    private String guardianPhone;
    private String morningStatus;
    private String eveningStatus;
    private OffsetDateTime lastEventAt;
    @ManyToOne(optional = false) private Bus bus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getPickupStop() { return pickupStop; }
    public void setPickupStop(String pickupStop) { this.pickupStop = pickupStop; }
    public String getGuardianName() { return guardianName; }
    public void setGuardianName(String guardianName) { this.guardianName = guardianName; }
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String guardianPhone) { this.guardianPhone = guardianPhone; }
    public String getMorningStatus() { return morningStatus; }
    public void setMorningStatus(String morningStatus) { this.morningStatus = morningStatus; }
    public String getEveningStatus() { return eveningStatus; }
    public void setEveningStatus(String eveningStatus) { this.eveningStatus = eveningStatus; }
    public OffsetDateTime getLastEventAt() { return lastEventAt; }
    public void setLastEventAt(OffsetDateTime lastEventAt) { this.lastEventAt = lastEventAt; }
    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }
}
