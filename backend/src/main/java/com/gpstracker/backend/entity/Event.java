package com.gpstracker.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tc_events")
public class Event {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "deviceid")
    private Long deviceId;

    @Column(name = "type")
    private String type;

    @Column(name = "eventtime")
    private LocalDateTime eventtime;

    // getters
    public Long getId() { return id; }
    public Long getDeviceId() { return deviceId; }
    public String getType() { return type; }
    public LocalDateTime getEventtime() { return eventtime; }
}
