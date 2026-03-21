package com.gpstracker.backend.dto;

public class RouteStationDTO {

    private Long   id;
    private int    serial;
    private String station;       // matches React field name
    private Double distance;      // matches React field name
    private String arrival;       // "HH:mm" string from React time input
    private String depart;        // "HH:mm" string from React time input

    public Long getId()                  { return id; }
    public void setId(Long id)           { this.id = id; }
    public int getSerial()               { return serial; }
    public void setSerial(int v)         { this.serial = v; }
    public String getStation()           { return station; }
    public void setStation(String v)     { this.station = v; }
    public Double getDistance()          { return distance; }
    public void setDistance(Double v)    { this.distance = v; }
    public String getArrival()           { return arrival; }
    public void setArrival(String v)     { this.arrival = v; }
    public String getDepart()            { return depart; }
    public void setDepart(String v)      { this.depart = v; }
}