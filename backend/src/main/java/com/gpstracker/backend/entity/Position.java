package com.gpstracker.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tc_positions")
public class Position {

    @Id
    @Column(name = "id")
    private Long id;
    private int deviceId;

    private Double latitude;
    private Double longitude;
    private Double speed;
    private Integer course;

    @Column(name = "fixtime")
    private LocalDateTime fixtime;

    @Column(name = "geofenceids")
    private String geofenceIds;

    public Long getId() { return id; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Double getSpeed() { return speed; }
    public Integer getCourse() { return course; }
    public String getGeofenceIds() { return geofenceIds; }
}
