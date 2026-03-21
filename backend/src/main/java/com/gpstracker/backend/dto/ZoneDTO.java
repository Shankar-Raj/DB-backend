package com.gpstracker.backend.dto;

import java.util.List;

public class ZoneDTO {

    private String       id;
    private String       name;
    private String       type;

    /**
     * Object → Jackson deserializes any JSON array automatically:
     *   [lat, lng]          → List<Double>         (Point)
     *   [[lat,lng], ...]    → List<List<Double>>   (Polygon / LineString)
     */
    private Object       coordinates;

    // Circle only
    private List<Double> center;
    private Double       radius;

    public String getId()                 { return id; }
    public void setId(String id)          { this.id = id; }
    public String getName()               { return name; }
    public void setName(String name)      { this.name = name; }
    public String getType()               { return type; }
    public void setType(String type)      { this.type = type; }
    public Object getCoordinates()        { return coordinates; }
    public void setCoordinates(Object c)  { this.coordinates = c; }
    public List<Double> getCenter()       { return center; }
    public void setCenter(List<Double> c) { this.center = c; }
    public Double getRadius()             { return radius; }
    public void setRadius(Double r)       { this.radius = r; }
}