package com.gpstracker.backend.dto;

import java.time.LocalDateTime;

public class PositionHistoryDTO {

    private Double latitude;
    private Double longitude;
    private Double speed;
    private LocalDateTime fixtime;
    private float course;

    public PositionHistoryDTO(
            Double latitude,
            Double longitude,
            Double speed,
            LocalDateTime fixtime,
            float course
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.fixtime = fixtime;
        this.course = course;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getSpeed() {
        return speed;
    }

    public LocalDateTime getFixtime() {
        return fixtime;
    }

    public float getCourse() {
        return course;
    }
}
