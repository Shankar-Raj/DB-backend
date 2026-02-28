package com.gpstracker.backend.dto;

public class GeofenceDTO {

    private Long id;
    private String name;
    private String type;
    private Object coordinates;

    public void setId(Long id) {this.id = id;};
    public void setName(String name) {this.name = name;};
    public void setType(String type) {this.type = type;};
    public void setCoordinates(Object coordinates) {this.coordinates = coordinates;};

    // getters & setters
}