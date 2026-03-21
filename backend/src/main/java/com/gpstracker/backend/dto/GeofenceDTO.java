package com.gpstracker.backend.dto;

import java.util.List;

public class GeofenceDTO {

    private Long          id;
    private String        name;
    private List<ZoneDTO> geofences;

    public Long getId()                       { return id; }
    public void setId(Long id)                { this.id = id; }
    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }
    public List<ZoneDTO> getGeofences()       { return geofences; }
    public void setGeofences(List<ZoneDTO> z) { this.geofences = z; }
}