package com.gpstracker.backend.dto;

import java.time.LocalDateTime;

public class DeviceLivePatchDTO {

    private Long deviceId;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private Integer course;
    private LocalDateTime fixtime;
    private String eventtype;

    public DeviceLivePatchDTO(
            Long deviceId,
            Double latitude,
            Double longitude,
            Double speed,
            Integer course,
            LocalDateTime fixtime,
            String eventtype
    ) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.course = course;
        this.fixtime = fixtime;
        this.eventtype = eventtype;
    }

    public Long getDeviceId() { return deviceId; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Double getSpeed() { return speed; }
    public Integer getCourse() { return course; }
    public LocalDateTime getFixtime() { return fixtime; }
    public String getEventtype() { return eventtype; }
}
