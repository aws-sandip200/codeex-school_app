package com.skoolbus.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "incidents")
public class Incident {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String severity;
    private String status;
    @Column(length = 1000) private String description;
    @Column(length = 1000) private String resolutionPlan;
    private OffsetDateTime createdAt;
    @ManyToOne(optional = false) private Bus bus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getResolutionPlan() { return resolutionPlan; }
    public void setResolutionPlan(String resolutionPlan) { this.resolutionPlan = resolutionPlan; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }
}
