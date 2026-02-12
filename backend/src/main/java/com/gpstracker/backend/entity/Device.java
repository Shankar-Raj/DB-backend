package com.gpstracker.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tc_devices")
public class Device {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "uniqueid")
    private String uniqueId;

    @Column(name = "name")
    private String name;

    @Column(name = "positionid")
    private Long positionId;

    @Column(name = "groupid")
    private Long groupId;

    @Column(name = "status")
    private String status;

    // getters & setters
    public Long getId() { return id; }
    public String getUniqueId() { return uniqueId; }
    public String getName() { return name; }
    public Long getPositionId() { return positionId; }
    public Long getGroupId() { return groupId; }
    public String getStatus() { return status; }
}
