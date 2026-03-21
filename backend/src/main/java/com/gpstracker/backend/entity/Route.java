package com.gpstracker.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "route")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_code", nullable = false, unique = true)
    private String routeCode;

    @Column(name = "route_type")
    private String routeType;

    @Column(name = "start_station", nullable = false)
    private String startStation;

    @Column(name = "end_station", nullable = false)
    private String endStation;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "total_distance")
    private Double totalDistance;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void onUpdate() { this.updatedAt = LocalDateTime.now(); }

    // ── Getters / Setters ──
    public Long getId()                        { return id; }
    public void setId(Long id)                 { this.id = id; }
    public String getRouteCode()               { return routeCode; }
    public void setRouteCode(String v)         { this.routeCode = v; }
    public String getRouteType()               { return routeType; }
    public void setRouteType(String v)         { this.routeType = v; }
    public String getStartStation()            { return startStation; }
    public void setStartStation(String v)      { this.startStation = v; }
    public String getEndStation()              { return endStation; }
    public void setEndStation(String v)        { this.endStation = v; }
    public LocalTime getStartTime()            { return startTime; }
    public void setStartTime(LocalTime v)      { this.startTime = v; }
    public LocalTime getEndTime()              { return endTime; }
    public void setEndTime(LocalTime v)        { this.endTime = v; }
    public Double getTotalDistance()           { return totalDistance; }
    public void setTotalDistance(Double v)     { this.totalDistance = v; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public LocalDateTime getUpdatedAt()        { return updatedAt; }
}
