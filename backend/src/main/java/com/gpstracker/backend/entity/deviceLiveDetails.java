package com.gpstracker.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "device_live_details")
public class deviceLiveDetails {

    @Id
    @Column(name = "deviceId")
    private Long deviceId;

    @Column(name = "uniqueid")
    private String uniqueid;

    @Column(name = "name")
    private String name;

    @Column(name = "eventId")
    private Long eventId;

    @Column(name = "eventtype")
    private String eventtype;

    @Column(name = "lastupdated")
    private LocalDateTime lastupdated;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "address")
    private String address;

    @Column(name = "geofenceids")
    private String geofenceids;

    @Column(name = "groupid")
    private Long groupid;

    @Column(name = "status")
    private String status;

    // 🔹 Getters and Setters

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventtype() {
        return eventtype;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public LocalDateTime getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(LocalDateTime lastupdated) {
        this.lastupdated = lastupdated;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGeofenceids() {
        return geofenceids;
    }

    public void setGeofenceids(String geofenceids) {
        this.geofenceids = geofenceids;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
