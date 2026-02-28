package com.gpstracker.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tc_geofences")
public class Geofence {

    @Id
    private Long id;

    private String name;

    @Column(columnDefinition = "geometry")
    private Object area;
}