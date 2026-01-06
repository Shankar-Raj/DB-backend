package com.gpstracker.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tc_devices")
@Data
public class Device {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "uniqueid", nullable = false)
    private String uniqueId;

    @Column(name = "status")
    private String status;

    @Column(name = "lastupdate")
    private LocalDateTime lastUpdate;

    @Column(name = "disabled")
    private Boolean disabled;
}
