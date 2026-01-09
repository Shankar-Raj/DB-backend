package com.gpstracker.backend.dto;

import java.time.LocalDateTime;

public class deviceLiveDetailsDTO {

    private Long deviceId;
    private String uniqueid;
    private String name;
    private Long eventId;
    private String eventtype;
    private LocalDateTime lastupdated;
    private Double speed;
    private Double latitude;
    private Double longitude;
    private String address;
    private String geofenceids;
    private Long groupid;
    private String status;

    // 🔹 Constructors

    public deviceLiveDetailsDTO() {
    }

    public deviceLiveDetailsDTO(
            Long deviceId,
            String uniqueid,
            String name,
            Long eventId,
            String eventtype,
            LocalDateTime lastupdated,
            Double speed,
            Double latitude,
            Double longitude,
            String address,
            String geofenceids,
            Long groupid,
            String status
    ) {
        this.deviceId = deviceId;
        this.uniqueid = uniqueid;
        this.name = name;
        this.eventId = eventId;
        this.eventtype = eventtype;
        this.lastupdated = lastupdated;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.geofenceids = geofenceids;
        this.groupid = groupid;
        this.status = status;
    }

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
