package com.gpstracker.backend.entity;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "route_station")
public class RouteStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_id", nullable = false)
    private Long routeId;

    @Column(name = "serial", nullable = false)
    private Integer serial;

    @Column(name = "station_name", nullable = false)
    private String stationName;

    @Column(name = "distance_from_prev")
    private Double distanceFromPrev;

    @Column(name = "arrival_time")
    private LocalTime arrivalTime;

    @Column(name = "departure_time")
    private LocalTime departureTime;

    // ── Getters / Setters ──
    public Long getId()                         { return id; }
    public void setId(Long id)                  { this.id = id; }
    public Long getRouteId()                    { return routeId; }
    public void setRouteId(Long v)              { this.routeId = v; }
    public Integer getSerial()                  { return serial; }
    public void setSerial(Integer v)            { this.serial = v; }
    public String getStationName()              { return stationName; }
    public void setStationName(String v)        { this.stationName = v; }
    public Double getDistanceFromPrev()         { return distanceFromPrev; }
    public void setDistanceFromPrev(Double v)   { this.distanceFromPrev = v; }
    public LocalTime getArrivalTime()           { return arrivalTime; }
    public void setArrivalTime(LocalTime v)     { this.arrivalTime = v; }
    public LocalTime getDepartureTime()         { return departureTime; }
    public void setDepartureTime(LocalTime v)   { this.departureTime = v; }
}
