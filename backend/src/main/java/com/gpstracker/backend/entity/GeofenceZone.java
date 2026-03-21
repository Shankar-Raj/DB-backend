package com.gpstracker.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "geofence_zone")
public class GeofenceZone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Plain Long FK — no @ManyToOne relationship
    // Reason: @ManyToOne would join-load Geofence which could
    // trigger cascading loads touching the geometry column.
    @Column(name = "geofence_id", nullable = false)
    private Long geofenceId;

    private String name;

    @Column(nullable = false)
    private String type;

    // ── geometry column is intentionally NOT mapped here ──
    // It is GEOMETRY type in DB (binary internally).
    // Reading it as String without ST_AsText() returns garbage.
    // We use native queries with ST_AsText() / ST_GeomFromText()
    // for all geometry reads and writes.

    private Double radius; // Circle only, null otherwise

    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }
    public Long getGeofenceId()          { return geofenceId; }
    public void setGeofenceId(Long id)   { this.geofenceId = id; }
    public String getName()              { return name; }
    public void setName(String name)     { this.name = name; }
    public String getType()              { return type; }
    public void setType(String type)     { this.type = type; }
    public Double getRadius()            { return radius; }
    public void setRadius(Double radius) { this.radius = radius; }
}