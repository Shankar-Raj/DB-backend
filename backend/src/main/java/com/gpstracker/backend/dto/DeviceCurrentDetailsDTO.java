package com.gpstracker.backend.dto;

import java.time.LocalDateTime;

public class DeviceCurrentDetailsDTO {

    public Long deviceId;
    public String uniqueId;
    public String name;

    public String eventtype;
    public LocalDateTime lastUpdated;

    public Double speed;
    public Double latitude;
    public Double longitude;
    public String geofenceIds;
    public Integer course;
    public LocalDateTime fixtime;

    public Long groupId;
    public String status;

    public String locationName;

    public DeviceCurrentDetailsDTO(
            Long deviceId,
            String uniqueId,
            String name,
            String eventtype,
            LocalDateTime lastUpdated,
            Double speed,
            Double latitude,
            Double longitude,
            String geofenceIds,
            Integer course,
            LocalDateTime fixtime,
            Long groupId,
            String status
    ) {
        this.deviceId = deviceId;
        this.uniqueId = uniqueId;
        this.name = name;
        this.eventtype = eventtype;
        this.lastUpdated = lastUpdated;
        this.speed = speed;
        this.latitude = latitude;
        this.longitude = longitude;
        this.geofenceIds = geofenceIds;
        this.course = course;
        this.fixtime = fixtime;
        this.groupId = groupId;
        this.status = status;
    }
}
